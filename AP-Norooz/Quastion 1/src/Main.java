import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        int menuNumber = 0;
        int temp;
        char hold;
        String str;
        Scanner reader = new Scanner(System.in);

        while (menuNumber != 3) {

            System.out.println("1) MultiPlayer game");
            System.out.println("2) Single game with computer");
            System.out.println("3) Exit");
            System.out.println("Choose:");
            Player player1 = new Player('W');
            Player player2 = new Player('B');
            Map tableOfGame = new Map(player1, player2);

            menuNumber = reader.nextInt();
            reader.nextLine();

            while (menuNumber == 1) {

                tableOfGame.interLockableLocations();
                tableOfGame.mapDisplay();
                if (tableOfGame.endOfGame()) {
                    menuNumber = 0;
                    break;
                }
                if (tableOfGame.getMovingTurn() % 2 == 0) {
                    System.out.println("Player 1 turn:");
                } else {
                    System.out.println("Player 2 turn:");
                }
                System.out.println("I C:");
                str = reader.nextLine();
                temp = str.charAt(0) - 48;
                hold = str.charAt(2);
                tableOfGame.playerMove(temp, hold);
            }

            while (menuNumber == 2) {
                tableOfGame.interLockableLocations();
                tableOfGame.mapDisplay();
                if (tableOfGame.endOfGame()) {
                    menuNumber = 0;
                    break;
                }
                if (tableOfGame.getMovingTurn() % 2 == 0) {
                    System.out.println("Player 1 turn:");
                    System.out.println("I C:");
                    str = reader.nextLine();
                    temp = str.charAt(0) - 48;
                    hold = str.charAt(2);
                } else {
                    try {
                        Thread.sleep(5000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    ComputerPlayer computerPlayer = new ComputerPlayer(player2);
                    System.out.println("Player 2 turn:");
                    Nut move = computerPlayer.computerMove(tableOfGame.getMap(),player1);
                    temp = move.getyPosition()+1;
                    hold = (char)(move.getxPosition()+65);
                }
                tableOfGame.playerMove(temp, hold);
            }

        }
    }
}

