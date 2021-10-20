import java.util.Scanner;

public class Main {

    public static void main (String[] args){

        while (true) {

            System.out.println("1) MultiPlayer Game");
            System.out.println("2) Single Game With Computer");
            System.out.println("3) Exit From Game");
            System.out.println("Choose:");

            Scanner reader = new Scanner(System.in);
            BoardOfGame boardOfGame = new BoardOfGame();
            int xPos , yPos , rotationBlockNumber, menuNumber;
            char rotationDirection;
            String str;

            menuNumber = reader.nextInt();
            reader.nextLine();

            while (menuNumber == 1) {
                boardOfGame.display();

                if (boardOfGame.isEndOfGame()) {
                    menuNumber = 0;
                    break;
                }

                if (boardOfGame.getMovingTurn() % 2 == 0) {
                    System.out.println("Player 1 turn:");
                } else {
                    System.out.println("Player 2 turn:");
                }
                str = reader.nextLine();
                xPos = (int) (str.charAt(0)) - 49;
                yPos = (int) (str.charAt(2)) - 49;
                rotationBlockNumber = -1;
                rotationDirection = ' ';
                if (str.length() >= 4) {
                    rotationBlockNumber = (int) str.charAt(4) - 49;
                }
                if (str.length() >= 6) {
                    rotationDirection = str.charAt(6);
                }


                move(boardOfGame, xPos, yPos, rotationBlockNumber, rotationDirection);

            }

            while (menuNumber == 2){
                boardOfGame.display();

                if (boardOfGame.isEndOfGame()) {
                    menuNumber = 0;
                    break;
                }

                if (boardOfGame.getMovingTurn() % 2 == 0) {
                    System.out.println("Player 1 turn:");
                    str = reader.nextLine();
                    xPos = (int) (str.charAt(0)) - 49;
                    yPos = (int) (str.charAt(2)) - 49;
                    rotationBlockNumber = -1;
                    rotationDirection = ' ';
                    if (str.length() >= 4) {
                        rotationBlockNumber = (int) str.charAt(4) - 49;
                    }
                    if (str.length() >= 6) {
                        rotationDirection = str.charAt(6);
                    }
                } else {
                    System.out.println("Player 2 turn:");
                    str = new Computer(boardOfGame.getListBlocks()).processAllTheConsecutiveBeads();
                    System.out.println(str);
                    xPos = (int) (str.charAt(0)) - 49;
                    yPos = (int) (str.charAt(2)) - 49;
                    rotationBlockNumber = (int) str.charAt(4) - 49;
                    rotationDirection = str.charAt(6);
                }


                move(boardOfGame, xPos, yPos, rotationBlockNumber, rotationDirection);
            }

            if(menuNumber == 3){
                break;
            }

        }
    }

    /**
     * a method for sent the players movement
     * @param boardOfGame the playing field
     * @param xPos the horizontal coordinates
     * @param yPos the vertical coordinates
     * @param rotationBlockNumber the block choose to rotation
     * @param rotationDirection the rotation direction
     */
    private static void move(BoardOfGame boardOfGame, int xPos, int yPos, int rotationBlockNumber, char rotationDirection) {
        if ((rotationBlockNumber != 0 && rotationBlockNumber != 1 && rotationBlockNumber != 2 && rotationBlockNumber != 3) || (rotationDirection != 'R' && rotationDirection != 'L')) {
            for (int i = 0; i < 4; i++) {
                if (boardOfGame.getListSymmetryBlocks()[i]) {
                    boardOfGame.playerMove(xPos, yPos, 6, ' ');
                    break;
                } else if (i + 1 == 4) {
                    System.out.println("you should choose a block for rotation");
                }
            }
        } else {
            boardOfGame.playerMove(xPos, yPos, rotationBlockNumber, rotationDirection);
        }
    }

}
