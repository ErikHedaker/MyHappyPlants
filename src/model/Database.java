package model;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The Database class which handle the connection and all queries to a PostgreSQL database
 *
 * @author Erik Hedåker
 */
public class Database {
    private final String connectionURL;

    public Database(String connectionURL) {
        this.connectionURL = connectionURL;
    }

    /**
     * Gets all profile names from the database
     *
     * @return A list of profile names
     */
    public ArrayList<String> getAllProfileNames() {
        final String SQL = "SELECT name FROM profile";
        ArrayList<String> names = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(connectionURL);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL)) {
            while (resultSet.next()) {
                names.add(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return names;
    }

    /**
     * Gets specific profile from the database
     *
     * @param name A name of the profile you want to get
     * @return A newly created Profile
     */
    public Profile getProfile(String name) {
        final String SQL =
            "SELECT id, name, password_hash, password_salt " +
            "FROM profile " +
            "WHERE profile.name = ?";
        try (Connection connection = DriverManager.getConnection(connectionURL);
             PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setString(1, name);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    return new Profile()
                        .setDatabaseID(id)
                        .setName(name)
                        .setPasswordHash(resultSet.getBytes("password_hash"))
                        .setPasswordSalt(resultSet.getBytes("password_salt"))
                        .setPlants(getPlants(id));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Gets all the plants linked to the profile from the database
     *
     * @param profileID The id of the profile
     * @return A list of newly created plants
     */
    public ArrayList<Plant> getPlants(int profileID) {
        final String SQL =
            "SELECT id, name_alias, name_wiki, hours_between_watering, last_time_watered " +
            "FROM plant " +
            "LEFT JOIN RecentWatering() AS recent_watering " +
            "ON plant.id = recent_watering.plant_id " +
            "WHERE plant.profile_id = ?";
        ArrayList<Plant> plants = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(connectionURL);
             PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setInt(1, profileID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    plants.add(
                        new Plant()
                            .setDatabaseID(resultSet.getInt("id"))
                            .setNameAlias(resultSet.getString("name_alias"))
                            .setNameWiki(resultSet.getString("name_wiki"))
                            .setHoursBetweenWatering(resultSet.getInt("hours_between_watering"))
                            .setLastTimeWatered(resultSet.getObject("last_time_watered", LocalDateTime.class))
                    );
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return plants;
    }

    /**
     * Adds a date into the watering table in the database to log when the user waters their plants
     *
     * @param plantID  The id of the plant that gets watered
     * @param happened A date of when the user watered their plant
     */
    public LocalDateTime waterPlant(int plantID, LocalDateTime happened) {
        final String SQL =
            "INSERT INTO watering (plant_id, happened)" +
            "VALUES (?, ?)";
        try (Connection connection = DriverManager.getConnection(connectionURL);
             PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setInt(1, plantID);
            preparedStatement.setObject(2, happened);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return happened;
    }

    /**
     * Calls the waterPlant and passes the current date as the second argument
     *
     * @param plantID The id of the plant that gets watered
     */
    public LocalDateTime waterPlant(int plantID) {
        return waterPlant(plantID, LocalDateTime.now());
    }

    /**
     * Adds a plant to the database
     *
     * @param plant The plant that gets added
     * @return An id generated by the database, unique for the plant
     */
    public int insertPlant(int profileID, Plant plant) {
        final String SQL =
            "INSERT INTO plant (name_alias, name_wiki, profile_id, hours_between_watering)" +
            "VALUES (?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(connectionURL);
             PreparedStatement preparedStatement = connection.prepareStatement(SQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, plant.getNameAlias());
            preparedStatement.setString(2, plant.getNameWiki());
            preparedStatement.setInt(3, profileID);
            preparedStatement.setInt(4, plant.getHoursBetweenWatering());
            preparedStatement.executeUpdate();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Adds a profile to the database
     *
     * @param profile The profile that gets added
     * @return An id generated by the database, unique for the profile
     */
    public int insertProfile(Profile profile) {
        final String SQL =
            "INSERT INTO profile (name, password_hash, password_salt)" +
            "VALUES (?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(connectionURL);
             PreparedStatement preparedStatement = connection.prepareStatement(SQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, profile.getName());
            preparedStatement.setBytes(2, profile.getPasswordHash());
            preparedStatement.setBytes(3, profile.getPasswordSalt());
            preparedStatement.executeUpdate();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Updates a plant in the database
     *
     * @param plant The plant that gets updated
     */
    public void updatePlant(Plant plant) {
        final String SQL =
            "UPDATE plant " +
            "SET name_alias = ?, name_wiki = ?, hours_between_watering = ? " +
            "WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(connectionURL);
             PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setString(1, plant.getNameAlias());
            preparedStatement.setString(2, plant.getNameWiki());
            preparedStatement.setInt(3, plant.getHoursBetweenWatering());
            preparedStatement.setInt(4, plant.getDatabaseID());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates a profile in the database
     *
     * @param profile The profile that gets updated
     */
    public void updateProfile(Profile profile) {
        final String SQL =
            "UPDATE profile " +
            "SET name = ?, password_hash = ?, password_salt = ? " +
            "WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(connectionURL);
             PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setString(1, profile.getName());
            preparedStatement.setBytes(2, profile.getPasswordHash());
            preparedStatement.setBytes(3, profile.getPasswordSalt());
            preparedStatement.setInt(4, profile.getDatabaseID());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Removes a plant from the database
     *
     * @param plantID The id of the plant that gets removed
     */
    public void deletePlant(int plantID) {
        final String SQL =
            "DELETE FROM plant " +
            "WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(connectionURL);
             PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setInt(1, plantID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Removes a profile from the database
     *
     * @param profileID The id of the profile that gets removed
     */
    public void deleteProfile(int profileID) {
        final String SQL =
            "DELETE FROM profile " +
            "WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(connectionURL);
             PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setInt(1, profileID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the image for a plant from the database
     *
     * @param plantID The id of the plant
     */
    public byte[] getPlantImage(int plantID) {
        final String SQL =
            "SELECT raw_data " +
            "FROM plant_image " +
            "WHERE plant_id = ?";
        try (Connection connection = DriverManager.getConnection(connectionURL);
             PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setInt(1, plantID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getBytes("raw_data");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Adds or updates the image for a plant in the database
     *
     * @param plantID The id of the plant
     */
    public void upsertPlantImage(int plantID, byte[] image) {
        final String SQL =
            "INSERT INTO plant_image (plant_id, raw_data) " +
            "VALUES (?, ?) " +
            "ON CONFLICT (plant_id) DO UPDATE " +
            "SET raw_data = ? " +
            "WHERE excluded.plant_id = ?";
        try (Connection connection = DriverManager.getConnection(connectionURL);
             PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setInt(1, plantID);
            preparedStatement.setBytes(2, image);
            preparedStatement.setBytes(3, image);
            preparedStatement.setInt(4, plantID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<HashMap<String,String>> searchPlant(String name, int limit) {
        final String SQL =
            "SELECT * FROM plant_trefle_data " +
            "WHERE scientific_name ILIKE ? " +
            "OR (common_name ILIKE ? " +
            "AND NOT EXISTS (SELECT 1 FROM plant_trefle_data WHERE scientific_name ILIKE ?))" +
            "ORDER BY LENGTH(scientific_name) ASC " +
            "LIMIT ? ";
        ArrayList<HashMap<String,String>> plants = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(connectionURL);
             PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, name);
            preparedStatement.setInt(4, limit);
            if (limit < 0) {
                preparedStatement.setNull(4, Types.INTEGER);
            }
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                ResultSetMetaData meta = resultSet.getMetaData();
                while (resultSet.next()) {
                    HashMap<String,String> plant = new HashMap<>();
                    for (int i = 1; i <= meta.getColumnCount(); i++) {
                        String key = meta.getColumnName(i);
                        String value = resultSet.getString(key);
                        plant.put(key, value);
                    }
                    plants.add(plant);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return plants;
    }

    public ArrayList<HashMap<String,String>> searchPlant(String name) {
        return searchPlant(name, -1);
    }

    public int getAverageWatering(String nameWiki) {
        final String SQL =
            "SELECT name_wiki, AVG(hours_between_watering) FROM plant " +
            "WHERE name_wiki = ? " +
            "GROUP BY name_wiki";
        ArrayList<HashMap<String,String>> plants = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(connectionURL);
             PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setString(1, nameWiki);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("avg");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void main(String[] args) {
        Database database = new Database(new SimpleEncryption().readFile());
        Profile profileAdmin = database.getProfile("Admin");
        System.out.println(profileAdmin);
        /*
        for( HashMap<String,String> plant : database.searchPlant("%rose%"))
        {
            System.out.println(plant.get("scientific_name"));
        }
        */
        System.out.println(database.getAverageWatering("Carex sylvatica"));
    }
}
