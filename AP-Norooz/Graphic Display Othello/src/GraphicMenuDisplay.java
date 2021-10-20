
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.logging.*;


public class GraphicMenuDisplay extends JFrame {

    private JButton exitFromGame;
    private JLabel title;
    private JPanel margin;
    private JButton multiPlayerGame;
    private JButton singlePlayerGame;

    public GraphicMenuDisplay(){

        margin = new JPanel();
        singlePlayerGame = new JButton();
        multiPlayerGame = new JButton();
        exitFromGame = new JButton();
        title = new JLabel();

        margin.setBorder(BorderFactory.createTitledBorder(new SoftBevelBorder(BevelBorder.RAISED, Color.black,Color.lightGray,Color.black,Color.gray), "Main Menu", TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", 0, 12), new Color(51, 51, 255)));

        singlePlayerGame.setBackground(new java.awt.Color(102, 102, 255));
        singlePlayerGame.addActionListener(evt -> singlePlayerGameActionPerformed(evt));
        singlePlayerGame.setText("1) Single Player Game");

        multiPlayerGame.setBackground(new java.awt.Color(102, 102, 255));
        multiPlayerGame.addActionListener(evt -> multiPlayerGameActionPerformed(evt));
        multiPlayerGame.setText("2) MultiPlayer Game");

        exitFromGame.setBackground(new Color(102, 102, 255));
        exitFromGame.addActionListener(evt -> exitFromGameActionPerformed(evt));
        exitFromGame.setText("3) Exit From Game");

        title.setBackground(new Color(102, 102, 255));
        title.setFont(new Font("Tahoma", 0, 14)); // NOI18N
        title.setForeground(new Color(51, 51, 51));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setText("Welcome To The Othello Game");
        title.setBorder(new SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.black, java.awt.Color.lightGray, java.awt.Color.black, java.awt.Color.darkGray));
        title.setMaximumSize(new Dimension(157, 23));
        title.setMinimumSize(new Dimension(157, 23));
        title.setPreferredSize(new Dimension(157, 23));

        GroupLayout marginLayout = new GroupLayout(margin);
        margin.setLayout(marginLayout);

        marginLayout.setHorizontalGroup(
                marginLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(marginLayout.createSequentialGroup()
                                .addGap(102, 102, 102)
                                .addComponent(title, GroupLayout.PREFERRED_SIZE, 199, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(GroupLayout.Alignment.TRAILING, marginLayout.createSequentialGroup()
                                .addGap(0, 122, Short.MAX_VALUE)
                                .addGroup(marginLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(multiPlayerGame, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
                                        .addComponent(singlePlayerGame, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
                                        .addComponent(exitFromGame, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(122, 122, 122))
        );

        marginLayout.setVerticalGroup(
                marginLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(marginLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(title, javax.swing.GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(22, 22, 22)
                                .addComponent(singlePlayerGame, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(multiPlayerGame, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                                .addComponent(this.exitFromGame, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(margin, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(margin, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }

    public static void main(String args[]){

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (final ClassNotFoundException ex) {
            Logger.getLogger(GraphicMenuDisplay.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(GraphicMenuDisplay.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(GraphicMenuDisplay.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(GraphicMenuDisplay.class.getName()).log(Level.SEVERE, null, ex);
        }

        GraphicMenuDisplay field = new GraphicMenuDisplay();
        field.setVisible(true);

    }


    private void singlePlayerGameActionPerformed(ActionEvent evt) {

        try {
            GraphicFieldDisplay graphicFieldDisplay = new GraphicFieldDisplay(1);
            graphicFieldDisplay.display(this);
        } catch (Exception e){}

    }

    private void multiPlayerGameActionPerformed(ActionEvent evt) {

        try {
            GraphicFieldDisplay graphicFieldDisplay = new GraphicFieldDisplay(2);
            graphicFieldDisplay.display(this);
        } catch (Exception e){}

    }

    private void exitFromGameActionPerformed(ActionEvent evt) {

        try {
            String[] strButton = new String[] {"Yes","No"};
            int result = JOptionPane.showOptionDialog(null , "Are you sure you want to quit?","Exit From Game",0,JOptionPane.WARNING_MESSAGE , null,strButton ,strButton[1]);
            if(result == 0) {
                closeDisplay();
            }
        } catch (Exception e){}

    }

    public void sleep(int time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    private void closeDisplay(){
        WindowEvent winClosingEvent = new WindowEvent(this,WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosingEvent);
    }


}
