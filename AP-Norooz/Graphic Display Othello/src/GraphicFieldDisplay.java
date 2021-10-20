import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GraphicFieldDisplay {

    private JFrame graphicFieldDisplay;
    private final int menuNumber;
    private GraphicFieldDisplayPanel graphicFieldDisplayPanel;

    public GraphicFieldDisplay(int menuNumber){
        graphicFieldDisplay = new JFrame("Othello Game Board");
        this.menuNumber = menuNumber;
    }

    public void display(JFrame field){

        closeDisplay(field);
        graphicFieldDisplayPanel = new GraphicFieldDisplayPanel(menuNumber , graphicFieldDisplay , field);
        graphicFieldDisplay.getContentPane().add(graphicFieldDisplayPanel);

        graphicFieldDisplay.setVisible(true);
        graphicFieldDisplay.setAlwaysOnTop(true);
        graphicFieldDisplay.setSize(605,620);
        graphicFieldDisplay.setResizable(false);
        graphicFieldDisplayPanel.updateGameProcess();

    }

    private void closeDisplay(JFrame theGameResult){
        WindowEvent winClosingEvent = new WindowEvent(theGameResult,WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosingEvent);
    }

}
