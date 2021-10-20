import java.util.ArrayList;

public class ComputerPlayer {

    /**
     * A player information
     * listAllNutPosition is a list of all position nut can be
     * listAllPositionTake is a list of number taken other player nuts
     */
    private Player autoPlayer;
    private ArrayList<Nut> listAllNutPosition;
    private ArrayList<Integer> listAllPositionTake;

    /**
     * constructor which only set a player
     *
     * @param autoPlayer is information of a player for computer player
     */
    public ComputerPlayer(Player autoPlayer) {
        this.autoPlayer = autoPlayer;
        listAllNutPosition = new ArrayList<>();
        listAllPositionTake = new ArrayList<>();
    }

    public Nut computerMove(char[][] map, Player otherPlayer) {
        if (autoPlayer.getPlayerNutColor() == 'W') {
            return moveProcess(map,otherPlayer);
        } else if (autoPlayer.getPlayerNutColor() == 'B') {
            return moveProcess(map,otherPlayer);
        }
        return null;
    }

    private Nut moveProcess(char[][] map, Player otherPlayer) {
        if (map[0][0] == '*') {
            return new Nut(0, 'A', autoPlayer.getPlayerNutColor());
        } else if (map[7][0] == '*') {
            return new Nut(7, 'A', autoPlayer.getPlayerNutColor());
        } else if (map[0][7] == '*') {
            return new Nut(0, 'H', autoPlayer.getPlayerNutColor());
        } else if (map[7][7] == '*') {
            return new Nut(7, 'H', autoPlayer.getPlayerNutColor());
        } else {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (map[i][j] == '*') {
                        Integer temp = 0;
                        Nut move = new Nut(i, (char) (j + 65), autoPlayer.getPlayerNutColor());
                        MotionCheck moveCheck = new MotionCheck(move);
                        listAllNutPosition.add(move);
                        if (moveCheck.checkSpacePosition(move, map, otherPlayer)) {
                            moveCheck.changeCheck(move, autoPlayer, otherPlayer, map);
                            for (int t = 0; t < 3; t++) {
                                for (int l = 0; l < 3; l++) {
                                    if (moveCheck.getNutNeighbor()[t][l]) {
                                        for (int k = 1; k < moveCheck.getDistance()[t][l]; k++) {
                                            if (t == 0 && l == 0) {
                                                if (map[move.getyPosition() - k][move.getxPosition() - k] == otherPlayer.getPlayerNutColor()) {
                                                    temp++;
                                                }
                                            } else if (t == 0 && l == 1) {
                                                if (map[move.getyPosition() - k][move.getxPosition()] == otherPlayer.getPlayerNutColor()) {
                                                    temp++;
                                                }
                                            } else if (t == 0) {
                                                if (map[move.getyPosition() - k][move.getxPosition() + k] == otherPlayer.getPlayerNutColor()) {
                                                    temp++;
                                                }
                                            } else if (t == 1 && l == 0) {
                                                if (map[move.getyPosition()][move.getxPosition() - k] == otherPlayer.getPlayerNutColor()) {
                                                    temp++;
                                                }
                                            } else if (t == 1 && l == 2) {
                                                if (map[move.getyPosition()][move.getxPosition() + k] == otherPlayer.getPlayerNutColor()) {
                                                    temp++;
                                                }
                                            } else if (t == 2 && l == 0) {
                                                if (map[move.getyPosition() + k][move.getxPosition() - k] == otherPlayer.getPlayerNutColor()) {
                                                    temp++;
                                                }
                                            } else if (t == 2 && l == 1) {
                                                if (map[move.getyPosition() + k][move.getxPosition()] == otherPlayer.getPlayerNutColor()) {
                                                    temp++;
                                                }
                                            } else {
                                                if (map[move.getyPosition() + k][move.getxPosition() + k] == otherPlayer.getPlayerNutColor()) {
                                                    temp++;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        listAllPositionTake.add(temp);
                    }
                }
            }
            int maxTakenPosition = 0;
            for (int i = 0; i < listAllNutPosition.size(); i++) {
                if (listAllPositionTake.get(i) >= maxTakenPosition) {
                    maxTakenPosition = listAllPositionTake.get(i);
                }
            }
            for (int i = 0; i < listAllNutPosition.size(); i++) {
                if (listAllPositionTake.get(i) == maxTakenPosition) {
                    return listAllNutPosition.get(i);
                }
            }
            return null;
        }
    }
}
