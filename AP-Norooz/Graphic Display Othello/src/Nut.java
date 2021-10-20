public class Nut {

    private char color;
    private int xPosition;
    private int yPosition;

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

    public int getxPosition() {
        return xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }

    public char getColor() {
        return color;
    }
}
