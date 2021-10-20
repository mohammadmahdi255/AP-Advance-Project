public class BoardOfGame {

    /**
     * field is the table of game
     * listBlocks is the collections of four block of game table
     * listSymmetryBlocks is a list for seeing the player should enter the rotationBlockNumber and rotationDirection or shouldn't
     * endOfGame is a boolean to see game is continue or not
     * movingTurn is the int that hold the two player turn number
     */
    private char[][] field;
    private char[][][] listBlocks;
    private boolean[] listSymmetryBlocks;
    private boolean endOfGame = false;
    private int movingTurn;

    /**
     * a constructor for game table
     */
    public BoardOfGame(){

        field = new char[6][6];
        listBlocks = new char[4][3][3];

        listSymmetryBlocks = new boolean[]{true,true,true,true};

        movingTurn = 0;

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                field[i][j] = ' ';
            }
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    listBlocks[i][j][k] = ' ';
                }
            }
        }

        checkTheSymmetryOfTheBlocks();

    }

    /**
     * a method for player movement
     * @param x the horizontal coordinates
     * @param y the vertical coordinates
     * @param rotationBlockNumber the block number choose to rotation
     * @param rotationDirection the rotation Direction choose to rotation
     */
    public void playerMove (int x,int y,int rotationBlockNumber,char rotationDirection) {
        if (blockFind(x, y) >= 0) {
            if (field[y][x] == ' ' && listBlocks[blockFind(x, y)][y % 3][x % 3] == ' ') {
                if(movingTurn % 2 == 0){
                    listBlocks[blockFind(x, y)][y % 3][x % 3] = 'W';
                } else {
                    listBlocks[blockFind(x, y)][y % 3][x % 3] = 'B';
                }
                setBlockOnField();
                if(new GameProcess().EndOfGameCondition(field)){
                    endOfGame = true;
                }
                if(!endOfGame) {
                    rotationOfBlock(rotationBlockNumber, rotationDirection);
                    setBlockOnField();
                    if (new GameProcess().EndOfGameCondition(field)) {
                        endOfGame = true;
                    }
                }
                movingTurn++;
            } else {
                System.out.println("This position isn't empty on board game");
            }
        } else {
            System.out.println("This position doesn't exist on board game");
        }
    }

    /**
     * a method for set each blocks information on the field
     */
    private void setBlockOnField() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                field[i][j] = listBlocks[blockFind(j, i)][i % 3][j % 3];
            }
        }
        checkTheSymmetryOfTheBlocks();
    }

    /**
     * a method to fine the nut's block
     * @param x the horizontal coordinates
     * @param y the vertical coordinates
     * @return the block that the nut belong
     */
    public int blockFind(int x,int y) {

        if (x >= 0 && x < 3 && y >= 0 && y < 3) {
            return 0;
        } else if (x >= 3 && x < 6 && y >= 0 && y < 3) {
            return 1;
        } else if (x >= 0 && x < 3 && y >= 3 && y < 6) {
            return 2;
        } else if (x >= 3 && x < 6 && y >= 3 && y < 6) {
            return 3;
        } else {
            return -1;
        }

    }

    /**
     * for creating listSymmetryBlocks
     */
    private void checkTheSymmetryOfTheBlocks(){
        for (int i = 0; i < 4; i++) {

            char[][] tempBlockForRotationRoundClock = new char[3][3];
            char[][] tempBlockForRotationCounterClockwise = new char[3][3];

            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    tempBlockForRotationRoundClock[j][k] = listBlocks[i][j][k];
                    tempBlockForRotationCounterClockwise[j][k] = listBlocks[i][j][k];
                }
            }

            tempBlockForRotationRoundClock = rotationRoundClock(tempBlockForRotationRoundClock);
            tempBlockForRotationCounterClockwise = rotationCounterClockwise(tempBlockForRotationCounterClockwise);

            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    if(tempBlockForRotationRoundClock[j][k] != listBlocks[i][j][k]){
                        listSymmetryBlocks[i] = false;
                    } else if(tempBlockForRotationCounterClockwise[j][k] != listBlocks[i][j][k]){
                        listSymmetryBlocks[i] = false;
                    }
                    if(j == 2 && k == 2){
                        listSymmetryBlocks[i] = true;
                    }
                }
            }

        }
    }

    /**
     * a method for rotation block
     * @param rotationBlockNumber the block number choose to rotation
     * @param rotationDirection the rotation Direction choose to rotation
     */
    private void rotationOfBlock(int rotationBlockNumber,char rotationDirection) {
        if (rotationBlockNumber < 4 && rotationBlockNumber >= 0) {
            if (rotationDirection == 'R') {
                listBlocks[rotationBlockNumber] = rotationRoundClock(listBlocks[rotationBlockNumber]);
            } else if (rotationDirection == 'L') {
                listBlocks[rotationBlockNumber] = rotationCounterClockwise(listBlocks[rotationBlockNumber]);
            }
        }
    }

    /**
     * a method for rotation round clock
     * @param block the block information for rotation
     * @return the rotation block information
     */
    public char[][] rotationRoundClock(char[][] block){
        char[][] tempBlock = new char[3][3];
        for (int i = 0; i < 3; i++) {
            for (int k = 2; k >= 0; k--) {
                tempBlock[i][Math.abs(k-2)] = block[k][i];
            }
        }
        return tempBlock;
    }

    /**
     * a method for rotation counter clockwise
     * @param block the block information for rotation
     * @return the rotation block information
     */
    public char[][] rotationCounterClockwise(char[][] block) {
        char[][] tempBlock = new char[3][3];
        for (int i = 2; i >= 0; i--) {
            for (int k = 0; k < 3; k++) {
                tempBlock[Math.abs(i - 2)][k] = block[k][i];
            }
        }
        return tempBlock;
    }

    /**
     * a method for show the table of game
     */
    public void display(){
        System.out.println("  1  2  3   4  5  6");
        for (int i = 0; i < 6; i++) {
            System.out.print(i+1);
            for (int j = 0; j < 6; j++) {
                if(field[i][j] == ' '){
                    System.out.print("\u001B[32m"+" " + '\u229B'+"\u001B[0m");
                } else {
                    if(field[i][j] == 'B') {
                        System.out.print(" " + '\u29BF');
                    } else {
                        System.out.print(" " + '\u29BE');
                    }
                }
                if(j == 2){
                    System.out.print("\u001B[31m" + " " + '\u2022' + "\u001B[0m");
                }
            }
            System.out.println();
            if(i == 2){
                System.out.print(" ");
                for (int j = 0; j < 4; j++) {
                    System.out.print("\u001B[31m" + " " + '\u2022' + "\u001B[0m");
                }
                System.out.print("   ");
                for (int j = 0; j < 4; j++) {
                    System.out.print("\u001B[31m" + " " + '\u2022' + "\u001B[0m");
                }
                System.out.println();
            }
        }
    }

    /**
     * a getter for moving turn number
     * @return movingTurn
     */
    public int getMovingTurn() {
        return movingTurn;
    }

    /**
     * a getter for list symmetry blocks
     * @return listSymmetryBlocks[]
     */
    public boolean[] getListSymmetryBlocks() {
        return listSymmetryBlocks;
    }

    /**
     * a getter for is end of game or not
     * @return endOfGame
     */
    public boolean isEndOfGame() {
        return endOfGame;
    }

    /**
     * a getter for list blocks
     * @return listBlocks[][][]
     */
    public char[][][] getListBlocks() {
        return listBlocks;
    }

}
