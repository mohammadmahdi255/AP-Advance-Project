import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class GraphicFieldDisplayPanel extends JPanel implements MouseListener {

    private GraphicNut[][] graphicNuts;
    private char[][] oldMap;
    private JFrame graphicFieldDisplay;
    private JFrame field;
    private Image pictureOfOthelloGameBoard;
    private Player player1;
    private Player player2;
    private Map tableOfGame;
    private int menuNumber;


    public GraphicFieldDisplayPanel(int menuNumber , JFrame graphicFieldDisplay , JFrame field){

        this.graphicFieldDisplay = graphicFieldDisplay;
        this.field = field;

        this.menuNumber = menuNumber;

        oldMap = new char[8][8];

        player1 = new Player('W');
        player2 = new Player('B');
        tableOfGame = new Map(player1, player2);

        String[] pictureAnimationAddress = new String[]{"picture 1.png", "picture 2.png", "picture 3.png", "picture 4.png", "picture 5.png", "picture 6.png", "picture 7.png"};

        graphicNuts = new GraphicNut[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                graphicNuts[i][j] = new GraphicNut(30 + j * 67, 28 + i * 67, 67, 67, "Empty Nut.png", ' ');
            }
        }
        updateGameBoard(tableOfGame.getMap(),tableOfGame.getMovingTurn());



        try{
            pictureOfOthelloGameBoard = ImageIO.read(new File("D:\\Java Programing Files/AP-Norooz/Graphic Display Othello/src/Image/Othello Game Board.png"));
        } catch (IOException e){
            e.printStackTrace();
        }

        addMouseListener(this);
        setFocusable(true);
    }

    @Override
    public void paintComponent(Graphics g){
        g.drawImage(pictureOfOthelloGameBoard , 0,0,598,596,this);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                graphicNuts[i][j].draw(g,this);
            }
        }
    }

    public void updateGameBoard(char[][] map , int movingTurn ) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (map[i][j] == 'W' && graphicNuts[i][j].getColor() != 'W') {
                    graphicNuts[i][j] = new GraphicNut(30 + j * 67, 28 + i * 67, 67, 67, "White Nut.png", 'W');
                } else if (map[i][j] == 'B' && graphicNuts[i][j].getColor() != 'B') {
                    graphicNuts[i][j] = new GraphicNut(30 + j * 67, 28 + i * 67, 67, 67, "Black Nut.png", 'B');
                } else if (map[i][j] == '*') {
                    if (movingTurn % 2 == 0 && graphicNuts[i][j].getColor() != 'w') {
                        graphicNuts[i][j] = new GraphicNut(30 + j * 67, 28 + i * 67, 67, 67, "Pale White Nut.png", 'w');
                    } else if (graphicNuts[i][j].getColor() != 'b'){
                        graphicNuts[i][j] = new GraphicNut(30 + j * 67, 28 + i * 67, 67, 67, "Pale Black Nut.png", 'b');
                    }
                } else if(graphicNuts[i][j].getColor() != ' ' && graphicNuts[i][j].getColor() != 'W' && graphicNuts[i][j].getColor() != 'B') {
                    graphicNuts[i][j] = new GraphicNut(30 + j * 67, 28 + i * 67, 67, 67, "Empty Nut.png", ' ');
                }
            }
        }
    }

    public void updateGameProcess(){

           tableOfGame.interLockableLocations();
           updateGameBoard(tableOfGame.getMap(), tableOfGame.getMovingTurn());
           repaint();
           tableOfGame.mapDisplay();

    }


    public void mouseClicked(MouseEvent e) {
                if (e.getModifiers() == InputEvent.BUTTON1_MASK) {

                    int j = (e.getX() - 30) / 67;
                    int i = (e.getY() - 28) / 67;
                    int temp = i + 1;
                    char hold = (char) (j + 65);

                    if (menuNumber == 1) {

                        if (tableOfGame.getMovingTurn() % 2 == 0 && !tableOfGame.endOfGame()) {
                            System.out.println("Player 1 turn:");
                            oldMap = tableOfGame.getMap();
                            tableOfGame.playerMove(temp, hold);
                            updateGameProcess();
                        }

                        if (tableOfGame.getMovingTurn() % 2 == 1 && !tableOfGame.endOfGame()) {
                            ComputerPlayer computerPlayer = new ComputerPlayer(player2);
                            System.out.println("Player 2 turn:");
                            Nut move = computerPlayer.computerMove(tableOfGame.getMap(), player1);
                            temp = move.getyPosition() + 1;
                            hold = (char) (move.getxPosition() + 65);
                            oldMap = tableOfGame.getMap();
                            tableOfGame.playerMove(temp, hold);
                        }

                    } else {

                        if (tableOfGame.getMovingTurn() % 2 == 0) {
                            System.out.println("Player 1 turn:");
                        } else {
                            System.out.println("Player 2 turn:");
                        }
                        for (int k = 0; k < 8; k++) {
                            for (int l = 0; l < 8; l++) {
                                oldMap[k][l] = tableOfGame.getMap()[k][l];
                            }
                        }
                        tableOfGame.playerMove(temp, hold);
                    }

                    updateGameProcess();

                    if(tableOfGame.endOfGame()){
                        endGameProcess();
                    }
            }
    }

    private void endGameProcess() {
        closeDisplay(graphicFieldDisplay);
        field.setVisible(true);
    }

    private void closeDisplay(JFrame theGameResult){
        WindowEvent winClosingEvent = new WindowEvent(theGameResult,WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosingEvent);
    }

    public void sleep(int time){

        try{
            Thread.sleep(time);
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}
