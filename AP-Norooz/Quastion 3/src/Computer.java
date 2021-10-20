import java.util.Random;

public class Computer extends Player{

    /**
     * creat a computer console
     * @param playerNumber is the player number in game
     */
    public Computer(int playerNumber) {
        super(playerNumber, true);
    }

    /**
     * this method automatic choose a card for the player's hand cards
     * @param theFieldCard this is the information of the last card set on field
     * @param moveCondition this is a boolean that specifies what kind of move player can go
     * @param theFieldColor this is the color of the field
     * @return is the index of player's hand cards
     */
    public int chooseCard(Card theFieldCard, boolean[] moveCondition, String theFieldColor) {


        if(moveCondition[0] && !moveCondition[1]) {


            while (true){

                int index = new Random().nextInt(getHandCardsNumber());

                    if (getCard(index).getCardSign().equals("+4")){
                        return index;
                    }

                }


        } else if (moveCondition[1]){


                while (true){

                    int index = new Random().nextInt(getHandCardsNumber());

                    if (!getCard(index).getCardSign().equals("+4") &&
                            (getCard(index).getCardSign().equals("WildColor") ||
                                    getCard(index).getCardSign().equals(theFieldCard.getCardSign()) ||
                                    getCard(index).getColor().equals(theFieldColor) )
                    ){
                        return index;
                    }

                }


        } else {

            return -1;

        }

    }

}
