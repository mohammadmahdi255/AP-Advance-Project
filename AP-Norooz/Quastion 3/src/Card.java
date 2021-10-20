public class Card {

    /**
     * color the color name of card
     * cardSing the sing that wright on card
     */
    private String color;
    private String cardSign;

    /**
     * creat a card
     * @param color is the color name of card
     * @param cardSign is the sing that wright on card
     */
    public Card(String color,String cardSign){

        this.color = color;
        this.cardSign = cardSign;

    }

    /**
     * this method return the card sing
     * @return is the sing of card
     */
    public String getCardSign() {
        return cardSign;
    }

    /**
     * this method return the card color name
     * @return is the color name of card
     */
    public String getColor() {
        return color;
    }

}
