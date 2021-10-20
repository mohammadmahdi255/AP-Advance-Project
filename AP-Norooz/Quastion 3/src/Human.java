import java.util.Scanner;

public class Human extends Player{

    /**
     * creat a human console
     * @param playerNumber is the player number in game
     */
    public Human(int playerNumber) {
        super(playerNumber, false);
    }

    /**
     * this method let player choose a card for his hand
     * @param theFieldCard this is the information of the last card set on field
     * @param moveCondition this is a boolean that specifies what kind of move player can go
     * @param theFieldColor this is the color of the field
     * @return is the index of player's hand cards
     */
    public int chooseCard(Card theFieldCard, boolean[] moveCondition, String theFieldColor) {

        int index;

        if(moveCondition[0] && !moveCondition[1]) {

            while (true) {

                Card toBeSet;
                System.out.print("Choose a card Number: ");
                index = new Scanner(System.in).nextInt() - 1;

                toBeSet = getCard(index);

                if(toBeSet != null) {

                    if (toBeSet.getCardSign().equals("+4")) {

                        return index;

                    } else {

                        System.out.println("Choose wild draw card.");

                    }

                }

            }

        } else if (moveCondition[1]){

            while (true) {

                Card toBeSet;
                System.out.print("Choose a card Number: ");
                index = new Scanner(System.in).nextInt() - 1;

                toBeSet = getCard(index);

                if(toBeSet != null) {

                    if (toBeSet.getCardSign().equals("WildColor")) {

                        return index;

                    } else if (toBeSet.getCardSign().equals(theFieldCard.getCardSign()) || toBeSet.getColor().equals(theFieldColor)) {

                        return index;

                    } else if (toBeSet.getCardSign().equals("+4")) {

                        System.out.println("You cannot play with this card right now.");

                    } else {

                        System.out.println("This card sign and color not match with the card on the field.");

                    }

                }

            }

        } else {

           index = -1;

        }

        return index;

    }

}
