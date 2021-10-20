import java.util.ArrayList;

public class GameProcess {

    /**
     * this method is check to see what kind of move player can go like he can use wild draw or he should draw a card and etc
     * @param player is the player information that is his turn
     * @param theFieldCard the last card set on field
     * @param theFieldColor the color card of field
     * @return is a array of boolean that show what move player can go
     */
    public boolean[] checkMove(Player player, Card theFieldCard, String theFieldColor){

        boolean moveWildDraw = false;
        boolean normalMove = false;

        for (int i = 0; i < player.getHandCardsNumber(); i++){

            if(player.getCard(i).getCardSign().equals("+4")) {
                moveWildDraw = true;
                normalMove = false;
            }

        }

        for (int i = 0; i < player.getHandCardsNumber(); i++) {
            if(player.getCard(i).getCardSign().equals(theFieldCard.getCardSign())){
                moveWildDraw = false;
                normalMove = true;
            } else if(player.getCard(i).getColor().equals(theFieldColor)){
                moveWildDraw = false;
                normalMove = true;
            } else if(player.getCard(i).getCardSign().equals("WildColor")){
                moveWildDraw = false;
                normalMove = true;
            }
        }

        return new boolean[]{moveWildDraw,normalMove};

    }

    /**
     * this method check to see game is end or not and if game is end show the score of each player and winner
     * @param players list of all players information
     * @return a boolean for program to end the game cycle
     */
    public boolean endOfGame(ArrayList<Player> players){

        for (Player player:players) {

            if(player.getHandCardsNumber() == 0){

                System.out.println();

                for (int i = 0; i < players.size(); i++) {

                    if(players.get(i).getHandCardsNumber() == 0){
                        System.out.println("Player "+(i+1)+" point :"+players.get(i).getScore()+" winner");
                    } else {
                        System.out.println("Player "+(i+1)+" point :"+players.get(i).getScore());
                    }

                }

                System.out.println();

                return true;
            }

        }

        return false;
    }


}
