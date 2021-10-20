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

    /**
     *
     * @return the ArrayList of all players nuts information
     */
    public ArrayList<Nut> getAllPlayerNuts() {
        return allPlayerNuts;
    }

    /**
     *
     * @return the color player choose for his nuts in game
     */
    public char getPlayerNutColor() {
        return playerNutColor;
    }

}
