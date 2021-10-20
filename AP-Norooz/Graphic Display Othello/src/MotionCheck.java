

public class MotionCheck {

    private int widthOfOrigin_1;
    private int widthOfOrigin_2;
    private boolean[][] nutNeighbor;
    private int[][] distance;

    public MotionCheck(Nut move) {
        widthOfOrigin_1 = -move.getxPosition() + move.getyPosition();
        widthOfOrigin_2 = move.getxPosition() + move.getyPosition();
        nutNeighbor = new boolean[3][3];
        distance = new int[][]{{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
    }

    public boolean emptySpaceExist(Nut move, char[][] map) {

        return map[move.getyPosition()][move.getxPosition()] == ' ';

    }

    public boolean checkSpacePosition(Nut move, char[][] map,Player otherPlayer) {

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                nutNeighbor[i][j] = false;
            }
        }

        if(move.getyPosition()<7 && move.getxPosition()<7) {
            nutNeighbor[2][2] = (map[move.getyPosition() + 1][move.getxPosition() + 1] == otherPlayer.getPlayerNutColor());
        }
        if(move.getxPosition()<7) {
            nutNeighbor[1][2] = (map[move.getyPosition()][move.getxPosition() + 1] == otherPlayer.getPlayerNutColor());
        }
        if(move.getyPosition()<7) {
            nutNeighbor[2][1] = (map[move.getyPosition() + 1][move.getxPosition()] == otherPlayer.getPlayerNutColor());
        }
        if(0<move.getyPosition() && move.getxPosition()<7) {
            nutNeighbor[0][2] = (map[move.getyPosition() - 1][move.getxPosition() + 1] == otherPlayer.getPlayerNutColor());
        }
        if(move.getyPosition()<7 && 0<move.getxPosition()) {
            nutNeighbor[2][0] = (map[move.getyPosition() + 1][move.getxPosition() - 1] == otherPlayer.getPlayerNutColor());
        }
        if(0<move.getxPosition()) {
            nutNeighbor[1][0] = (map[move.getyPosition()][move.getxPosition() - 1] == otherPlayer.getPlayerNutColor());
        }
        if(0<move.getyPosition()) {
            nutNeighbor[0][1] = (map[move.getyPosition() - 1][move.getxPosition()] == otherPlayer.getPlayerNutColor());
        }
        if(0<move.getyPosition() && 0<move.getxPosition()) {
            nutNeighbor[0][0] = (map[move.getyPosition() - 1][move.getxPosition() - 1] == otherPlayer.getPlayerNutColor());
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (nutNeighbor[i][j])
                    return true;
            }
        }
        return false;
    }

    public void changeCheck(Nut move, Player movePlayer, Player otherPlayer, char[][] map) {
        int hold;
        int[][] directDistance = new int[][]{{130, 130, 130}, {130, 130, 130}, {130, 130, 130}};
        for (Nut navigator : movePlayer.getAllPlayerNuts()) {
            hold = (int) (Math.pow(navigator.getyPosition() - move.getyPosition(), 2)
                    + Math.pow(navigator.getxPosition() - move.getxPosition(), 2));

            if (navigator.getyPosition() == navigator.getxPosition() + widthOfOrigin_1) {

                if (navigator.getxPosition() > move.getxPosition()) {

                    if (directDistance[2][2] > hold && nutNeighbor[2][2]) {
                        directDistance[2][2] = hold;
                        distance[2][2] = navigator.getxPosition() - move.getxPosition();
                    }

                } else {

                    if (directDistance[0][0] > hold && nutNeighbor[0][0]) {
                        directDistance[0][0] = hold;
                        distance[0][0] = move.getxPosition() - navigator.getxPosition();
                    }

                }
            } else if (navigator.getyPosition() == -navigator.getxPosition() + widthOfOrigin_2) {

                if (navigator.getxPosition() > move.getxPosition()) {

                    if (directDistance[0][2] > hold && nutNeighbor[0][2]) {
                        directDistance[0][2] = hold;
                        distance[0][2] = navigator.getxPosition() - move.getxPosition();
                    }

                } else {

                    if (directDistance[2][0] > hold && nutNeighbor[2][0]) {
                        directDistance[2][0] = hold;
                        distance[2][0] = move.getxPosition() - navigator.getxPosition();
                    }

                }
            } else if (navigator.getyPosition() == move.getyPosition()) {

                if (navigator.getxPosition() > move.getxPosition()) {

                    if (directDistance[1][2] > hold && nutNeighbor[1][2]) {
                        directDistance[1][2] = hold;
                        distance[1][2] = navigator.getxPosition() - move.getxPosition();
                    }

                } else {

                    if (directDistance[1][0] > hold && nutNeighbor[1][0]) {
                        directDistance[1][0] = hold;
                        distance[1][0] = move.getxPosition() - navigator.getxPosition();
                    }

                }
            } else if (navigator.getxPosition() == move.getxPosition()) {

                if (navigator.getyPosition() > move.getyPosition()) {

                    if (directDistance[2][1] > hold && nutNeighbor[2][1]) {
                        directDistance[2][1] = hold;
                        distance[2][1] = navigator.getyPosition() - move.getyPosition();
                    }

                } else {

                    if (directDistance[0][1] > hold && nutNeighbor[0][1]) {
                        directDistance[0][1] = hold;
                        distance[0][1] = move.getyPosition() - navigator.getyPosition();
                    }

                }
            }
        }

        Marking(move, map, otherPlayer);
    }

    private void Marking(Nut move, char[][] map, Player otherPlayer) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (i != 1 || j != 1) {
                    if (nutNeighbor[i][j]) {
                        nutNeighbor[i][j] = distance[i][j] > 1;
                    }
                    for (int k = 1; k < distance[i][j]; k++) {
                        if (i == 0 && j == 0) {
                            if (map[move.getyPosition() - k][move.getxPosition() - k] != otherPlayer.getPlayerNutColor()) {
                                nutNeighbor[i][j] = false;
                            }
                        } else if (i == 0 && j == 1) {
                            if (map[move.getyPosition() - k][move.getxPosition()] != otherPlayer.getPlayerNutColor()) {
                                nutNeighbor[i][j] = false;
                            }
                        } else if (i == 0) {
                            if (map[move.getyPosition() - k][move.getxPosition() + k] != otherPlayer.getPlayerNutColor()) {
                                nutNeighbor[i][j] = false;
                            }
                        } else if (i == 1 && j == 0) {
                            if (map[move.getyPosition()][move.getxPosition() - k] != otherPlayer.getPlayerNutColor()) {
                                nutNeighbor[i][j] = false;
                            }
                        } else if (i == 1 && j == 2) {
                            if (map[move.getyPosition()][move.getxPosition() + k] != otherPlayer.getPlayerNutColor()) {
                                nutNeighbor[i][j] = false;
                            }
                        } else if (i == 2 && j == 0) {
                            if (map[move.getyPosition() + k][move.getxPosition() - k] != otherPlayer.getPlayerNutColor()) {
                                nutNeighbor[i][j] = false;
                            }
                        } else if (i == 2 && j == 1) {
                            if (map[move.getyPosition() + k][move.getxPosition()] != otherPlayer.getPlayerNutColor()) {
                                nutNeighbor[i][j] = false;
                            }
                        } else {
                            if (map[move.getyPosition() + k][move.getxPosition() + k] != otherPlayer.getPlayerNutColor()) {
                                nutNeighbor[i][j] = false;
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean[][] getNutNeighbor() {
        return nutNeighbor;
    }

    public int[][] getDistance() {
        return distance;
    }
}
