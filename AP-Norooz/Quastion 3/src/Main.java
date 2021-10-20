import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        int mainMenu = 0;

        while (mainMenu != 2) {

            System.out.println("1)Start");
            System.out.println("2)Exit");
            System.out.print("Choose:");

            mainMenu = new Scanner(System.in).nextInt();


            if (mainMenu == 1) {

                int humanPlayersNumber, computerPlayersNumber;

                do {

                    System.out.print("Human Players Number:");
                    humanPlayersNumber = new Scanner(System.in).nextInt();

                    System.out.print("Computer Players Number:");
                    computerPlayersNumber = new Scanner(System.in).nextInt();

                } while (humanPlayersNumber < 0 || computerPlayersNumber < 0 || computerPlayersNumber + humanPlayersNumber <= 1 || computerPlayersNumber + humanPlayersNumber >= 6);

                    BoardOfGame field = new BoardOfGame(humanPlayersNumber, computerPlayersNumber);

                    field.play();

            }

        }
    }

}
