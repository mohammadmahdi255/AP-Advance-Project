import java.util.ArrayList;

public class Map {

    /**
     * all nut position
     * the number of turn
     * the information of two players
     */
    private char[][] map;
    private int movingTurn;
    private Player player1;
    private Player player2;

    /**
     * constructor
     * @param player1 information of player1 nuts and color
     * @param player2 information of player2 nuts and color
     */
    public Map(Player player1, Player player2) {
        map = new char[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                map[i][j] = ' ';
            }
        }
        this.player1 = player1;
        this.player2 = player2;
        map[3][3] = 'W';
        map[4][4] = 'W';
        map[4][3] = 'B';
        map[3][4] = 'B';
        player1.getAllPlayerNuts().add(new Nut(3, 'D', 'W'));
        player1.getAllPlayerNuts().add(new Nut(4, 'E', 'W'));
        player2.getAllPlayerNuts().add(new Nut(4, 'D', 'B'));
        player2.getAllPlayerNuts().add(new Nut(3, 'E', 'B'));
        movingTurn = 0;
    }

    /**
     * this method is for player move turn and set players move turn
     *
     * @param width           is width of game table and between 0 - 9
     * @param length          is length of game table and is one of this set members [A,B,C,D,E,F,H,G]
     */
    public void playerMove(int width, char length) {

        Player movePlayer;
        Player otherPlayer;

        if (movingTurn % 2 == 0) {
            movePlayer = player1;
            otherPlayer = player2;
        } else {
            movePlayer = player2;
            otherPlayer = player1;
        }

        Nut move = new Nut(width - 1, length, movePlayer.getPlayerNutColor());
        if (map[move.getyPosition()][move.getxPosition()] == '*') {
            map[move.getyPosition()][move.getxPosition()] = move.getColor();
            MotionCheck moveCheck = new MotionCheck(move);
            if (moveCheck.checkSpacePosition(move, map, otherPlayer)) {
                moveCheck.changeCheck(move, movePlayer, otherPlayer, map);
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (moveCheck.getNutNeighbor()[i][j]) {
                            for (int k = 1; k < moveCheck.getDistance()[i][j]; k++) {
                                if (i == 0 && j == 0) {
                                    if (map[move.getyPosition() - k][move.getxPosition() - k] == otherPlayer.getPlayerNutColor()) {
                                        map[move.getyPosition() - k][move.getxPosition() - k] = move.getColor();
                                    }
                                } else if (i == 0 && j == 1) {
                                    if (map[move.getyPosition() - k][move.getxPosition()] == otherPlayer.getPlayerNutColor()) {
                                        map[move.getyPosition() - k][move.getxPosition()] = move.getColor();
                                    }
                                } else if (i == 0) {
                                    if (map[move.getyPosition() - k][move.getxPosition() + k] == otherPlayer.getPlayerNutColor()) {
                                        map[move.getyPosition() - k][move.getxPosition() + k] = move.getColor();
                                    }
                                } else if (i == 1 && j == 0) {
                                    if (map[move.getyPosition()][move.getxPosition() - k] == otherPlayer.getPlayerNutColor()) {
                                        map[move.getyPosition()][move.getxPosition() - k] = move.getColor();
                                    }
                                } else if (i == 1 && j == 2) {
                                    if (map[move.getyPosition()][move.getxPosition() + k] == otherPlayer.getPlayerNutColor()) {
                                        map[move.getyPosition()][move.getxPosition() + k] = move.getColor();
                                    }
                                } else if (i == 2 && j == 0) {
                                    if (map[move.getyPosition() + k][move.getxPosition() - k] == otherPlayer.getPlayerNutColor()) {
                                        map[move.getyPosition() + k][move.getxPosition() - k] = move.getColor();
                                    }
                                } else if (i == 2 && j == 1) {
                                    if (map[move.getyPosition() + k][move.getxPosition()] == otherPlayer.getPlayerNutColor()) {
                                        map[move.getyPosition() + k][move.getxPosition()] = move.getColor();
                                    }
                                } else {
                                    if (map[move.getyPosition() + k][move.getxPosition() + k] == otherPlayer.getPlayerNutColor()) {
                                        map[move.getyPosition() + k][move.getxPosition() + k] = move.getColor();
                                    }
                                }
                            }
                        }
                    }
                }

                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (map[i][j] == '*') {
                            map[i][j] = ' ';
                        }
                    }
                }

                otherPlayer.getAllPlayerNuts().clear();

                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (map[i][j] != movePlayer.getPlayerNutColor() && map[i][j] != ' ') {
                            if (movePlayer.getPlayerNutColor() == 'W') {
                                otherPlayer.getAllPlayerNuts().add(new Nut(i, (char) (j + 65), 'B'));
                            } else {
                                otherPlayer.getAllPlayerNuts().add(new Nut(i, (char) (j + 65), 'W'));
                            }
                        }
                    }
                }

                movePlayer.getAllPlayerNuts().clear();
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (map[i][j] != otherPlayer.getPlayerNutColor() && map[i][j] != ' ') {
                            if (otherPlayer.getPlayerNutColor() == 'W') {
                                movePlayer.getAllPlayerNuts().add(new Nut(i, (char) (j + 65), 'B'));
                            } else {
                                movePlayer.getAllPlayerNuts().add(new Nut(i, (char) (j + 65), 'W'));
                            }
                        }
                    }
                }

                movingTurn++;
            }
        } else {
            System.out.println("You cannot move to this position");
        }
    }

    /**
     * this method is for show the place can choose by player
     *
     */
    public void interLockableLocations() {

        Player movePlayer;
        Player otherPlayer;

        if (movingTurn % 2 == 0) {
            movePlayer = player1;
            otherPlayer = player2;
        } else {
            movePlayer = player2;
            otherPlayer = player1;
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Nut tempNut = new Nut(i, (char) (j + 65), movePlayer.getPlayerNutColor());
                MotionCheck moveCheck = new MotionCheck(tempNut);
                if (moveCheck.emptySpaceExist(tempNut, map) && moveCheck.checkSpacePosition(tempNut, map, otherPlayer)) {
                    moveCheck.changeCheck(tempNut, movePlayer, otherPlayer, map);
                    for (int k = 0; k < 3; k++) {
                        for (int l = 0; l < 3; l++) {
                            if (moveCheck.getNutNeighbor()[k][l]) {
                                map[i][j] = '*';
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * this method is for display
     */
    public void mapDisplay() {
        System.out.println("  A  B  C  D  E  F  G  H");
        for (int i = 0; i < 8; i++) {
            System.out.print(i+1);
            for (int j = 0; j < 8; j++) {
                if(map[i][j] != ' ') {
                    if(map[i][j] == 'B') {
                        System.out.print(" " + '\u29BF');
                    } else if (map[i][j] == 'W'){
                        System.out.print(" " + '\u29BE');
                    } else {
                        System.out.print("\u001B[32m"+" " + '\u229B'+"\u001B[0m");
                    }
                } else {
                    System.out.print("\u001B[31m"+" "+'\u229D'+"\u001B[0m");
                }
            }
            System.out.println();
        }
    }

    /**
     *
     * @return the boolean of the game is over or not
     */
    public boolean endOfGame(){
        int nutsPlayer1 = 0 , nutsPlayer2 = 0;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(map[i][j] == 'W'){
                    nutsPlayer1++;
                } else if(map[i][j] == 'B'){
                    nutsPlayer2++;
                }
            }
        }

            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if(map[i][j] == '*'){
                        return false;
                    }
                }
            }

            if(nutsPlayer1+nutsPlayer2 == 64 || nutsPlayer1 == 0 || nutsPlayer2 == 0) {
                System.out.println("The game has been end");
                System.out.println("Player 1 nuts: " + nutsPlayer1);
                System.out.println("Player 2 nuts: " + nutsPlayer2);
                if (nutsPlayer2 > nutsPlayer1) {
                    System.out.println("Player 2 is winner");
                } else if (nutsPlayer2 < nutsPlayer1) {
                    System.out.println("Player 1 is winner");
                } else {
                    System.out.println("Player 2 draw Player 1");
                }
                return true;
            } else {
                if(movingTurn % 2 == 0) {
                    System.out.println("Player 1 Pass");
                } else {
                    System.out.println("Player 2 Pass");
                }
                movingTurn++;
                interLockableLocations();
                mapDisplay();
                return false;
            }
    }

    /**
     *
     * @return int the number of turn
     */
    public int getMovingTurn() {
        return movingTurn;
    }

    /**
     *
     * @return the play field information
     */
    public char[][] getMap() {
        return map;
    }
}

