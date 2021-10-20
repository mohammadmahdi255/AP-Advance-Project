public class Nut {

    private char color;
    private int xPosition;
    private int yPosition;

    /**
     *
     * @param width a number of row board game
     * @param length a char of column board game
     * @param color a color of nut
     */
    public Nut(int width,char length,char color){
        int len = (int) length - 65;
        if (width >= 0 && width < 8 && len >= 0 && len < 8) {
            this.color = color;
            yPosition = width;
            xPosition = len;
        } else {
            System.out.println("This position doesn't exist on the map");
        }
    }

    /**
     *
     * @return the int number of row board game
     */
    public int getxPosition() {
        return xPosition;
    }

    /**
     *
     * @return the int number of column board game
     */
    public int getyPosition() {
        return yPosition;
    }

    /**
     *
     * @return the color of nut
     */
    public char getColor() {
        return color;
    }
}
