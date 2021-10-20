import java.util.ArrayList;

public class Player {

    // all player nut information;
    private ArrayList<Nut> allPlayerNuts;
    private char playerNutColor;
    /**
     * constructor which only creat an ArrayList
     * @param playerNutColor is the nut color of player
     */
    public Player(char playerNutColor) {
        allPlayerNuts = new ArrayList<>();
        this.playerNutColor = playerNutColor;
    }

    public ArrayList<Nut> getAllPlayerNuts() {
        return allPlayerNuts;
    }

    public char getPlayerNutColor() {
        return playerNutColor;
    }

}
