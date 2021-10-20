public class GameProcess {

    /**
     * a method that check to see game is over or not and who is the winner
     * @param field is the table of game
     * @return is a boolean to see game is continue or not
     */
    public boolean EndOfGameCondition(char[][] field) {

        boolean winPlayer1 = false;
        boolean winPlayer2 = false;
        int[] arr1 = checkPossibilityOfEndGame(field, 'W');
        int[] arr2 = checkPossibilityOfEndGame(field, 'B');

        for (int i = 0; i < 8; i++) {
            if(arr1[i] == 5) {
                winPlayer1 = true;
                break;
            }
        }

        for (int i = 0; i < 8; i++) {
            if(arr2[i] == 5) {
                winPlayer2 = true;
                break;
            }
        }

        if (winPlayer1 && winPlayer2) {
            System.out.println("Player 1 && Player 2 has won");
            return true;
        } else if (winPlayer1) {
            System.out.println("Player 1 has won");
            return true;
        } else if (winPlayer2) {
            System.out.println("Player 2 has won");
            return true;
        } else {
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 6; j++) {
                    if (field[i][j] == ' ') {
                        break;
                    } else if (i == 5 && j == 5) {
                        System.out.println("Nobody wins");
                        return true;
                    }
                }
            }
            return false;
        }
    }

    /**
     * the nut on lines with slope = -1
     *
     * @param field the board game information
     * @param nut   the color of selected nut
     * @return the value of the player won or lose
     */
    public int[] checkPossibilityOfEndGame(char[][] field, char nut) {

        int[] arr = new int[]{0, 0, 0, 0, 0, 0, 0, 0};

        // the nuts in line y = x-1 : x > 1
        for (int i = 0, counter = 0; i < 5; i++) {
            if (field[i][i + 1] == nut) {
                counter++;
            } else if (arr[0] <= counter) {
                arr[0] = counter;
                counter = 0;
            }
            if (counter == 5) {
                arr[0] = counter;
                break;
            }
        }

        // the nuts in line y = x
        for (int i = 0, counter = 0; i < 6; i++) {
            if (field[i][i] == nut) {
                counter++;
            } else if (arr[1] <= counter) {
                arr[1] = counter;
                counter = 0;
            }
            if (counter == 5) {
                arr[1] = counter;
                break;
            }
        }

        // the nuts in line y = x+1
        for (int i = 0, counter = 0; i < 5; i++) {
            if (field[i + 1][i] == nut) {
                counter++;
            } else if (arr[2] <= counter) {
                arr[2] = counter;
                counter = 0;
            }
            if (counter == 5) {
                arr[2] = counter;
                break;
            }
        }

        // the nuts in line y = -x+6
        for (int i = 0, counter = 0; i < 5; i++) {
            if (field[4 - i][i] == nut) {
                counter++;
            } else if (arr[3] <= counter) {
                arr[3] = counter;
                counter = 0;
            }
            if (counter == 5) {
                arr[3] = counter;
                break;
            }
        }

        // the nuts in line y = -x+7
        for (int i = 0, counter = 0; i < 6; i++) {
            if (field[5 - i][i] == nut) {
                counter++;
            } else if (arr[4] <= counter) {
                arr[4] = counter;
                counter = 0;
            }
            if (counter == 5) {
                arr[4] = counter;
                break;
            }
        }

        // the nuts in line y = -x+8 : x > 1
        for (int i = 1, counter = 0; i < 6; i++) {
            if (field[6 - i][i] == nut) {
                counter++;
            } else if (arr[5] <= counter) {
                arr[5] = counter;
                counter = 0;
            }
            if (counter == 5) {
                arr[5] = counter;
                break;
            }
        }

        // the nuts in line y = c
        for (int i = 0; i < 6; i++) {
            int counter = 0;
            for (int j = 0; j < 6; j++) {
                if (field[i][j] == nut) {
                    counter++;
                } else if (arr[6] <= counter) {
                    arr[6] = counter;
                    counter = 0;
                }
                if (counter == 5) {
                    arr[6] = counter;
                    break;
                }
            }
        }

        // the nuts in line x = c
        for (int i = 0; i < 6; i++) {
            int counter = 0;
            for (int j = 0; j < 6; j++) {
                if (field[j][i] == nut) {
                    counter++;
                } else if (arr[7] <= counter) {
                    arr[7] = counter;
                    counter = 0;
                }
                if (counter == 5) {
                    arr[7] = counter;
                    break;
                }
            }
        }

        return arr;
    }
}
