public class ConsoleColor {

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\033[0;35m";

    /**
     * this method is return the ANSI_RESET
     * @return is a string
     */
    public String getAnsiReset() {
        return ANSI_RESET;
    }

    /**
     * this method get a string color name and return the color string code
     * @param color is a string which carrier the color's name
     * @return is a string which is the string code of color
     */
    public String getColor(String color) {

        switch (color) {
            case "black":
                return ANSI_BLACK;
            case "red":
                return ANSI_RED;
            case "yellow":
                return ANSI_YELLOW;
            case "green":
                return ANSI_GREEN;
            case "blue":
                return ANSI_BLUE;
            case "purple":
                return ANSI_PURPLE;
        }

        return null;

    }

}
