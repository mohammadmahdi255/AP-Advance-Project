import java.util.ArrayList;
import java.util.Random;

public class CollectionOfCards {

    /**
     * listCards is the storage of cards
     */
    private ArrayList<Card> listCards;

    /**
     * creat all card that exist in game
     */
    public CollectionOfCards() {

        listCards = new ArrayList<>();

        creatCardsOfGame("red");
        creatCardsOfGame("yellow");
        creatCardsOfGame("green");
        creatCardsOfGame("blue");

        shuffle();

    }

    /**
     * this method shuffle the list cards
     */
    private void shuffle(){

        for (int i = 0; i < 5*listCards.size(); i++) {

            Card tempCard = getRandomCard();
            removeCard(tempCard);
            listCards.add(0,tempCard);

        }

    }

    /**
     * this method is creat card
     * @param color the color name of card
     */
    private void creatCardsOfGame(String color){

        listCards.add(new Card(color,"0"));

        for (int i = 0; i < 2; i++) {
            for (int j = 1; j < 10; j++) {
                listCards.add(new Card(color,j+""));
            }
            listCards.add(new Card(color,"Skip"));
            listCards.add(new Card(color,"Reverse"));
            listCards.add(new Card(color,"+2"));
        }

        listCards.add(new Card("black","WildColor"));
        listCards.add(new Card("black","+4"));


    }

    /**
     * this method add a card to storage
     * @param addCard is a card is going to add
     */
    public void addCard(Card addCard){

        listCards.add(0,addCard);

    }

    /**
     * this method is return a random card form storage cards
     * @return is a card of storage cards
     */
    public Card getRandomCard(){

        int index = new Random().nextInt(listCards.size());
        Card randomCard = listCards.get(index);
        removeCard(randomCard);
        return randomCard;

    }

    /**
     * this method is return the top card in storage cards
     * @return is a card form top of storage cards
     */
    public Card getTopCard(){

        Card topCard = listCards.get(listCards.size()-1);
        removeCard(topCard);
        return topCard;

    }

    /**
     * this method is remove a card from storage cards
     * @param removeCard is a card is going to remove
     */
    private void removeCard(Card removeCard){
        listCards.remove(removeCard);
    }



}
