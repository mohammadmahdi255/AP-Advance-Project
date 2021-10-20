import com.sun.java.swing.plaf.windows.WindowsScrollBarUI;
import com.sun.java.swing.plaf.windows.WindowsScrollPaneUI;

import javax.swing.*;
import javax.swing.plaf.basic.BasicMenuBarUI;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;


public class SideBar extends JPanel {

    private Theme theme;

    private Handler handler;

    private UnripePage unripePage;

    private ArrayList<String> methodTypes;

    private ArrayList<Request> requests;
    private JPanel[] panels;
    private JScrollPane scrollPane;

    private JButton[] buttons;
    private JMenuBar[] menuBars;
    private JMenu[] menus;

    private JTextField filter;

    /**
     * this is a constructor for sidebar
     */
    public SideBar(Theme theme, Dimension sideBarSize, Dimension unripePageSize) {

        this.theme = theme;

        handler = new Handler();

        unripePage = new UnripePage(theme, unripePageSize);

        methodTypes = new ArrayList<>();

        requests = new ArrayList<>();

        panels = new JPanel[2];
        scrollPane = new JScrollPane();
        buttons = new JButton[3];
        menuBars = new JMenuBar[3];
        menus = new JMenu[3];
        filter = new JTextField();

        for (int i = 0; i < 3; i++) {

            buttons[i] = new JButton();
            buttons[i].setLayout(new BorderLayout(0,0));
            buttons[i].setBackground(theme.getButtonTheme()[0][0]);
            buttons[i].setForeground(theme.getButtonTheme()[0][1]);
            buttons[i].setFocusable(false);
            buttons[i].addMouseListener(handler);

            menuBars[i] = new JMenuBar();
            menuBars[i].setLayout(new BorderLayout(0, 0));

            menus[i] = new JMenu();
            menus[i].setLayout(new BorderLayout(0, 0));

            if (i < 2) {

                panels[i] = new JPanel();
                panels[i].setLayout(null);
                panels[i].setSize(250, 50);
                panels[i].setBackground(theme.getBackGroundTheme()[0]);
                panels[i].addMouseListener(handler);

            }

        }

        unripePage.getNewRequest().addActionListener(handler);

        panels[0].add(menuBars[0]);
        panels[0].add(buttons[0]);
        panels[0].add(filter);
        panels[0].add(buttons[1]);
        panels[0].add(buttons[2]);
        panels[0].setPreferredSize(new Dimension(sideBarSize.width, 130));

        scrollPane.setViewportView(panels[1]);
        scrollPane.setUI(new WindowsScrollPaneUI());
        scrollPane.getHorizontalScrollBar().setUI(new WindowsScrollBarUI());
        scrollPane.getVerticalScrollBar().setUI(new WindowsScrollBarUI());

        buttons[0].setBounds(0, 55, 150, 30);
        buttons[0].setText("New Environment");
        buttons[0].setIcon(new ImageIcon(theme.getAddressIcons()+"icons8_sort_down_10px.png"));
        buttons[0].setHorizontalTextPosition(SwingConstants.LEADING);
        buttons[0].add(menuBars[1] ,BorderLayout.WEST);

        buttons[1].setBounds(152, 55, 96, 30);
        buttons[1].setText("Cookies");

        buttons[2].setBounds(207, 90, 35, 35);
        buttons[2].setIcon(new ImageIcon(theme.getAddressIcons()+"icons8_sort_down_&&_plus_25px.png"));
        buttons[2].add(menuBars[2] ,BorderLayout.WEST);

        menuBars[0].setBounds(0, 0, 250, 50);
        menuBars[0].setUI(new BasicMenuBarUI());
        menuBars[0].setBackground(theme.getInsomniaTheme()[0]);
        menuBars[0].setBorder(BorderFactory.createMatteBorder(1,0,1,0,theme.getInsomniaTheme()[3]));
        menuBars[0].add(menus[0]);

        menuBars[1].setPreferredSize(new Dimension(0, 0));
        menuBars[1].add(menus[1]);

        menuBars[2].setPreferredSize(new Dimension(0, 0));
        menuBars[2].add(menus[2]);

        menus[0].setForeground(theme.getInsomniaTheme()[1]);
        menus[0].setFont(new Font("Times New Roman", Font.PLAIN, 20));
        menus[0].setText("Insomnia");
        menus[0].setIcon(new ImageIcon(theme.getAddressIcons()+"icons8_list_30px.png"));
        menus[0].setHorizontalAlignment(SwingConstants.LEFT);
        menus[0].setHorizontalTextPosition(SwingConstants.TRAILING);
        menus[0].setIconTextGap(8);
        menus[0].addMouseListener(handler);
        menus[0].add(new JMenuItem("Workspace Settings"));
        menus[0].add(new JMenuItem("Create Workspace"));
        menus[0].add(new JMenuItem("Preferences"));
        menus[0].add(new JMenuItem("Import/Export"));
        menus[0].add(new JMenuItem("Log In"));

        menus[1].setMenuLocation(-18, -7 + buttons[0].getHeight());
        menus[1].add(new JMenuItem("No Environment"));
        menus[1].add(new JMenuItem("Manage Environments"));

        menus[2].setMenuLocation(-18, -7 + buttons[2].getHeight());
        menus[2].add(new JMenuItem("New Request"));
        menus[2].add(new JMenuItem("New Folder"));
        menus[2].getItem(0).setIcon(new ImageIcon(theme.getAddressIcons()+"icons8_joyent_15px.png"));
        menus[2].getItem(0).setAccelerator(KeyStroke.getKeyStroke("ctrl N"));
        menus[2].getItem(1).setIcon(new ImageIcon(theme.getAddressIcons()+"icons8_doctors_folder_15px.png"));

        for (int i = 0; i < 3; i++) {
            for (Component component : menus[i].getMenuComponents()) {
                ((JMenuItem) component).addActionListener(handler);
            }
        }

        filter.setBounds(2, 90, 200, 35);
        filter.setBackground(theme.getTextFieldTheme()[0][0]);
        filter.setForeground(theme.getTextFieldTheme()[0][1]);
        filter.addFocusListener(handler);
        filter.addMouseListener(handler);
        filter.addKeyListener(handler);

        setLayout(new BorderLayout(0, 0));
        addMouseListener(handler);
        setSize(250, 50);
        add(panels[0], BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

    }

    /**
     *
     * @return return the space work which belong to request
     */
    public UnripePage getUnripePage() {
        return unripePage;
    }

    /**
     *
     * @return the part of sidebar field
     */
    public JPanel[] getPanels() {
        return panels;
    }

    /**
     *
     * @return this handler for event
     */
    public Handler getHandler() {
        return handler;
    }

    /**
     *
     * @return the request ArrayList
     */
    public ArrayList<Request> getRequests() {
        return requests;
    }

    /**
     *
     * @return this JPanel
     */
    private SideBar getThis(){
        return this;
    }

    public ArrayList<String> getMethodTypes() {
        return methodTypes;
    }

    /**
     *
     * @param unripePage set the new request space work
     */
    public void setUnripePage(UnripePage unripePage){

        Container currentPage = this.unripePage.getParent();
        currentPage.remove(1);
        this.unripePage = unripePage;

        currentPage.add(this.unripePage, BorderLayout.CENTER);
        this.unripePage.addMouseListener(handler);

    }

    public void setPageLabel() {

        if(scrollPane.getHeight() >= 35 * panels[1].getComponentCount()) {
            for (int i = 0; i < panels[1].getComponentCount(); i++) {
                panels[1].getComponent(i).setBounds(0, 35 * i, 250, 35);
            }

            panels[1].setPreferredSize(new Dimension(250, 35 * panels[1].getComponentCount()));
        } else {
            for (int i = 0; i < panels[1].getComponentCount(); i++) {
                panels[1].getComponent(i).setBounds(0, 35 * i, 233, 35);
            }

            panels[1].setPreferredSize(new Dimension(233, 35 * panels[1].getComponentCount()));
        }

        updateUI();
    }

    /**
     * set Color of them in the side bar
     */
    public void setTheme(){

        for (int i = 0; i < 3; i++) {

            buttons[i].setBackground(theme.getButtonTheme()[0][0]);
            buttons[i].setForeground(theme.getButtonTheme()[0][1]);

            if(i < 2) {

                panels[i].setBackground(theme.getBackGroundTheme()[0]);

            }

        }

        menuBars[0].setBackground(theme.getInsomniaTheme()[0]);

        menus[0].setForeground(theme.getInsomniaTheme()[1]);

        buttons[0].setIcon(new ImageIcon(theme.getAddressIcons()+"icons8_list_30px.png"));

        buttons[1].setIcon(new ImageIcon(theme.getAddressIcons()+"icons8_sort_down_10px.png"));

        buttons[2].setIcon(new ImageIcon(theme.getAddressIcons()+"icons8_sort_down_&&_plus_25px.png"));

        menus[2].getItem(0).setIcon(new ImageIcon(theme.getAddressIcons()+"icons8_joyent_15px.png"));
        menus[2].getItem(1).setIcon(new ImageIcon(theme.getAddressIcons()+"icons8_doctors_folder_15px.png"));

        filter.setBackground(theme.getTextFieldTheme()[0][0]);
        filter.setForeground(theme.getTextFieldTheme()[0][1]);

    }

    /**
     * an inner class which control events
     */
    private class Handler implements ActionListener, MouseListener, FocusListener, KeyListener, HierarchyBoundsListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            for (int i = 0; i < 3; i++) {
                buttons[i].setSelected(false);
            }

            if (e.getSource().equals(menus[2].getItem(0)) || e.getSource().equals(unripePage.getNewRequest())) {

                String[] option = new String[methodTypes.size()];

                for (String string : methodTypes) {
                    option[methodTypes.indexOf(string)] = string;
                }

                JComboBox[] comboBoxes = new JComboBox[3];
                JTextField[] textFields = new JTextField[2];

                comboBoxes[0] = new JComboBox<>(new String[]{"GET","POST","PUT","PATCH","DELETE","OPTIONS","HEAD","Custom Method"});
                comboBoxes[0].setBounds(400, 0, 150, 35);

                comboBoxes[1] = new JComboBox<>(new String[]{"Add Method","Select Method" ,"Remove Method"});
                comboBoxes[1].setBounds(300, 40, 125, 35);
                comboBoxes[1].setEnabled(false);

                if (option.length == 0) {
                    comboBoxes[1].removeItemAt(2);
                    comboBoxes[1].removeItemAt(1);
                }

                comboBoxes[2] = new JComboBox<>(option);
                comboBoxes[2].setBounds(425, 40, 125, 35);
                comboBoxes[2].setEnabled(false);

                textFields[0] = new JTextField();
                textFields[0].setBounds(0, 0, 400, 35);
                textFields[0].setText("My Request");

                textFields[1] = new JTextField();
                textFields[1].setBounds(0, 40, 300, 35);
                textFields[1].setEnabled(false);

                JPanel panel = new JPanel();
                panel.setPreferredSize(new Dimension(550, 35));
                panel.setLayout(null);
                panel.add(textFields[0]);
                panel.add(comboBoxes[0]);
                panel.add(textFields[1]);
                panel.add(comboBoxes[1]);
                panel.add(comboBoxes[2]);

                comboBoxes[0].addActionListener(event -> {
                    if (comboBoxes[0].getSelectedIndex() == comboBoxes[0].getItemCount() - 1) {
                        comboBoxes[1].setEnabled(true);
                        if (comboBoxes[1].getSelectedIndex() == 0) {
                            textFields[1].setEnabled(true);
                            comboBoxes[2].setEnabled(false);
                        } else {
                            textFields[1].setEnabled(false);
                            comboBoxes[2].setEnabled(true);
                        }
                    } else {
                        textFields[0].setEnabled(true);
                        textFields[1].setEnabled(false);
                        comboBoxes[1].setEnabled(false);
                        comboBoxes[2].setEnabled(false);
                    }
                    panel.updateUI();
                });


                comboBoxes[1].addActionListener(event -> {
                    if(comboBoxes[1].isEnabled()) {

                        if (comboBoxes[1].getSelectedIndex() == 0) {
                            textFields[0].setEnabled(true);
                            textFields[1].setEnabled(true);
                            comboBoxes[2].setEnabled(false);
                        } else {
                            textFields[0].setEnabled(false);
                            textFields[1].setEnabled(false);
                            comboBoxes[2].setEnabled(true);
                        }

                    }
                    panel.updateUI();
                });

                int result = JOptionPane.showOptionDialog(
                        null,
                        panel,
                        "Add New Request",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        new ImageIcon(theme.getAddressIcons() + "icons8_repository_100px.png"),
                        new String[]{"Ok"},
                        "Ok");

                if (result == 0) {

                    if(comboBoxes[1].isEnabled()) {

                        if (comboBoxes[1].getSelectedIndex() == 0) {

                            methodTypes.add(textFields[1].getText());

                        } else if (comboBoxes[1].getSelectedIndex() == 1) {

                            textFields[1].setText(((String) comboBoxes[2].getSelectedItem()));

                        } else if (comboBoxes[1].getSelectedIndex() == 2) {

                            methodTypes.remove(comboBoxes[2].getSelectedIndex());

                        }

                    } else {

                        textFields[1].setText(((String) comboBoxes[0].getItemAt(comboBoxes[0].getSelectedIndex())));

                    }

                    requests.add(0, new Request(theme, textFields[0].getText(), textFields[1].getText(), unripePage.getSize(), getThis()));

                    setUnripePage(requests.get(0));

                    ((Request) unripePage).getRequestName().addMouseListener(handler);
                    ((Request) unripePage).getRequestName().addKeyListener(handler);

                    panels[1].add(requests.get(0).getPageLabel(), 0);

                    setPageLabel();

                }

            }

            getThis().updateUI();

        }

        @Override
        public void mouseClicked(MouseEvent e) {

            for (Request request : requests) {

                if (e.getSource().equals(request.getRequestName())) {
                    if (e.getClickCount() > 1) {
                        request.getRequestName().setEditable(true);
                        request.getRequestName().setFocusable(true);
                        request.getRequestName().setForeground(theme.getTextFieldTheme()[0][2]);
                        request.getRequestName().setFont(new Font("Normal",Font.ITALIC,request.getRequestName().getFont().getSize()));
                        request.getRequestName().updateUI();
                    }
                } else if (request.getRequestName().isEnabled()) {
                    request.getRequestName().setEditable(false);
                    request.getRequestName().setFocusable(false);
                    request.getRequestName().setForeground(theme.getTextFieldTheme()[0][1]);
                    request.getRequestName().setFont(new Font("Normal",Font.PLAIN,request.getRequestName().getFont().getSize()));
                    request.getRequestName().updateUI();
                }

            }

            updateUI();
        }

        @Override
        public void mousePressed(MouseEvent e) {

            if (e.getSource().equals(menus[0])) {
                menuBars[0].setBackground(theme.getInsomniaTheme()[2]);
            }

            updateUI();
        }

        @Override
        public void mouseReleased(MouseEvent e) {

            if (e.getSource().equals(buttons[0])  && !buttons[0].isSelected()) {
                menus[1].doClick();
                buttons[0].setSelected(true);
            } else {
                buttons[0].setSelected(false);
            }

            if (e.getSource().equals(buttons[2])  && !buttons[2].isSelected()) {
                menus[2].doClick();
                buttons[2].setSelected(true);
            } else {
                buttons[2].setSelected(false);
            }

            getThis().updateUI();
        }

        @Override
        public void mouseEntered(MouseEvent e) {

            if (e.getSource().equals(menus[0])) {
                menuBars[0].setBackground(theme.getInsomniaTheme()[2]);
            }

            getThis().updateUI();
        }

        @Override
        public void mouseExited(MouseEvent e) {

            if (e.getSource().equals(menus[0])) {
                menuBars[0].setBackground(theme.getInsomniaTheme()[0]);
            }

            getThis().updateUI();
        }

        @Override
        public void focusGained(FocusEvent e) {

            if (e.getSource().equals(filter) && filter.getForeground().equals(theme.getTextFieldTheme()[0][1])) {
                filter.setText("");
                filter.setForeground(theme.getTextFieldTheme()[0][2]);
            }

        }

        @Override
        public void focusLost(FocusEvent e) {

            if (e.getSource().equals(filter) && filter.getText().equals("")) {

                filter.setText("Filter");
                filter.setForeground(theme.getTextFieldTheme()[0][1]);

            }

        }

        @Override
        public void keyTyped(KeyEvent e) {

            for (Request request : requests) {

                if (e.getSource().equals(request.getRequestName()) && e.getKeyCode() == KeyEvent.VK_ENTER) {
                    request.getRequestName().setEditable(false);
                }

            }

        }

        @Override
        public void keyPressed(KeyEvent e) {
        }

        @Override
        public void keyReleased(KeyEvent e) {

            if (e.getSource().equals(filter)) {

                panels[1].removeAll();

                for (Request request : requests) {

                    panels[1].add(request.getPageLabel());

                }

                for (Request request : requests) {

                    String name = request.getRequestName().getText().toLowerCase();
                    String search = filter.getText().toLowerCase();

                    for (int i = 0 ; i < search.length() ; i++) {

                        int j = name.indexOf(search.charAt(i));

                        if (name.contains(((String.valueOf(search.charAt(i)))))) {

                            char[] hold = name.toCharArray();
                            if (hold.length - j - 1 >= 0)
                                System.arraycopy(hold, j + 1, hold, j , hold.length - j - 1);

                            name = new String(hold);
                            name = name.substring(0,name.length()-1);

                        } else {
                            panels[1].remove(request.getPageLabel());
                            break;
                        }

                    }

                }

                setPageLabel();

            }

            getThis().updateUI();
        }

        @Override
        public void ancestorMoved(HierarchyEvent e) {

        }

        @Override
        public void ancestorResized(HierarchyEvent e) {
            setPageLabel();
        }

    }

}
