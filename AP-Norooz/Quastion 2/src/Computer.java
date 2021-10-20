public class Computer extends GameProcess {

    /**
     * everyPossibilityFields is all possibility field that computer can change the old field to one of this possibility field
     * allRotationsPossibility is an array with all rotations possibility
     * listConsecutiveBeads is the list of all number nut in one line
     */
    private char[][][] everyPossibilityFields;
    private final String[] allRotationsPossibility;
    private int[][] listConsecutiveBeads;

    /**
     * a method for creat all possibility of field after rotation
     * @param listBlocks is the collections of four block of game table
     */
    public Computer(char[][][] listBlocks) {
        everyPossibilityFields = new char[8][6][6];
        allRotationsPossibility = new String[]{"1R", "1L", "2R", "2L", "3R", "3L", "4R", "4L"};
        listConsecutiveBeads = new int[8][8];
        for (int i = 0; i < 8; i++) {
            char typeOfRotation = allRotationsPossibility[i].charAt(1);
            if (typeOfRotation == 'R') {
                listBlocks[i / 2] = new BoardOfGame().rotationRoundClock(listBlocks[i / 2]);
            } else {
                listBlocks[i / 2] = new BoardOfGame().rotationCounterClockwise(listBlocks[i / 2]);
            }
            for (int j = 0; j < 6; j++) {
                for (int k = 0; k < 6; k++) {
                    everyPossibilityFields[i][j][k] = listBlocks[new BoardOfGame().blockFind(k, j)][j % 3][k % 3];
                }
            }
            if (typeOfRotation == 'R') {
                listBlocks[i / 2] = new BoardOfGame().rotationCounterClockwise(listBlocks[i / 2]);
            } else {
                listBlocks[i / 2] = new BoardOfGame().rotationRoundClock(listBlocks[i / 2]);
            }
        }
        //display();
    }

    /**
     * a method for computer movement
     * @return a string which hold the coordinates of computer move and the rotation block information
     */
    public String processAllTheConsecutiveBeads() {
        for (int i = 0; i < 8; i++) {
            listConsecutiveBeads[i] = super.checkPossibilityOfEndGame(everyPossibilityFields[i], 'B');
        }

        int max = 0;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (listConsecutiveBeads[i][j] > max) {
                    max = listConsecutiveBeads[i][j];
                }
            }
        }

        for (; max >= 0; max--) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (listConsecutiveBeads[i][j] == max) {
                        String str = findTheMovePosition(i, j);
                        if (!str.equals("null")) {
                            return str + " " + allRotationsPossibility[i].charAt(0) + " " + allRotationsPossibility[i].charAt(1);
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * a method to see which move is creat the maximum black nut in one line
     * @param i the vertical coordinates of listConsecutiveBeads
     * @param j the horizontal coordinates of listConsecutiveBeads
     * @return a string which only hold the coordinates of computer move
     */
    public String findTheMovePosition(int i,int j) {

        int x, y;
        if (j == 0) {
            for (int k = 0; k < 5; k++) {
                if (everyPossibilityFields[i][k][k + 1] == ' ') {
                    x = (k + 1) % 3;
                    y = k % 3;
                    return getString(i, x, y, k + 1, k);
                }
            }
        } else if (j == 1) {
            for (int k = 0; k < 6; k++) {
                if (everyPossibilityFields[i][k][k] == ' ') {
                    x = k % 3;
                    y = k % 3;
                    return getString(i, x, y, k, k);
                }
            }
        } else if (j == 2) {
            for (int k = 0; k < 5; k++) {
                if (everyPossibilityFields[i][k + 1][k] == ' ') {
                    x = k % 3;
                    y = (k + 1) % 3;
                    return getString(i, x, y, k, k + 1);
                }
            }
        } else if (j == 3) {
            for (int k = 0; k < 5; k++) {
                if (everyPossibilityFields[i][4 - k][k] == ' ') {
                    x = k % 3;
                    y = (4 - k) % 3;
                    return getString(i, x, y, k, 4 - k);
                }
            }
        } else if (j == 4) {
            for (int k = 0; k < 6; k++) {
                if (everyPossibilityFields[i][5 - k][k] == ' ') {
                    x = k % 3;
                    y = (5 - k) % 3;
                    return getString(i, x, y, k, 5 - k);
                }
            }
        } else if (j == 5) {
            for (int k = 1; k < 6; k++) {
                if (everyPossibilityFields[i][6 - k][k] == ' ') {
                    x = k % 3;
                    y = (6 - k) % 3;
                    return getString(i, x, y, k, 6 - k);
                }
            }
        } else if (j == 6) {
            for (int k = 0; k < 6; k++) {
                for (int l = 0; l < 6; l++) {
                    if (everyPossibilityFields[i][k][l] == ' ') {
                        x = l % 3;
                        y = k % 3;
                        return getString(i, x, y, l, k);
                    }
                }
            }
        } else if (j == 7) {
            for (int k = 0; k < 6; k++) {
                for (int l = 0; l < 6; l++) {
                    if (everyPossibilityFields[i][l][k] == ' ') {
                        x = k % 3;
                        y = l % 3;
                        return getString(i, x, y, k, l);
                    }
                }
            }
        }

        return "null";

    }

    /**
     * a method to undo the rotation move
     * @param i the int which show rotation possibility number
     * @param x the block vertical coordinates of listConsecutiveBeads
     * @param y the block horizontal coordinates of listConsecutiveBeads
     * @param k the field vertical coordinates of listConsecutiveBeads
     * @param l the field horizontal coordinates of listConsecutiveBeads
     * @return a string which only hold the coordinates of computer move
     */
    private String getString(int i, int x, int y,int k ,int l) {

        int xPos = x , yPos = y;
        int temp = new BoardOfGame().blockFind(k,l);

        if (i % 2 == 0 && allRotationsPossibility[i].charAt(0) == (char)(temp+49)) {
            xPos = y;
            yPos = Math.abs(x - 2);
        } else if(i % 2 == 1 && allRotationsPossibility[i].charAt(0) == (char)(temp+49)) {
            yPos = x;
            xPos = Math.abs(y - 2);
        }

        if(temp == 1){
            xPos+=3;
        } else if(temp == 2){
            yPos+=3;
        } else if(temp == 3){
            xPos+=3;
            yPos+=3;
        }

        return (xPos+1) + " " + (yPos+1);
    }

    /**
     * this method is for showing the all possibility field which is disable this method is for the time we want to see what happen
     * when computer move
     */
    /*public void display() {
        for (int k = 0; k < 8; k++) {
            System.out.println("  1 2 3   4 5 6");
            for (int i = 0; i < 6; i++) {
                System.out.print(i + 1);
                for (int j = 0; j < 6; j++) {
                    if (everyPossibilityFields[k][i][j] == ' ') {
                        System.out.print(" .");
                    } else {
                        System.out.print(" " + everyPossibilityFields[k][i][j]);
                    }
                    if (j == 2) {
                        System.out.print(" |");
                    }
                }
                System.out.println();
                if (i == 2) {
                    System.out.println("================");
                }
            }
        }
    }*/

}
