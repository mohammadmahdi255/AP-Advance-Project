import javax.swing.*;
import javax.swing.plaf.basic.BasicMenuBarUI;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

/**
 * this class is control all components
 */
public class GUI extends JFrame {

    private Theme theme;

    private boolean isFullScreen;
    private boolean toggleSidebar;

    private JMenuBar menuBar;

    private JPanel currentPage;
    private SideBar sideBar;

    private JCheckBox checkBox;
    private JComboBox<String> comboBox;

    /**
     * this is a Constructor which creat JFrame components
     */
    public GUI() {

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) {
        }

        theme = new Theme();

        isFullScreen = false;
        toggleSidebar = false;

        menuBar = new JMenuBar();

        currentPage = new JPanel();
        sideBar = new SideBar(theme, new Dimension(250,600), new Dimension(750, 600));

        checkBox = new JCheckBox();
        comboBox = new JComboBox<>(new String[]{"Dark Mod", "Light Mod"});

        Handler handler = new Handler();

        setLayout(new BorderLayout(0, 0));
        setMinimumSize(new Dimension(650, 500));
        setBounds(200, 100, 1000, 600);
        setFocusable(true);
        addMouseListener(sideBar.getHandler());
        addWindowStateListener(handler);
        add(menuBar, BorderLayout.NORTH);
        add(currentPage, BorderLayout.CENTER);

        JMenu application = new JMenu("Application");
        application.setForeground(Color.BLACK);
        application.add(new JMenuItem("Options"));
        application.add(new JMenuItem("Exit"));
        application.getItem(0).setAccelerator(KeyStroke.getKeyStroke("ctrl O"));
        application.getItem(0).addActionListener(handler);
        application.getItem(1).setAccelerator(KeyStroke.getKeyStroke("ctrl E"));
        application.getItem(1).addActionListener(handler);

        JMenu view = new JMenu("View");
        view.setForeground(Color.BLACK);
        view.add(new JMenuItem("Toggle Full Screen"));
        view.add(new JMenuItem("Toggle Sidebar"));
        view.getItem(0).addActionListener(handler);
        view.getItem(0).setAccelerator(KeyStroke.getKeyStroke("F11"));
        view.getItem(1).setAccelerator(KeyStroke.getKeyStroke("F10"));
        view.getItem(1).addActionListener(handler);

        JMenu help = new JMenu("Help");
        help.setForeground(Color.BLACK);
        help.add(new JMenuItem("About"));
        help.add(new JMenuItem("Help"));
        help.getItem(0).addActionListener(handler);
        help.getItem(0).setAccelerator(KeyStroke.getKeyStroke("ctrl I"));
        help.getItem(1).addActionListener(handler);
        help.getItem(1).setAccelerator(KeyStroke.getKeyStroke("ctrl H"));

        menuBar.setPreferredSize(new Dimension(400, 25));
        menuBar.setBackground(new Color(0xDAD5D6));
        menuBar.setUI(new BasicMenuBarUI());
        menuBar.add(application);
        menuBar.add(view);
        menuBar.add(help);

        currentPage.setLayout(new BorderLayout(0, 0));
        currentPage.add(sideBar, BorderLayout.WEST);
        currentPage.add(sideBar.getUnripePage(), BorderLayout.CENTER);
        currentPage.addHierarchyBoundsListener(sideBar.getHandler());
        currentPage.setBounds(100, 100, 1000, 600);

        comboBox.addActionListener(handler);

        addWindowListener(handler);

    }

    /**
     *
     * @return this JFrame
     */
    private JFrame getThis(){
        return this;
    }

    /**
     * controlling event and react to them
     */
    private class Handler implements ActionListener , WindowStateListener , WindowListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            //this is for application menu item
            if(e.getSource().equals(menuBar.getMenu(0).getItem(0))) {

                JLabel label = new JLabel("follow redirect");
                label.setBounds(10, 10, 100, 35);

                checkBox.setBounds(120, 10, 20, 20);

                comboBox.setBounds(10, 55, 100, 25);

                JPanel panel = new JPanel();
                panel.setPreferredSize(new Dimension(200, 50));
                panel.add(label);
                panel.add(checkBox);
                panel.add(comboBox);

                String[] strButton = new String[]{"ok"};

                JOptionPane.showOptionDialog(null,
                        panel,
                        "About",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE,
                        new ImageIcon(theme.getAddressIcons() + "icons8_services_160px.png"),
                        strButton,
                        strButton[0]);

            }

            //this for menu theme color
            else if (e.getSource().equals(comboBox)) {
                theme.setTheme(comboBox.getSelectedIndex());
                sideBar.setTheme();
                sideBar.getUnripePage().setTheme();
            }

            //this is for exit from program menu item
            else if (e.getSource().equals(menuBar.getMenu(0).getItem(1))) {

                String[] strButton = new String[]{"Yes", "No"};

                int result = JOptionPane.showOptionDialog(null,
                        "Are you sure you want to quit?",
                        "Exit From Game",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE,
                        null,
                        strButton,
                        strButton[1]);

                if (result == 0) {
                    closeDisplay();
                }

            }

            //this is for full screen menu item
            else if (e.getSource().equals(menuBar.getMenu(1).getItem(0))) {

                if (isFullScreen) {
                    setExtendedState(JFrame.NORMAL);
                } else {
                    setExtendedState(JFrame.MAXIMIZED_BOTH);
                }

            }

            //this is for toggle side bar menu item
            else if (e.getSource().equals(menuBar.getMenu(1).getItem(1))) {

                if (!toggleSidebar) {
                    sideBar.setSize(new Dimension(0, sideBar.getHeight()));
                    sideBar.setPreferredSize(new Dimension(0, sideBar.getHeight()));
                    toggleSidebar = true;
                } else {
                    sideBar.setSize(new Dimension(250, sideBar.getHeight()));
                    sideBar.setPreferredSize(new Dimension(250, sideBar.getHeight()));
                    toggleSidebar = false;
                }

                if (sideBar.getRequests().size() != 0) {
                    for (Request request : sideBar.getRequests()) {
                        if (request.isSelected()) {
                            request.setLayout(true);
                        } else if (sideBar.getRequests().indexOf(request) == sideBar.getRequests().size() - 1) {
                            sideBar.getUnripePage().setLayout(true);
                        }
                    }
                } else {
                    sideBar.getUnripePage().setLayout(true);
                }

            }

            //this is for about menu item
            else if (e.getSource().equals(menuBar.getMenu(2).getItem(0))) {

                JLabel label = new JLabel("The Mid Term AP Project");
                JLabel label1 = new JLabel("Name : Mohammad Mahdi Nemati Haravani");
                JLabel label2 = new JLabel("Student Number : 9831066");
                JLabel label3 = new JLabel("Email : nemati.mahdi255@gmail.com");

                JPanel panel = new JPanel();
                panel.setLayout(new GridLayout(4, 1, 5, 5));
                panel.add(label);
                panel.add(label1);
                panel.add(label2);
                panel.add(label3);

                String[] strButton = new String[]{"ok"};

                JOptionPane.showOptionDialog(null,
                        panel,
                        "About",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE,
                        new ImageIcon(theme.getAddressIcons() + "icons8_high_importance_160px.png"),
                        strButton,
                        strButton[0]);

            }

            //this is for help menu item
            else if (e.getSource().equals(menuBar.getMenu(2).getItem(1))) {

                JLabel label = new JLabel("Support method : GET-POST-PUT-DELETE ");
                JLabel label1 = new JLabel("Support  body type : Form Data-Form URL Encoded-Binary Upload-Json");

                JPanel panel = new JPanel();
                panel.setLayout(new GridLayout(2, 1, 5, 5));
                panel.add(label);
                panel.add(label1);


                String[] strButton = new String[]{"ok"};

                JOptionPane.showOptionDialog(null,
                        panel,
                        "Help",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE,
                        new ImageIcon(theme.getAddressIcons() + "icons8_help_160px.png"),
                        strButton,
                        strButton[0]);

            }

            try {
                currentPage.updateUI();
            } catch (NullPointerException ignored){}

        }

        /**
         * this method react with exit menu item
         */
        private void closeDisplay(){

            WindowEvent winClosingEvent = new WindowEvent(getThis(),WindowEvent.WINDOW_CLOSING);
            Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosingEvent);

        }

        /**
         * save program status when program close
         */
        private void saveStatus() {

            try {

                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(new File("D:/Java Programing Files/AP-MidTerm/Phase 3/src/Save/save status.obj")));

                out.writeBoolean(isFullScreen);
                out.writeBoolean(toggleSidebar);
                out.writeBoolean(checkBox.isSelected());
                out.writeInt(comboBox.getSelectedIndex());
                out.writeUTF(sideBar.getFilter().getText());
                out.writeObject(sideBar.getFilter().getForeground());
                out.writeInt(sideBar.getRequests().size());

                out.close();

                for (Request request : sideBar.getRequests()) {
                    request.savePrimaryData();
                    out = new ObjectOutputStream(new FileOutputStream(new File("D:/Java Programing Files/AP-MidTerm/Phase 3/src/Save/"+(sideBar.getRequests().indexOf(request) + 1)+".obj")));
                    out.writeObject(request);
                    out.close();
                }


            } catch (IOException ignored){}

        }

        /**
         * run the save setting from last opening
         */
        private void loadStatus() {

            try {

                ObjectInputStream in = new ObjectInputStream(new FileInputStream(new File("D:/Java Programing Files/AP-MidTerm/Phase 3/src/Save/save status.obj")));

                isFullScreen = in.readBoolean();
                toggleSidebar = in.readBoolean();
                checkBox.setSelected(in.readBoolean());
                comboBox.setSelectedIndex(in.readInt());
                sideBar.getFilter().setText(in.readUTF());
                sideBar.getFilter().setForeground(((Color) in.readObject()));
                int size = in.readInt();

                in.close();

                Request request;

                for (int i = size; i > 0 ; i--) {
                    sideBar.loadRequest(new File("D:/Java Programing Files/AP-MidTerm/Phase 3/src/Save/"+i+".obj"));
                }

                for (int i = 0; i < size ; i++) {
                   in = new ObjectInputStream(new FileInputStream(new File("D:/Java Programing Files/AP-MidTerm/Phase 3/src/Save/"+(i+1)+".obj")));
                   request = (Request) in.readObject();
                    if (request.isSelected()) {
                        sideBar.setUnripePage(sideBar.getRequests().get(i));
                        sideBar.getRequests().get(i).setHover();
                        sideBar.getRequests().get(i).setSelected(true);
                        sideBar.getRequests().get(i).setOtherButtonDeSelect();
                        in.close();
                        break;
                    }
                    in.close();
                }

                File file = new File("D:/Java Programing Files/AP-MidTerm/Phase 3/src/Save/save status.obj");
                file.delete();

                for (int i = 1; i <= size ; i++) {
                    file = new File("D:/Java Programing Files/AP-MidTerm/Phase 3/src/Save/"+i+".obj");
                    file.delete();
                }

            } catch (IOException | ClassNotFoundException ignored){}

        }

        @Override
        public void windowStateChanged(WindowEvent e) {

            isFullScreen = !isFullScreen;

        }

        @Override
        public void windowOpened(WindowEvent e) {
            loadStatus();
        }

        @Override
        public void windowClosing(WindowEvent e) {
            saveStatus();
        }

        @Override
        public void windowClosed(WindowEvent e) {

        }

        @Override
        public void windowIconified(WindowEvent e) {

        }

        @Override
        public void windowDeiconified(WindowEvent e) {

        }

        @Override
        public void windowActivated(WindowEvent e) {

        }

        @Override
        public void windowDeactivated(WindowEvent e) {

        }
    }

}
