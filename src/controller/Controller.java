package controller;

import model.Database;

public class Controller {

    private static Controller controller;
    private Database database;

    private Controller() {
        database = new Database();
    }

    public static Controller getInstance() {
        if (controller == null) {
            controller = new Controller();
        }
        return controller;
    }

    public Database getDatabase() {
        return database;
    }
}
