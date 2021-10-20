import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class BoardOfGame {

    /**
     * totalCards is the all cards collection and the method need for this collection
     * theFieldCard is the last card which set on field
     * theFieldColor since the card can be black so i decide to choose a variable for color of field
     * players is a list of all players and the information of them
     * movingTurn is charge when players moving and show that which player's turn it is
     * rotateDirection is show the direction of player should move
     * theDrawPenalty is save the penalty of take random card from cards collection
     */
    private CollectionOfCards totalCards;
    private Card theFieldCard;
    private String theFieldColor;
    private ArrayList<Player> players;
    private int movingTurn;
    private int rotateDirection;
    private int theDrawPenalty;

    /**
     * creat players information set the rotate direction give each player seven card from top of cards(hint : the card collection is shuffle after creating cards)
     * @param humanPlayersNumber the number of human players
     * @param computerPlayersNumber the number of computer players
     */
    public BoardOfGame(int humanPlayersNumber, int computerPlayersNumber) {

        totalCards = new CollectionOfCards();
        players = new ArrayList<>();

        for (int i = 0; i < humanPlayersNumber; i++) {
            players.add(new Human(i + 1 ));
        }

        for (int i = 0; i < computerPlayersNumber; i++) {
            players.add(new Computer(humanPlayersNumber + i + 1));
        }

        for (int i = 0; i < 7; i++) {

            for (Player player : players) {

                player.addCard(totalCards.getTopCard());

            }

        }

        while (true) {

            theFieldCard = totalCards.getRandomCard();
            if (!theFieldCard.getColor().equals("black")) {
                break;
            } else {
                totalCards.addCard(theFieldCard);
            }

        }

        theDrawPenalty = 0;

        theFieldColor = theFieldCard.getColor();

        movingTurn = new Random().nextInt(players.size());

        this.rotateDirection = 1;

        switch (theFieldCard.getCardSign()) {
            case "Reverse":
                tuningMovingTurn();
                break;
            case "Skip":
                rotateDirection *= -1;
                tuningMovingTurn();
                rotateDirection *= -1;
                break;
            case "+2":
                for (int i = 0; i < 2; i++) {
                    players.get(movingTurn).addCard(totalCards.getRandomCard());
                }
                tuningMovingTurn();
                break;
        }

    }

    /**
     * this is a method for playing the game
     */
    public void play() {

        while (true) {

            String rotation;

            if (rotateDirection == 1) {
                rotation = "up to down";
            } else {
                rotation = "down to up";
            }

            for (int i = 0; i < 2; i++) {
                System.out.println("\nrotate direction :" + rotation + "\n");
                System.out.println("(the field card)");
                showTheFieldCard();
                System.out.println();

                sleep(1000);

                for (int j = 0; j < players.size(); j++) {

                    if (j == movingTurn) {
                        players.get(movingTurn).showHandCards();
                    } else {
                        System.out.print("Player " + (j + 1) + " Cards");
                        showTheOtherPlayerCards(players.get(j).getHandCardsNumber());
                    }
                    System.out.println();

                }

                if(new GameProcess().endOfGame(players)){
                    return;
                }

                sleep(3000);


                boolean[] moveCondition = new GameProcess().checkMove(players.get(movingTurn), theFieldCard, theFieldColor);
                int cardIndex;

                if (players.get(movingTurn).isAutoPlayer()) {

                    cardIndex = ((Computer) players.get(movingTurn)).chooseCard(theFieldCard, moveCondition, theFieldColor);

                } else {

                    cardIndex = ((Human) players.get(movingTurn)).chooseCard(theFieldCard, moveCondition, theFieldColor);

                }

                if (cardIndex == -1) {

                    if (i == 0) {
                        players.get(movingTurn).addCard(totalCards.getTopCard());
                    } else {
                        tuningMovingTurn();
                    }

                } else if (players.get(movingTurn).getCard(cardIndex).getColor().equals("black")) {

                    setBlackCardProcess(cardIndex);
                    break;

                } else {

                    setOtherCardProcess(cardIndex);
                    break;

                }

            }


        }

    }

    /**
     * this method is receive the select card by player and if is not black card the program use this method to set the card and active it's effect
     * @param cardIndex the number of card which player choose from the cards of his hands
     */
    private void setOtherCardProcess(int cardIndex) {

        totalCards.addCard(theFieldCard);
        theFieldCard = players.get(movingTurn).getCard(cardIndex);
        theFieldColor = theFieldCard.getColor();
        players.get(movingTurn).removeCard(theFieldCard);

        if (players.get(movingTurn).getHandCardsNumber() == 1 && !players.get(movingTurn).isAutoPlayer()) {

            String answer;
            while (true) {
                System.out.print("Do you want to have one card in your hand card (yes/no):");
                answer = new Scanner(System.in).next();
                if (answer.equals("yes") || answer.equals("no")) {
                    break;
                } else {
                    System.out.println("enter yes or no");
                }
            }

            if (answer.equals("no")) {
                for (int i = 0; i < 2; i++) {
                    players.get(movingTurn).addCard(totalCards.getTopCard());
                }
            }

        }

        switch (theFieldCard.getCardSign()) {
            case "Skip":
                tuningMovingTurn();
                tuningMovingTurn();
                break;
            case "Reverse":
                rotateDirection *= -1;
                tuningMovingTurn();
                break;
            case "+2":
                tuningMovingTurn();
                int index = searchDrawCards();
                if (index != -1) {

                    String rotation;

                    if (rotateDirection == 1) {
                        rotation = "up to down";
                    } else {
                        rotation = "down to up";
                    }
                    System.out.println("\nrotate direction :" + rotation + "\n");
                    System.out.println("(the field card)");
                    showTheFieldCard();
                    System.out.println();

                    sleep(1000);

                    for (int j = 0; j < players.size(); j++) {

                        if (j == movingTurn) {
                            players.get(movingTurn).showHandCards();
                        } else {
                            System.out.print("Player " + (j + 1) + " Cards");
                            showTheOtherPlayerCards(players.get(j).getHandCardsNumber());
                        }
                        System.out.println();

                    }

                    sleep(3000);

                    if (players.get(movingTurn).isAutoPlayer()) {

                        theDrawPenalty += 2;

                        if (players.get(movingTurn).getCard(index).getCardSign().equals("+4")) {
                            setBlackCardProcess(index);
                        } else {
                            setOtherCardProcess(index);
                        }

                    } else {

                        String answer = ask();
                        if (answer.equals("yes")) {

                            theDrawPenalty += 2;
                            showTheFieldCard();
                            players.get(movingTurn).showHandCards();

                            while (true) {

                                index = getIndex();

                                if (players.get(movingTurn).getCard(index).getCardSign().equals("+4") || players.get(movingTurn).getCard(index).getCardSign().equals("+2")) {
                                    break;
                                } else {
                                    System.out.println("this card isn't a draw or wild draw card");
                                }

                            }

                            if (players.get(movingTurn).getCard(index).getCardSign().equals("+4")) {
                                setBlackCardProcess(index);
                            } else {
                                setOtherCardProcess(index);
                            }

                        } else {
                            index = -1;
                        }

                    }
                }
                if (index == -1) {

                    for (int i = 0; i < theDrawPenalty + 2; i++) {

                        players.get(movingTurn).addCard(totalCards.getRandomCard());

                    }

                    theDrawPenalty = 0;
                    tuningMovingTurn();
                }
                break;
            default:
                tuningMovingTurn();
                break;
        }

    }

    /**
     * this method is receive the select card by player and if is black card the program use this method to set the card and active it's effect
     * @param cardIndex the number of card which player choose from the cards of his hands
     */
    private void setBlackCardProcess(int cardIndex) {


        if (players.get(movingTurn).isAutoPlayer()) {

            int color = new Random().nextInt(4);
            switch (color) {
                case 0:
                    theFieldColor = "red";
                    break;
                case 1:
                    theFieldColor = "yellow";
                    break;
                case 2:
                    theFieldColor = "green";
                    break;
                case 3:
                    theFieldColor = "blue";
                    break;
            }

        } else {

            while (true) {

                System.out.print("Choose a color for the field color:");
                theFieldColor = new Scanner(System.in).next();

                if (!theFieldColor.equals("red") &&
                        !theFieldColor.equals("yellow") &&
                        !theFieldColor.equals("green") &&
                        !theFieldColor.equals("blue")) {

                    System.out.println("Enter the right color.");

                } else {
                    break;
                }

            }

        }

        totalCards.addCard(theFieldCard);
        theFieldCard = players.get(movingTurn).getCard(cardIndex);
        players.get(movingTurn).removeCard(theFieldCard);

        if (players.get(movingTurn).getHandCardsNumber() == 1 && !players.get(movingTurn).isAutoPlayer()) {

            String answer;
            while (true) {
                System.out.print("Do you want to have one card in your hand card (yes/no):");
                answer = new Scanner(System.in).next();
                if (answer.equals("yes") || answer.equals("no")) {
                    break;
                } else {
                    System.out.println("enter yes or no");
                }
            }

            if (answer.equals("no")) {
                for (int i = 0; i < 2; i++) {
                    players.get(movingTurn).addCard(totalCards.getTopCard());
                }
            }

        }

        tuningMovingTurn();

        int index = searchDrawCards();

        if (index != -1 && theFieldCard.getCardSign().equals("+4")) {

            String rotation;

            if (rotateDirection == 1) {
                rotation = "up to down";
            } else {
                rotation = "down to up";
            }
            System.out.println("\nrotate direction :" + rotation + "\n");
            System.out.println("(the field card)");
            showTheFieldCard();
            System.out.println();

            sleep(1000);

            for (int j = 0; j < players.size(); j++) {

                if (j == movingTurn) {
                    players.get(movingTurn).showHandCards();
                } else {
                    System.out.print("Player " + (j + 1) + " Cards");
                    showTheOtherPlayerCards(players.get(j).getHandCardsNumber());
                }
                System.out.println();

            }

            sleep(3000);

            if (players.get(movingTurn).isAutoPlayer()) {

                theDrawPenalty += 4;

                if (players.get(movingTurn).getCard(index).getCardSign().equals("+4")) {
                    setBlackCardProcess(index);
                } else {
                    setOtherCardProcess(index);
                }

            } else {

                String answer = ask();
                if (answer.equals("yes")) {

                    theDrawPenalty += 4;
                    showTheFieldCard();
                    players.get(movingTurn).showHandCards();

                    while (true) {

                        index = getIndex();

                        if (players.get(movingTurn).getCard(index).getCardSign().equals("+4") ||
                                (players.get(movingTurn).getCard(index).getCardSign().equals("+2") &&
                                        players.get(movingTurn).getCard(index).getColor().equals(theFieldColor))
                        ) {
                            break;
                        } else {
                            System.out.println("this card isn't a draw or wild draw card");
                        }

                    }

                    if (players.get(movingTurn).getCard(index).getCardSign().equals("+4")) {
                        setBlackCardProcess(index);
                    } else {
                        setOtherCardProcess(index);
                    }

                } else {
                    index = -1;
                }
            }
        }

        if (index == -1) {

            if (theFieldCard.getCardSign().equals("+4")) {
                for (int i = 0; i < theDrawPenalty + 4; i++) {

                    players.get(movingTurn).addCard(totalCards.getRandomCard());

                }
                tuningMovingTurn();
            }

            theDrawPenalty = 0;
        }

    }

    /**
     * this method used by two up methods and check to see the number card you enter is valid or not
     * @return the number card chosen by human players
     */
    private int getIndex() {
        int index;
        do {
            System.out.print("choose a draw or wild draw card:");
            index = new Scanner(System.in).nextInt() - 1;
            if (index < 0 || index >= players.get(movingTurn).getHandCardsNumber()) {
                System.out.println("Enter the right number for card");
            }
        } while (index < 0 || index >= players.get(movingTurn).getHandCardsNumber());
        return index;
    }

    /**
     * this method is only use for yes and no questions
     * @return the String which is yes or no
     */
    private String ask() {
        String answer;
        while (true) {
            System.out.print("Do you want escape from being penalty(yes/no):");
            answer = new Scanner(System.in).next();
            if (answer.equals("yes") || answer.equals("no")) {
                break;
            } else {
                System.out.println("enter yes or no");
            }
        }
        return answer;
    }

    /**
     * this method only Calculates the moving turn and control the moving turn value
     */
    private void tuningMovingTurn() {
        movingTurn += rotateDirection;

        if (movingTurn == players.size()) {
            movingTurn = 0;
        } else if (movingTurn == -1) {
            movingTurn = players.size() - 1;
        }

    }

    /**
     * this method is show the last card set by player on the field
     */
    private void showTheFieldCard() {

        ConsoleColor coloring = new ConsoleColor();

        System.out.println(coloring.getColor(theFieldColor) + "|$$$$$$$$$$$$$$$$$|   " + coloring.getAnsiReset());
        System.out.println(coloring.getColor(theFieldColor) + "|                 |   " + coloring.getAnsiReset());
        System.out.print(coloring.getColor(theFieldColor) + "|" + coloring.getAnsiReset());
        for (int j = 1; j < (18 - (theFieldCard.getCardSign().length() + theFieldColor.length())) / 2; j++) {
            System.out.print(" ");
        }
        System.out.print(coloring.getColor(theFieldColor) + theFieldColor + " " + theFieldCard.getCardSign() + coloring.getAnsiReset());
        for (int j = 1; j < (19 - (theFieldCard.getCardSign().length() + theFieldColor.length())) / 2; j++) {
            System.out.print(" ");
        }
        System.out.println(coloring.getColor(theFieldColor) + "|   " + coloring.getAnsiReset());
        System.out.println(coloring.getColor(theFieldColor) + "|                 |   " + coloring.getAnsiReset());
        System.out.println(coloring.getColor(theFieldColor) + "|$$$$$$$$$$$$$$$$$|   " + coloring.getAnsiReset());

    }

    /**
     * this method is show other player cards Backwards
     * @param playerCardsNumber is the number cards player has
     */
    private void showTheOtherPlayerCards(int playerCardsNumber) {

        ConsoleColor coloring = new ConsoleColor();

        System.out.println();

        for (int i = 0; i < 5; i++) {

            for (int j = 0; j < playerCardsNumber; j++) {
                System.out.print(coloring.getColor("purple") + "|$$$$$$$$$$$$$$$$$|   " + coloring.getAnsiReset());
            }
            System.out.println();
        }

    }

    /**
     * this method is for sleeping program
     * @param time is a integer which is according to millis seconds
     */
    private void sleep(int time) {

        try {
            Thread.sleep(time);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * this method is search that the player have a normal draw or draw wild in his hand for escaping form penalty
     * @return the int of first card that he can escape from penalty and if he don't have this card then the method return -1
     */
    private int searchDrawCards() {

        for (int i = 0; i < players.get(movingTurn).getHandCardsNumber(); i++) {

            if (players.get(movingTurn).getCard(i).getCardSign().equals("+4")) {

                return i;

            } else if (players.get(movingTurn).getCard(i).getCardSign().equals("+2")){

                if (theFieldCard.getCardSign().equals("+4")) {

                    if (theFieldColor.equals(players.get(movingTurn).getCard(i).getCardSign())) {
                        return i;
                    } else {
                        return -1;
                    }

                } else {

                    return i;

                }

            }

        }

        return -1;

    }

}
