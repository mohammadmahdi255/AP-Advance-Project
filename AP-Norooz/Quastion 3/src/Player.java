import java.util.ArrayList;

public class Player {

    /**
     * handCards is including all player's cards information
     * playerNumber is the player number in game
     * autoPlayer is a boolean that show the player is computer or not
     * coloring is a object from consoleColor for show the text in different color in console
     * score is the all player's hand cards score
     */
    private ArrayList<Card> handCards;
    private int playerNumber;
    private boolean autoPlayer;
    private ConsoleColor coloring ;
    private int score;

    /**
     * creat players information
     * @param playerNumber is player number in game
     * @param autoPlayer is a boolean that show the player is computer or not
     */
    public Player(int playerNumber ,boolean autoPlayer){

        handCards = new ArrayList<>();
        this.playerNumber = playerNumber;
        this.autoPlayer = autoPlayer;
        coloring = new ConsoleColor();
        score = 0;

    }

    /**
     * this method return the number card player has
     * @return the number of player cards
     */
    public int getHandCardsNumber(){

        return handCards.size();

    }

    /**
     * this method show all players cards
     */
    public void showHandCards(){

        System.out.println("Player "+playerNumber+" turn");

        for (Card handCard : handCards) {
            System.out.print(coloring.getColor(handCard.getColor()) + "|$$$$$$$$$$$$$$$$$|   " + coloring.getAnsiReset());
        }

        System.out.println();

        int index = 1;
        for (Card handCard : handCards) {
            if(index < 10) {
                System.out.print(coloring.getColor(handCard.getColor()) + "|        " + (index++) + "        |   " + coloring.getAnsiReset());
            } else {
                System.out.print(coloring.getColor(handCard.getColor()) + "|       " + (index++) + "        |   " + coloring.getAnsiReset());
            }
        }

        System.out.println();

        print();

        for (Card handCard : handCards) {
            System.out.print(coloring.getColor(handCard.getColor()) + "|                 |   " + coloring.getAnsiReset());
        }

        System.out.println();

        for (Card handCard : handCards) {
            System.out.print(coloring.getColor(handCard.getColor()) + "|$$$$$$$$$$$$$$$$$|   " + coloring.getAnsiReset());
        }

        System.out.println();

    }

    /**
     * this method is for setting the text of card in center
     */
    private void print(){

        for (Card handCard : handCards) {
            System.out.print(coloring.getColor(handCard.getColor())+"|"+coloring.getAnsiReset());
            for (int j = 1; j < (18 - (handCard.getCardSign().length()+handCard.getColor().length())) / 2; j++) {
                System.out.print(" ");
            }
            System.out.print(coloring.getColor(handCard.getColor())+handCard.getColor()+" "+handCard.getCardSign()+coloring.getAnsiReset());
            for (int j = 1; j < (19 - (handCard.getCardSign().length()+handCard.getColor().length())) / 2; j++) {
                System.out.print(" ");
            }
            System.out.print(coloring.getColor(handCard.getColor())+"|   "+coloring.getAnsiReset());
        }

        System.out.println();

    }

    /**
     * this method is add a card to hand card list
     * @param addCard is a card information that is going to add to players hand cards
     */
    public void addCard(Card addCard) {

        handCards.add(addCard);

        if (addCard.getColor().equals("black")) {
            score += 50;
        } else if (addCard.getCardSign().equals("Skip") || addCard.getCardSign().equals("Reverse") || addCard.getColor().equals("+2")) {
            score += 20;
        } else {
            score += addCard.getCardSign().charAt(0) - 48;
        }

    }

    /**
     * this method remove a select card from the hand card
     * @param removeCard is a card information that is going to remove form players hand cards
     */
    public void removeCard(Card removeCard) {

        handCards.remove(removeCard);

        if (removeCard.getColor().equals("black")) {
            score -= 50;
        } else if (removeCard.getCardSign().equals("Skip") || removeCard.getCardSign().equals("Reverse") || removeCard.getColor().equals("+2")) {
            score -= 20;
        } else {
            score -= removeCard.getCardSign().charAt(0) - 48;
        }

    }

    /**
     * this method is return a card information player has in his hand card
     * @param index the number of card in
     * @return the card information of player hand cards
     */
    public Card getCard(int index){

        if(index < handCards.size() && index >= 0) {
            return handCards.get(index);
        } else {
            System.out.println("Please enter the right number.");
        }

        return null;
    }

    /**
     * this method is for see player is human or computer
     * @return if the player is computer the value is true otherwise is false
     */
    public boolean isAutoPlayer() {
        return autoPlayer;
    }

    /**
     * this method is for sending the player score
     * @return is the score value
     */
    public int getScore() {
        return score;
    }
}
