package myhappyplants.view;

public class Utilities {

    public static String centerText(String text, int maxWidth) {
        if (maxWidth == 0)
            maxWidth = 80;
        int spaces = (int) Math.round((maxWidth-1.4*text.length())/2);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < spaces; i++) {
            sb.append(" ");
        }
        sb.append(text);
        return sb.toString();
    }
}
