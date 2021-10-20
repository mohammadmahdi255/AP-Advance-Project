import com.sun.java.swing.plaf.windows.WindowsScrollBarUI;
import com.sun.java.swing.plaf.windows.WindowsScrollPaneUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * this class creat each request work space
 */
public class Request extends UnripePage implements Serializable {

    private static final long serialVersionUID = 134553L;

    private transient Handler handler;

    private transient SideBar sideBar;
    private String url;
    private String requestNameString;
    private String methodType;
    private String binaryFileAddress;
    private int bodyType;
    private transient JLabel requestMethod;
    private transient JTextField requestName;
    private transient JPanel pagePanel;
    private transient JPanel panel;
    private transient JButton button;
    private transient JMenuBar menuBar;
    private transient JMenu menu;
    private boolean isSelected;

    private transient JComboBox<? extends String> comboBox;
    private transient JTextField URL;
    private transient JButton send;

    private transient JPanel[] panels;
    private transient ArrayList<JPanel>[] arrayLists;
    private transient JTextArea textArea;
    private String json;
    private ArrayList<String[]> saveFormBody;
    private ArrayList<String[]> saveQuery;
    private ArrayList<String[]> saveHeader;

    private transient JComboBox<String> chooseBodyType;

    private ArrayList<Response> responses;
    private transient JComboBox<String> comboBoxResponses;

    /**
     * @param name       request name
     * @param methodType type of way to send request
     * @param size       the size of Unripe page
     * @param sideBar    is the information of side bar
     */
    public Request(Theme theme, String name, String methodType, Dimension size, SideBar sideBar) {

        super(theme, size);

        this.methodType = methodType;
        this.sideBar = sideBar;

        handler = new Handler();

        requestMethod = new JLabel();
        requestName = new JTextField(name);
        pagePanel = new JPanel();
        panel = new JPanel();
        button = new JButton();
        menuBar = new JMenuBar();
        menu = new JMenu();
        isSelected = true;
        requestNameString = name;

        comboBox = new JComboBox<>(new String[]{"GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "HEAD", "Custom Method"});
        URL = new JTextField();
        send = new JButton("Send");

        JTabbedPane tabbedPane = new JTabbedPane();
        JScrollPane[] scrollPanes = new JScrollPane[5];
        arrayLists = new ArrayList[3];
        panels = new JPanel[5];

        textArea = new JTextArea();

        saveFormBody = new ArrayList<>();
        saveQuery = new ArrayList<>();
        saveHeader = new ArrayList<>();

        chooseBodyType = new JComboBox<>(new String[]{"Form Data", "Form Url Encoded", "Json", "Binary File", "No Body"});
        chooseBodyType.setPreferredSize(new Dimension(150, 30));

        responses = new ArrayList<>();
        comboBoxResponses = new JComboBox<>(new String[]{"Clear History", "Delete Current Response"});

        for (int i = 0; i < 5; i++) {

            panels[i] = new JPanel();
            panels[i].setLayout(null);
            panels[i].setBackground(theme.getBackGroundTheme()[2]);

            scrollPanes[i] = new JScrollPane(panels[i]);
            scrollPanes[i].setBackground(theme.getBackGroundTheme()[2]);
            scrollPanes[i].setUI(new WindowsScrollPaneUI());
            scrollPanes[i].getHorizontalScrollBar().setUI(new WindowsScrollBarUI());
            scrollPanes[i].getVerticalScrollBar().setUI(new WindowsScrollBarUI());

        }

        arrayLists[0] = new ArrayList<>();
        arrayLists[1] = new ArrayList<>();
        arrayLists[2] = new ArrayList<>();

        requestMethod.setPreferredSize(new Dimension(45, 35));
        requestMethod.setFont(new Font("Normal", Font.PLAIN, requestMethod.getFont().getSize() - 1));
        requestMethod.setBackground(theme.getLabelTheme()[0][2]);
        requestMethod.addMouseListener(handler);
        requestMethod.setHorizontalAlignment(SwingConstants.CENTER);
        requestMethod.setVerticalAlignment(SwingConstants.CENTER);
        setMethodColor(methodType);

        requestName.setEditable(false);
        requestName.setFont(new Font("Normal", Font.PLAIN, requestName.getFont().getSize() + 2));
        requestName.setBorder(new EmptyBorder(5, 0, 5, 5));
        requestName.setBackground(theme.getLabelTheme()[0][2]);
        requestName.setForeground(theme.getTextFieldTheme()[0][1]);
        requestName.setSelectionColor(theme.getTextFieldTheme()[0][1]);
        requestName.setFocusable(false);
        requestName.addMouseListener(handler);

        pagePanel.setLayout(new BorderLayout(5, 5));
        pagePanel.setPreferredSize(new Dimension(240, 35));
        pagePanel.setBackground(theme.getLabelTheme()[0][2]);
        pagePanel.addMouseListener(handler);
        pagePanel.add(requestMethod, BorderLayout.WEST);
        pagePanel.add(requestName, BorderLayout.CENTER);
        pagePanel.add(panel, BorderLayout.EAST);

        panel.setPreferredSize(new Dimension(43, 35));
        panel.setLayout(null);
        panel.setBorder(new EmptyBorder(0, 0, 0, 0));
        panel.setBackground(theme.getLabelTheme()[0][2]);
        panel.addMouseListener(handler);
        panel.add(menuBar ,BorderLayout.WEST);

        button.setFocusable(false);
        button.setBounds(0, 3, 35, 29);
        button.setBackground(theme.getButtonTheme()[0][0]);
        button.setLayout(new BorderLayout(0, 0));
        button.addMouseListener(handler);
        button.setIcon(new ImageIcon(theme.getAddressIcons() + "icons8_sort_down_10px.png"));

        menuBar.setPreferredSize(new Dimension(0, 0));
        menuBar.add(menu);

        menu.add(new JMenuItem("Delete"));
        menu.getItem(0).addActionListener(handler);
        menu.add(new JMenuItem("Save Request"));
        menu.getItem(1).addActionListener(handler);
        menu.add(new JMenuItem("Save Response Body"));
        menu.getItem(2).addActionListener(handler);
        menu.getItem(0).setIcon(new ImageIcon(theme.getAddressIcons() + "icons8_trash_can_15px.png"));
        menu.getItem(1).setIcon(new ImageIcon(theme.getAddressIcons() + "icons8_save_all_15px.png"));
        menu.getItem(2).setIcon(new ImageIcon(theme.getAddressIcons() + "icons8_save_as_15px.png"));
        menu.getItem(0).setAccelerator(KeyStroke.getKeyStroke("ctrl shift pressed D"));
        menu.getItem(1).setAccelerator(KeyStroke.getKeyStroke("ctrl S"));
        menu.getItem(2).setAccelerator(KeyStroke.getKeyStroke("ctrl shift pressed S"));
        menu.setMenuLocation(-18, -10 + panel.getPreferredSize().height);

        setOtherButtonDeSelect();

        comboBox.setPreferredSize(new Dimension(100, 50));
        comboBox.setBorder(new EmptyBorder(0, 0, 0, 0));
        comboBox.setBackground(theme.getComboBoxTheme()[0]);
        comboBox.setForeground(theme.getComboBoxTheme()[1]);
        comboBox.setSelectedItem(methodType);
        comboBox.addActionListener(handler);

        URL.setFont(new Font("Normal", Font.PLAIN, URL.getFont().getSize() + 4));
        URL.setBorder(new EmptyBorder(0, 0, 0, 0));
        URL.addKeyListener(handler);
        InputMap im = URL.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        im.put(KeyStroke.getKeyStroke("ctrl L"),"URL");

        URL.getActionMap().put("URL", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                URL.requestFocus();
            }
        });

        send.setPreferredSize(new Dimension(60, 50));
        send.setUI(new BasicButtonUI());
        setSendBackground();

        im = send.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        im.put(KeyStroke.getKeyStroke("ctrl ENTER"),"send");

        send.getActionMap().put("send", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendRequest();
            }
        });

        send.addActionListener(handler);
        send.updateUI();

        tabbedPane.addTab("Body", scrollPanes[0]);
        tabbedPane.addTab("Auth", scrollPanes[1]);
        tabbedPane.addTab("Query", scrollPanes[2]);
        tabbedPane.addTab("Header", scrollPanes[3]);
        tabbedPane.addTab("Docs", scrollPanes[4]);

        chooseBodyType.setSelectedIndex(4);
        chooseBodyType.addActionListener(handler);

        setDefaultPanels0(true);

        setDefaultPanels1();

        setDefaultPanels2();

        setDefaultPanels3();

        setDefaultPanels4();

        ((JPanel) getHold()[0].getComponent(0)).add(comboBox, BorderLayout.WEST);
        ((JPanel) getHold()[0].getComponent(0)).add(URL, BorderLayout.CENTER);
        ((JPanel) getHold()[0].getComponent(0)).add(send, BorderLayout.EAST);

        ((JPanel) getHold()[0].getComponent(1)).removeAll();
        ((JPanel) getHold()[0].getComponent(1)).setLayout(new BorderLayout(0, 0));
        ((JPanel) getHold()[0].getComponent(1)).add(tabbedPane, BorderLayout.CENTER);

        addHierarchyBoundsListener(handler);

        setDefaultResponse();

        comboBoxResponses.setPreferredSize(new Dimension(150, 50));
        comboBoxResponses.setBorder(new EmptyBorder(0, 0, 0, 0));
        comboBoxResponses.addActionListener(handler);

        sideBar.getPanels()[0].addMouseListener(handler);
        sideBar.getPanels()[1].addMouseListener(handler);

        setTheme(false);

    }

    /**
     * @param methodType is set the color for each method
     */
    public void setMethodColor(String methodType) {

        switch (methodType) {
            case "GET":
                requestMethod.setForeground(theme.getMethodColors()[0]);
                requestMethod.setText("GET");
                break;
            case "POST":
                requestMethod.setForeground(theme.getMethodColors()[1]);
                requestMethod.setText("POST");
                break;
            case "PUT":
                requestMethod.setForeground(theme.getMethodColors()[2]);
                requestMethod.setText("PUT");
                break;
            case "PATCH":
                requestMethod.setForeground(theme.getMethodColors()[3]);
                requestMethod.setText("PATCH");
                break;
            case "DELETE":
                requestMethod.setForeground(theme.getMethodColors()[4]);
                requestMethod.setText("DEL");
                break;
            case "OPTIONS":
                requestMethod.setForeground(theme.getMethodColors()[5]);
                requestMethod.setText("OPT");
                break;
            case "HEAD":
                requestMethod.setForeground(theme.getMethodColors()[6]);
                requestMethod.setText("HEAD");
                break;
            default:
                requestMethod.setForeground(theme.getMethodColors()[7]);
                if (methodType.length() >= 4) {
                    requestMethod.setText(methodType.substring(0, 4));
                } else {
                    requestMethod.setText(methodType);
                }
                break;
        }


    }

    /**
     *
     * @return the request string name
     */
    public String getRequestNameString() {
        return requestNameString;
    }

    /**
     *
     * @return the request method type
     */
    public String getMethodType() {
        return methodType;
    }

    /**
     *
     * @return the array list of response
     */
    public ArrayList<Response> getResponses() {
        return responses;
    }

    /**
     * @return the name field of request
     */
    public JTextField getRequestName() {
        return requestName;
    }

    /**
     * @return the panel for accessing to this request from side bar
     */
    public JPanel getPageLabel() {
        return pagePanel;
    }

    /**
     * @param selected if we select a request the other must be deselected
     */
    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    /**
     * @return return the button information which is inside of the page label
     */
    public JButton getButton() {
        return button;
    }

    /**
     *
     * @return the panel of button in page label request
     */
    @Override
    public JPanel getPanel() {
        return panel;
    }

    public JComboBox<? extends String> getComboBox() {
        return comboBox;
    }

    /**
     * set the tab 1 default form
     */
    public void setDefaultPanels0(boolean deleteAll) {

        panels[0].removeAll();

        if (deleteAll) {
            arrayLists[0] = new ArrayList<>();
        }

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        panel.setBackground(theme.getBackGroundTheme()[2]);
        panel.add(chooseBodyType);

        panels[0].add(panel);

        if (chooseBodyType.getSelectedIndex() == 4) {
            JPanel jPanel = new JPanel();

            JLabel label = new JLabel("Select a body type from above");
            JLabel label1 = new JLabel(new ImageIcon(theme.getAddressIcons() + "icons8_hand_peace_160px.png"));

            setTabbedPane(jPanel, label, label1);
            panels[0].add(jPanel);
        } else if (chooseBodyType.getSelectedIndex() < 2) {
            if (arrayLists[0].size() == 0) {
                addField(0);
            } else {
                for (JPanel jPanel : arrayLists[0]) {
                    panels[0].add(jPanel);
                }
            }
        } else if (chooseBodyType.getSelectedIndex() == 3) {

            JButton[] buttons = new JButton[2];

            buttons[0] = new JButton("Reset File");
            buttons[0].setSize(80, 35);
            buttons[0].setFocusable(false);
            buttons[0].addMouseListener(handler);

            buttons[1] = new JButton("Choose File");
            buttons[1].setSize(80, 35);
            buttons[1].setFocusable(false);
            buttons[1].addMouseListener(handler);

            JPanel jPanel = new JPanel();
            jPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
            jPanel.add(buttons[0]);
            jPanel.add(buttons[1]);

            JTextField fileAddress = new JTextField(binaryFileAddress);
            fileAddress.setOpaque(true);
            fileAddress.setFont(new Font("Normal", Font.PLAIN, fileAddress.getFont().getSize() + 1));
            fileAddress.setEditable(false);
            fileAddress.setBorder(BorderFactory.createLineBorder(theme.getLabelTheme()[1][3], 2));
            fileAddress.addKeyListener(handler);

            panels[0].add(fileAddress);
            panels[0].add(jPanel);

        } else if (chooseBodyType.getSelectedIndex() == 2) {

            JScrollPane scrollPane = new JScrollPane(textArea);
            textArea.setLineWrap(true);

            scrollPane.setUI(new WindowsScrollPaneUI());
            scrollPane.getHorizontalScrollBar().setUI(new WindowsScrollBarUI());
            scrollPane.getVerticalScrollBar().setUI(new WindowsScrollBarUI());

            panels[0].add(scrollPane);

        }

        setTheme(false);
    }

    /**
     * set the tab 2 default form
     */
    private void setDefaultPanels1() {

        JPanel panel = new JPanel();
        JLabel label = new JLabel("Select an auth type from above");
        JLabel label1 = new JLabel(new ImageIcon(theme.getAddressIcons() + "icons8_padlock_160px.png"));

        setTabbedPane(panel, label, label1);

        panels[1].removeAll();
        panels[1].add(panel);

    }

    /**
     * since tab 1 and 2 is use the same way to creat so they use this method
     */
    private void setTabbedPane(JPanel panel, JLabel label, JLabel label1) {

        panel.setLayout(null);
        panel.setBounds(getParts()[1].getWidth() / 2 - 100, getParts()[1].getHeight() / 2 - 100, 200, 200);
        panel.setBackground(theme.getBackGroundTheme()[2]);
        panel.add(label1, BorderLayout.NORTH);
        panel.add(label, BorderLayout.CENTER);

        label1.setBounds(0, 0, 200, 160);

        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBounds(0, 160, 200, 40);
        label.setFont(new Font("Normal", Font.BOLD, label.getFont().getSize()));
        label.setBackground(theme.getLabelTheme()[3][0]);
        label.setForeground(theme.getLabelTheme()[3][1]);

        label1.setHorizontalAlignment(SwingConstants.CENTER);

    }

    /**
     * set the tab 2 default form
     */
    public void setDefaultPanels2() {

        panels[2].removeAll();

        JTextField textField = new JTextField(URL.getText());

        textField.setOpaque(true);
        textField.setFont(new Font("Normal", Font.PLAIN, textField.getFont().getSize() + 4));
        textField.setEditable(false);
        textField.setBorder(BorderFactory.createLineBorder(theme.getLabelTheme()[1][3], 2));

        arrayLists[1] = new ArrayList<>();

        panels[2].add(textField);

        addField(1);

        setTheme(false);

    }

    /**
     * set the tab 3 default form
     */
    public void setDefaultPanels3() {

        arrayLists[2] = new ArrayList<>();
        panels[3].removeAll();

        addField(2);

    }

    /**
     *
     * @param index get the array list index
     */
    public void addField(int index) {

        int i = 1;

        int ind = 0;

        if (index != 0) {
            ind = index + 1;
        }

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(theme.getBackGroundTheme()[2]);

        if ((arrayLists[0].size() == 0 && chooseBodyType.getSelectedIndex() < 2 && index == 0) || arrayLists[1].size() == 0 || arrayLists[2].size() == 0) {
            panel.add(new JButton(new ImageIcon(theme.getAddressIcons() + "icons8_delete_database_15px.png")));
            ((JButton) panel.getComponent(0)).setHorizontalAlignment(SwingConstants.CENTER);
            panel.getComponent(0).addMouseListener(handler);
            panel.getComponent(0).setBackground(theme.getBackGroundTheme()[2]);
            arrayLists[index].add(panel);
            panels[ind].add(panel);
            i = 0;
        } else {
            panel.add(new JButton(new ImageIcon(theme.getAddressIcons() + "icons8_text_input_form_15px.png")));
            panel.getComponent(0).setBackground(theme.getButtonTheme()[0][0]);
            ((JButton) panel.getComponent(0)).setHorizontalAlignment(SwingConstants.CENTER);
            panel.getComponent(0).addMouseListener(handler);

            panel.add(new JCheckBox());
            panel.getComponent(1).setBackground(theme.getBackGroundTheme()[2]);
            ((JCheckBox) panel.getComponent(1)).setHorizontalAlignment(SwingConstants.CENTER);
            panel.getComponent(1).addMouseListener(handler);
            ((JCheckBox) panel.getComponent(1)).setSelected(true);

            panel.add(new JButton(new ImageIcon(theme.getAddressIcons() + "icons8_trash_can_15px-1.png")));
            panel.getComponent(2).setBackground(theme.getButtonTheme()[0][0]);
            ((JButton) panel.getComponent(2)).setHorizontalAlignment(SwingConstants.CENTER);
            panel.getComponent(2).addMouseListener(handler);

            arrayLists[index].add(arrayLists[index].size() - 1, panel);
            panels[ind].add(panel, panels[ind].getComponentCount() - 1);
        }

        panel.add(new JTextField(), 1 - i);
        panel.getComponent(1 - i).addMouseListener(handler);
        panel.getComponent(1 - i).setBackground(theme.getTextFieldTheme()[0][0]);
        panel.getComponent(1 - i).setForeground(theme.getTextFieldTheme()[0][1]);

        panel.add(new JTextField(), 2 - i);
        panel.getComponent(2 - i).addMouseListener(handler);
        panel.getComponent(2 - i).setBackground(theme.getTextFieldTheme()[0][0]);
        panel.getComponent(2 - i).setForeground(theme.getTextFieldTheme()[0][1]);

        if (i == 0) {

            panel.getComponent(1).setFocusable(false);
            ((JTextField) panel.getComponent(1)).setText("New name");

            panel.getComponent(2).setFocusable(false);
            ((JTextField) panel.getComponent(2)).setText("New value");

        } else {

            panel.getComponent(0).addFocusListener(handler);
            panel.getComponent(0).addKeyListener(handler);
            ((JTextField) panel.getComponent(0)).setText("name");

            panel.getComponent(1).addFocusListener(handler);
            panel.getComponent(1).addKeyListener(handler);
            ((JTextField) panel.getComponent(1)).setText("value");
        }

        if ((arrayLists[0].size() != 0 || chooseBodyType.getSelectedIndex() > 1) && arrayLists[1].size() != 0 && arrayLists[2].size() != 0) {
            setLayout(false);
        }

    }

    /**
     * set the tab 5 default form
     */
    private void setDefaultPanels4() {

        panels[4].removeAll();

        JPanel panel = new JPanel();
        JButton button = new JButton("Add Description");
        JLabel label1 = new JLabel(new ImageIcon(theme.getAddressIcons() + "icons8_document_160px.png"));

        panel.setLayout(null);
        panel.setBounds(getHold()[0].getWidth() / 2 - 100, getHold()[0].getHeight() / 2 - 125, 200, 200);
        panel.setBackground(theme.getBackGroundTheme()[2]);
        panel.add(label1);
        panel.add(button);

        label1.setBounds(0, 0, 200, 160);

        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setBounds(0, 160, 200, 40);
        button.setFont(new Font("Normal", Font.BOLD, button.getFont().getSize()));
        button.setBackground(theme.getButtonTheme()[0][0]);
        button.setForeground(theme.getButtonTheme()[0][1]);
        button.addMouseListener(handler);

        label1.setHorizontalAlignment(SwingConstants.CENTER);

        panels[4].add(panel);

    }

    /**
     * set the default page for response
     */
    public void setDefaultResponse() {

        getParts()[2].removeAll();
        getParts()[3].removeAll();
        getParts()[3].setLayout(null);

        JLabel[] labels = new JLabel[4];
        JPanel panel = new JPanel();

        panel.setLayout(new GridLayout(2, 2, 10, 10));
        panel.setBackground(theme.getBackGroundTheme()[2]);

        labels[0] = new JLabel("Send Request");

        labels[1] = new JLabel("Ctrl+Enter");

        labels[2] = new JLabel("Focus Url Bar");

        labels[3] = new JLabel("Ctrl+L");

        for (int i = 0; i < 2; i++) {

            labels[2 * i + 1].setBackground(theme.getLabelTheme()[1][0]);
            labels[2 * i + 1].setForeground(theme.getLabelTheme()[1][1]);
            labels[2 * i + 1].setBorder(BorderFactory.createLineBorder(theme.getLabelTheme()[1][3]));
            labels[2 * i + 1].setHorizontalAlignment(SwingConstants.CENTER);

            labels[2 * i].setBackground(theme.getBackGroundTheme()[2]);
            labels[2 * i].setForeground(theme.getLabelTheme()[1][1]);

        }

        for (int i = 0; i < 4; i++) {
            panel.add(labels[i]);
            labels[i].setOpaque(true);
        }

        getParts()[3].add(panel);

    }

    /**
     * when we select a new request from list of request the other request panels must be deselected
     */
    public void setOtherButtonDeSelect() {
        for (Request request : sideBar.getRequests()) {
            if (!request.equals(this)) {
                request.setSelected(false);
                request.getButton().setEnabled(true);
                request.getPageLabel().remove(request.getButton());
                request.setBackground();
                request.getButton().add(request.getMenuBar() ,BorderLayout.WEST);
            } else {
                if (button.isShowing()) {
                    button.add(menuBar ,BorderLayout.WEST);
                } else {
                    panel.add(menuBar, BorderLayout.WEST);
                }
            }
        }
    }

    /**
     *
     * @return the JMenuBar for button
     */
    public JMenuBar getMenuBar() {
        return menuBar;
    }

    /**
     *
     * @return the value of this request is selected or not
     */
    public boolean isSelected() {
        return isSelected;
    }

    /**
     * set every components that not use Layout Manager
     */
    @Override
    protected void setLayout(boolean update) {

        if (update) {
            try {
                super.setLayout(true);
            } catch (NullPointerException ignored) {
            }
        }

        if (responses.size() == 0 && getParts()[3].getComponentCount() != 0) {
            getParts()[3].getComponent(0).setBounds(getHold()[1].getWidth() / 2 - 155, getParts()[3].getHeight() / 2 - 40, 310, 80);
        }

        for (int index = 0; index < 3; index++) {

            int ind = 0;

            if (index != 0) {
                ind = index + 1;
            }

            for (JPanel jPanel : arrayLists[index]) {
                if (index == 2) {
                    jPanel.setBounds(0, 5 + 40 * arrayLists[index].indexOf(jPanel), getHold()[0].getWidth(), 35);
                } else {
                    jPanel.setBounds(0, 45 + 40 * arrayLists[index].indexOf(jPanel), getHold()[0].getWidth(), 35);
                }
            }

            if (5 + 40 * panels[ind].getComponentCount() > this.getHeight() - 70) {
                for (JPanel jPanel : arrayLists[index]) {
                    if (arrayLists[index].indexOf(jPanel) < arrayLists[index].size() - 1) {

                        jPanel.getComponent(0).setBounds(38, 0, 100 + (getHold()[0].getWidth() - 375) / 2, 35);
                        jPanel.getComponent(1).setBounds(141 + (getHold()[0].getWidth() - 375) / 2, 0, 100 + (getHold()[1].getWidth() - 375) / 2, 35);
                        jPanel.getComponent(2).setBounds(244 + (getHold()[0].getWidth() - 375), 5, 33, 25);
                        jPanel.getComponent(3).setBounds(277 + (getHold()[0].getWidth() - 375), 5, 30, 25);
                        jPanel.getComponent(4).setBounds(307 + (getHold()[0].getWidth() - 375), 5, 33, 25);

                    } else {

                        jPanel.getComponent(0).setBounds(3, 5, 33, 25);
                        jPanel.getComponent(1).setBounds(38, 0, 100 + (getHold()[0].getWidth() - 375) / 2, 35);
                        jPanel.getComponent(2).setBounds(141 + (getHold()[1].getWidth() - 375) / 2, 0, 100 + (getHold()[1].getWidth() - 375) / 2, 35);

                    }
                }

                try {
                    if (ind != 3) {
                        panels[ind].getComponent(0).setBounds(5, 5, getParts()[0].getWidth() - 27, 35);

                        if (ind == 0 && chooseBodyType.getSelectedIndex() == 3) {
                            panels[0].getComponent(1).setBounds(5, 55, getHold()[0].getWidth() - 27, 35);
                            panels[0].getComponent(2).setBounds(5, 90, getHold()[0].getWidth() - 27, 35);
                        } else if (ind == 0 && chooseBodyType.getSelectedIndex() == 2) {
                            panels[0].getComponent(1).setBounds(0, 55, getHold()[0].getWidth() - 17, getParts()[1].getHeight() - 55);
                        }

                    }
                } catch (ArrayIndexOutOfBoundsException ignore) {
                }

                panels[ind].setPreferredSize(new Dimension(getHold()[0].getWidth() - 20, 5 + 40 * panels[ind].getComponentCount()));

            } else {
                for (JPanel jPanel : arrayLists[index]) {
                    if (arrayLists[index].indexOf(jPanel) < arrayLists[index].size() - 1) {

                        jPanel.getComponent(0).setBounds(40, 0, 100 + (getHold()[0].getWidth() - 375) / 2, 35);
                        jPanel.getComponent(1).setBounds(145 + (getHold()[0].getWidth() - 375) / 2, 0, 100 + (getHold()[1].getWidth() - 375) / 2, 35);
                        jPanel.getComponent(2).setBounds(250 + (getHold()[0].getWidth() - 375), 5, 33, 25);
                        jPanel.getComponent(3).setBounds(285 + (getHold()[0].getWidth() - 375), 5, 30, 25);
                        jPanel.getComponent(4).setBounds(317 + (getHold()[0].getWidth() - 375), 5, 33, 25);

                    } else {

                        jPanel.getComponent(0).setBounds(5, 5, 33, 25);
                        jPanel.getComponent(1).setBounds(40, 0, 100 + (getHold()[0].getWidth() - 375) / 2, 35);
                        jPanel.getComponent(2).setBounds(145 + (getHold()[0].getWidth() - 375) / 2, 0, 100 + (getHold()[1].getWidth() - 375) / 2, 35);

                    }
                }

                try {
                    if (ind != 3) {
                        panels[ind].getComponent(0).setBounds(5, 5, getHold()[0].getWidth() - 12, 35);

                        if (ind == 0 && chooseBodyType.getSelectedIndex() == 3) {
                            panels[0].getComponent(1).setBounds(5, 55, getHold()[0].getWidth() - 12, 35);
                            panels[0].getComponent(2).setBounds(5, 90, getHold()[0].getWidth() - 12, 35);
                        } else if (ind == 0 && chooseBodyType.getSelectedIndex() == 2) {
                            panels[0].getComponent(1).setBounds(0, 55, getHold()[0].getWidth() - 2, getParts()[1].getHeight() - 55);
                        }

                    }
                } catch (ArrayIndexOutOfBoundsException ignore) {
                }

                panels[ind].setPreferredSize(new Dimension(getHold()[0].getWidth() - 2, 5 + 40 * panels[ind].getComponentCount()));
                panels[ind].setSize(new Dimension(getHold()[0].getWidth() - 2, 5 + 40 * panels[ind].getComponentCount()));

            }

            if (chooseBodyType.getSelectedIndex() == 4) {
                panels[0].getComponent(1).setBounds(getParts()[1].getWidth() / 2 - 100, getParts()[1].getHeight() / 2 - 100, 200, 200);
            }

            try {
                panels[1].getComponent(0).setBounds(getParts()[1].getWidth() / 2 - 100, getParts()[1].getHeight() / 2 - 100, 200, 200);
                panels[4].getComponent(0).setBounds(getParts()[1].getWidth() / 2 - 100, getParts()[1].getHeight() / 2 - 100, 200, 200);
            } catch (ArrayIndexOutOfBoundsException ignore) {
            }

            try {
                panels[ind].updateUI();
            } catch (NullPointerException ignore) {
            }

        }

    }

    /**
     *
     * @param colorUpdate if the theme field is changing the value is true
     */
    public void setTheme(boolean colorUpdate) {

        try {
            super.setTheme();

            bodyType = chooseBodyType.getSelectedIndex();

            if (chooseBodyType.getSelectedIndex() == 3) {

                panels[0].getComponent(1).setBackground(theme.getLabelTheme()[1][0]);
                panels[0].getComponent(1).setForeground(theme.getLabelTheme()[1][1]);
                panels[0].getComponent(2).setBackground(theme.getBackGroundTheme()[2]);

                ((JPanel) panels[0].getComponent(2)).getComponent(0).setBackground(theme.getButtonTheme()[0][0]);
                ((JPanel) panels[0].getComponent(2)).getComponent(0).setForeground(theme.getButtonTheme()[0][1]);

                ((JPanel) panels[0].getComponent(2)).getComponent(1).setBackground(theme.getButtonTheme()[0][0]);
                ((JPanel) panels[0].getComponent(2)).getComponent(1).setForeground(theme.getButtonTheme()[0][1]);

            }

            if (chooseBodyType.getSelectedIndex() == 2) {
                panels[0].getComponent(1).setBackground(theme.getBackGroundTheme()[2]);
                panels[0].getComponent(1).setForeground(theme.getTextFieldTheme()[0][1]);
                textArea.setBackground(theme.getBackGroundTheme()[2]);
                textArea.setForeground(theme.getTextFieldTheme()[0][1]);
            }

            if (colorUpdate) {

                for (int i = 0; i < 3; i++) {
                    for (JPanel panel : arrayLists[i]) {
                        if (arrayLists[i].indexOf(panel) != arrayLists[i].size() - 1) {

                            panel.setBackground(theme.getBackGroundTheme()[2]);

                            for (int j = 0; j < 2; j++) {
                                panel.getComponent(j).setBackground(theme.getTextFieldTheme()[0][0]);
                                if (((JCheckBox) panel.getComponent(3)).isSelected()) {
                                    if (panel.getComponent(j).getForeground().equals(Color.white) || panel.getComponent(j).getForeground().equals(new Color(100, 100, 100))) {
                                        panel.getComponent(j).setForeground(theme.getTextFieldTheme()[0][2]);
                                    } else {
                                        panel.getComponent(j).setForeground(theme.getTextFieldTheme()[0][1]);
                                    }
                                } else {
                                    if (panel.getComponent(j).getForeground().equals(new Color(171, 171, 171)) || panel.getComponent(j).getForeground().equals(new Color(78, 80, 76))) {
                                        panel.getComponent(j).setForeground(theme.getTextFieldTheme()[1][2]);
                                    } else {
                                        panel.getComponent(j).setForeground(theme.getTextFieldTheme()[1][1]);
                                    }
                                }
                            }

                            panel.getComponent(2).setBackground(theme.getButtonTheme()[0][0]);
                            ((JButton) panel.getComponent(2)).setIcon(new ImageIcon(theme.getAddressIcons() + "icons8_text_input_form_15px.png"));
                            panel.getComponent(3).setBackground(theme.getBackGroundTheme()[2]);
                            panel.getComponent(4).setBackground(theme.getButtonTheme()[0][0]);
                            ((JButton) panel.getComponent(4)).setIcon(new ImageIcon(theme.getAddressIcons() + "icons8_trash_can_15px-1.png"));

                        } else {
                            panel.setBackground(theme.getBackGroundTheme()[2]);
                            panel.getComponent(0).setBackground(theme.getButtonTheme()[0][0]);
                            ((JButton) panel.getComponent(0)).setIcon(new ImageIcon(theme.getAddressIcons() + "icons8_delete_database_15px.png"));
                            panel.getComponent(1).setBackground(theme.getTextFieldTheme()[0][0]);
                            panel.getComponent(1).setForeground(theme.getTextFieldTheme()[0][1]);
                            panel.getComponent(2).setBackground(theme.getTextFieldTheme()[0][0]);
                            panel.getComponent(2).setForeground(theme.getTextFieldTheme()[0][1]);
                        }
                    }
                }

                if (isSelected) {
                    setHover();
                } else {
                    setBackground();
                }

                button.setBackground(theme.getButtonTheme()[0][0]);

                button.setIcon(new ImageIcon(theme.getAddressIcons() + "icons8_sort_down_10px.png"));

                requestName.setForeground(theme.getTextFieldTheme()[0][1]);

                for (int i = 0; i < 5; i++) {
                    panels[i].setBackground(theme.getBackGroundTheme()[2]);
                }

                setDefaultPanels1();
                setDefaultPanels4();

            }

            URL.setBackground(theme.getTextFieldTheme()[2][0]);
            URL.setForeground(theme.getTextFieldTheme()[2][1]);

            chooseBodyType.setSelectedIndex(bodyType);

            comboBoxResponses.setBackground(theme.getComboBoxTheme()[0]);

            panels[2].getComponent(0).setBackground(theme.getLabelTheme()[1][0]);
            panels[2].getComponent(0).setForeground(theme.getLabelTheme()[1][1]);
            ((JTextField) panels[2].getComponent(0)).setBorder(BorderFactory.createLineBorder(theme.getLabelTheme()[1][3], 2));

            try {
                getThis().updateUI();
            } catch (NullPointerException ignore) {
            }

        } catch (NullPointerException | ArrayIndexOutOfBoundsException ignore) {
        }

    }

    /**
     * set page panel of request lighter when the pointer is on page panel
     */
    void setHover() {
        pagePanel.setBackground(theme.getLabelTheme()[0][2]);
        requestName.setBackground(theme.getLabelTheme()[0][2]);
        panel.setBackground(theme.getLabelTheme()[0][2]);
    }

    /**
     * set page panel of request to his normal color when the pointer is out of page panel
     */
    private void setBackground() {
        pagePanel.setBackground(theme.getLabelTheme()[0][0]);
        requestName.setBackground(theme.getTextFieldTheme()[0][0]);
        panel.setBackground(theme.getLabelTheme()[0][0]);
    }

    /**
     * set send hover
     */
    private void setSendHover() {
        send.setBackground(theme.getButtonTheme()[1][2]);
        send.setForeground(theme.getButtonTheme()[1][1]);
    }

    /**
     * set send button background
     */
    private void setSendBackground() {
        send.setBackground(theme.getButtonTheme()[1][0]);
        send.setForeground(theme.getButtonTheme()[1][1]);
    }

    /**
     * @return this panel
     */
    private Request getThis() {
        return this;
    }

    /**
     *
     * @return the array lists of body type - query - header
     */
    public ArrayList<JPanel>[] getArrayLists() {
        return arrayLists;
    }

    /**
     *
     * @return the response JComboBox
     */
    public JComboBox<String> getComboBoxResponses() {
        return comboBoxResponses;
    }

    /**
     *
     * @param requestNameString set the request string name
     */
    public void setRequestNameString(String requestNameString) {
        this.requestNameString = requestNameString;
    }

    /**
     *
     * @return the save form data and from urlencoded
     */
    public ArrayList<String[]> getSaveFormBody() {
        return saveFormBody;
    }

    /**
     *
     * @return the save query array list
     */
    public ArrayList<String[]> getSaveQuery() {
        return saveQuery;
    }

    /**
     *
     * @return the save header array list
     */
    public ArrayList<String[]> getSaveHeader() {
        return saveHeader;
    }

    /**
     *
     * @return the index of body type of request
     */
    public int getBodyType() {
        return bodyType;
    }

    /**
     *
     * @return the URL string address
     */
    public String getUrl() {
        return url;
    }

    /**
     *
     * @return the binary file address
     */
    public String getBinaryFileAddress() {
        return binaryFileAddress;
    }

    /**
     *
     * @return the JComboBox of request body type
     */
    public JComboBox<String> getChooseBodyType() {
        return chooseBodyType;
    }

    /**
     *
     * @return the URL JTextField
     */
    public JTextField getURL() {
        return URL;
    }

    /**
     *
     * @param binaryFileAddress set the binary file address
     */
    public void setBinaryFileAddress(String binaryFileAddress) {
        this.binaryFileAddress = binaryFileAddress;
    }

    /**
     *
     * @return the json text area
     */
    public JTextArea getTextArea() {
        return textArea;
    }

    /**
     *
     * @return the json command
     */
    public String getJson() {
        return json;
    }

    /**
     * save the prime data of this request before it's save in a file
     */
    public void savePrimaryData() {

        saveFormBody = new ArrayList<>();
        saveQuery = new ArrayList<>();
        saveHeader = new ArrayList<>();

        new Response(theme).saveData(arrayLists[0], saveFormBody);
        new Response(theme).saveData(arrayLists[1], saveQuery);
        new Response(theme).saveData(arrayLists[2], saveHeader);

        url = URL.getText();
        json = textArea.getText();

    }

    /**
     * set the url address with query
     */
    public void setQuery() {

        StringBuilder stringBuilder = new StringBuilder(URL.getText());

        if (stringBuilder.toString().contains("?")) {

            for (JPanel panel : arrayLists[1]) {

                if (arrayLists[1].indexOf(panel) != arrayLists[1].size() - 1) {

                    JTextField key = ((JTextField) panel.getComponent(0));
                    JTextField value = ((JTextField) panel.getComponent(1));

                    if (key.getForeground().equals(theme.getTextFieldTheme()[0][2]) && ((JCheckBox) panel.getComponent(3)).isSelected() && !key.getText().equals("")) {

                        if (!stringBuilder.toString().endsWith("?") && !stringBuilder.toString().endsWith("&")) {
                            stringBuilder.append("&");
                        }

                        setUrlQuery(stringBuilder, key, value);

                    }

                }

            }

        } else {

            for (JPanel panel : arrayLists[1]) {

                if (arrayLists[1].indexOf(panel) != arrayLists[1].size() - 1) {

                    JTextField key = ((JTextField) panel.getComponent(0));
                    JTextField value = ((JTextField) panel.getComponent(1));

                    if (key.getForeground().equals(theme.getTextFieldTheme()[0][2]) && ((JCheckBox) panel.getComponent(3)).isSelected() && !key.getText().equals("")) {

                        if (!stringBuilder.toString().contains("?")) {
                            stringBuilder.append("?");
                        } else if(!stringBuilder.toString().endsWith("?")){
                            stringBuilder.append("&");
                        }

                        setUrlQuery(stringBuilder, key, value);

                    }

                }
            }

        }
        ((JTextField) panels[2].getComponent(0)).setText(stringBuilder.toString());


    }

    /**
     *
     * @param stringBuilder the url to be build
     * @param key the key of query
     * @param value the value of query
     */
    private void setUrlQuery(StringBuilder stringBuilder, JTextField key, JTextField value) {
        char[] split = key.getText().toCharArray();

        for (char c : split) {
            if ((47 < c && c < 58) || (64 < c && c < 91) || (94 < c && c < 123)) {
                stringBuilder.append(c);
            } else {
                stringBuilder.append("%").append(Integer.toHexString(c));
            }
        }

        if (!value.getText().equals("") && value.getForeground().equals(theme.getTextFieldTheme()[0][2])) {
            stringBuilder.append("=");

            split = value.getText().toCharArray();

            for (char c : split) {
                if ((47 < c && c < 58) || (64 < c && c < 91) || (94 < c && c < 123)) {
                    stringBuilder.append(c);
                } else {
                    stringBuilder.append("%").append(Integer.toHexString(c));
                }
            }

        }
    }

    /**
     * send request information
     */
    private void sendRequest() {
        responses.add(0, new Response(theme, ((String) comboBox.getSelectedItem()), chooseBodyType.getSelectedIndex(), ((JTextField) panels[2].getComponent(0)).getText(), URL.getText(), arrayLists[2], arrayLists[1], arrayLists[0], binaryFileAddress, textArea.getText()));

        Thread thread = new Thread(responses.get(0));

        thread.start();

        try {
            thread.join(800);
        } catch (InterruptedException ignore) {
        }

        String date = responses.get(0).getDateOfSendingRequest();
        comboBoxResponses.insertItemAt(responses.size() + ")" + date + " " + responses.get(0).getMethod() + " " + responses.get(0).getOriginalUrl(), 2);
        comboBoxResponses.setSelectedIndex(2);

        getParts()[2].removeAll();
        getParts()[3].removeAll();
        getParts()[3].setLayout(new BorderLayout(0, 0));
        getParts()[2].add(responses.get(0).getPanel(), BorderLayout.CENTER);
        getParts()[2].add(comboBoxResponses, BorderLayout.EAST);
        getParts()[3].add(responses.get(0).getTabbedPane(), BorderLayout.CENTER);

        try {
            getParts()[2].updateUI();
        } catch (NullPointerException ignore) {
        }
    }

    /**
     * an inner class which control events
     */
    private class Handler implements MouseListener, HierarchyBoundsListener, ActionListener, FocusListener, KeyListener, ChangeListener {

        @Override
        public void mouseClicked(MouseEvent e) {

            if (e.getSource().equals(button) && button.isEnabled()) {
                menu.doClick();
                button.setEnabled(false);
                panel.remove(button);
                if (isSelected) {
                    setHover();
                    panel.add(menuBar,BorderLayout.WEST);
                } else {
                    setBackground();
                    button.add(menuBar ,BorderLayout.WEST);
                }
            } else if (panels[4].getComponent(0) instanceof JPanel && e.getSource().equals(((JPanel) panels[4].getComponent(0)).getComponent(1))) {
                panels[4].removeAll();

                JTextArea textArea = new JTextArea();

                textArea.setBackground(theme.getBackGroundTheme()[2]);
                textArea.setForeground(theme.getTextFieldTheme()[0][1]);

                panels[4].setLayout(new BorderLayout());
                panels[4].add(textArea, BorderLayout.CENTER);

                try {
                    getParts()[1].updateUI();
                } catch (NullPointerException ignore) {
                }

            }


            for (int i = 0; i < 3; i++) {
                if (arrayLists[i].size() > 0 && (e.getSource().equals(arrayLists[i].get(arrayLists[i].size() - 1).getComponent(1))
                        || e.getSource().equals(arrayLists[i].get(arrayLists[i].size() - 1).getComponent(2)))) {
                    addField(i);
                }
            }


            for (JPanel panel : arrayLists[1]) {
                if (arrayLists[1].size() > 0 && arrayLists[1].indexOf(panel) != arrayLists[1].size() - 1 && e.getSource().equals(panel.getComponent(3))) {
                    setQuery();
                }
            }

            if (arrayLists[2].size() > 0 && e.getSource().equals(arrayLists[2].get(arrayLists[2].size() - 1).getComponent(0))) {
                setDefaultPanels3();
            } else if (arrayLists[1].size() > 0 && e.getSource().equals(arrayLists[1].get(arrayLists[1].size() - 1).getComponent(0))) {
                setDefaultPanels2();
            } else if (arrayLists[0].size() > 0 && e.getSource().equals(arrayLists[0].get(arrayLists[0].size() - 1).getComponent(0))) {
                setDefaultPanels0(true);
            }

            try {
                getThis().updateUI();
            } catch (NullPointerException ignore) {
            }

        }

        @Override
        public void mousePressed(MouseEvent e) {

            if ((e.getSource().equals(pagePanel) || e.getSource().equals(requestMethod) || e.getSource().equals(requestName) || e.getSource().equals(panel)) && button.isEnabled()) {
                sideBar.setUnripePage(getThis());
                setHover();
                isSelected = true;
                setOtherButtonDeSelect();
            } else if (chooseBodyType.getSelectedIndex() == 3 && e.getSource().equals(((JPanel) panels[0].getComponent(2)).getComponent(1))) {

                JFrame parentFrame = new JFrame();

                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Open Response Body");

                if (fileChooser.showOpenDialog(parentFrame) == JFileChooser.APPROVE_OPTION) {
                    ((JTextField) panels[0].getComponent(1)).setText(fileChooser.getSelectedFile().getAbsolutePath());
                    binaryFileAddress = fileChooser.getSelectedFile().getAbsolutePath();
                }

            } else if (chooseBodyType.getSelectedIndex() == 3 && e.getSource().equals(((JPanel) panels[0].getComponent(2)).getComponent(0))) {
                ((JTextField) panels[0].getComponent(1)).setText("");
            }

            for (int i = 0; i < 3; i++) {

                int index = 0;

                if (i != 0) {
                    index = i + 1;
                }

                for (JPanel panel : arrayLists[i]) {
                    if (panel.getComponentCount() == 5) {
                        if (e.getSource().equals(panel.getComponent(4))) {
                            arrayLists[i].remove(panel);
                            panels[index].remove(panel);
                            setLayout(false);
                            break;
                        } else if (e.getSource().equals(panel.getComponent(3))) {
                            for (int j = 0; j < 3; j++) {
                                if (((JCheckBox) panel.getComponent(3)).isSelected()) {
                                    if (panel.getComponent(j).getForeground().equals(theme.getTextFieldTheme()[0][2])) {
                                        panel.getComponent(j).setForeground(theme.getTextFieldTheme()[1][2]);
                                    } else {
                                        panel.getComponent(j).setForeground(theme.getTextFieldTheme()[1][1]);
                                    }
                                } else {
                                    if (panel.getComponent(j).getForeground().equals(theme.getTextFieldTheme()[1][2])) {
                                        panel.getComponent(j).setForeground(theme.getTextFieldTheme()[0][2]);
                                    } else {
                                        panel.getComponent(j).setForeground(theme.getTextFieldTheme()[0][1]);
                                    }
                                }
                            }
                        } else if (e.getSource().equals(panel.getComponent(2))) {

                            JTextArea textArea = new JTextArea();
                            if (!panel.getComponent(1).getForeground().equals(theme.getTextFieldTheme()[0][1])) {
                                textArea.setText(((JTextField) panel.getComponent(1)).getText());
                            }

                            textArea.setBackground(theme.getTextFieldTheme()[0][0]);

                            if (((JCheckBox) panel.getComponent(3)).isSelected()) {
                                textArea.setForeground(theme.getTextFieldTheme()[0][2]);
                            } else {
                                textArea.setForeground(theme.getTextFieldTheme()[0][1]);
                            }

                            JScrollPane scrollPane = new JScrollPane(textArea);
                            scrollPane.setPreferredSize(new Dimension(400, 400));

                            int result = JOptionPane.showOptionDialog(
                                    null,
                                    scrollPane,
                                    "Edit",
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.QUESTION_MESSAGE,
                                    new ImageIcon(""),
                                    new String[]{"Done"},
                                    "Done");
                            if (result == 0) {
                                if (!textArea.getText().equals("")) {
                                    ((JTextField) panel.getComponent(1)).setText(textArea.getText());
                                    if (((JCheckBox) panel.getComponent(3)).isSelected()) {
                                        panel.getComponent(1).setForeground(theme.getTextFieldTheme()[0][2]);
                                    } else {
                                        panel.getComponent(1).setForeground(theme.getTextFieldTheme()[1][2]);
                                    }
                                } else {
                                    ((JTextField) panel.getComponent(1)).setText("value");
                                    if (((JCheckBox) panel.getComponent(3)).isSelected()) {
                                        panel.getComponent(1).setForeground(theme.getTextFieldTheme()[0][1]);
                                    } else {
                                        panel.getComponent(1).setForeground(theme.getTextFieldTheme()[1][1]);
                                    }
                                }

                            }

                        }
                    }
                }

            }

            if (e.getSource().equals(send)) {
                setSendBackground();
                try {
                    send.updateUI();
                } catch (NullPointerException ignore) {
                }
            }

            try {
                getThis().updateUI();
            } catch (NullPointerException ignore) {
            }

        }

        @Override
        public void mouseReleased(MouseEvent e) {

            if (e.getSource().equals(panel)) {
                panel.add(button, BorderLayout.CENTER);
                button.add(menuBar, BorderLayout.WEST);
                setHover();
            }

            if (e.getSource().equals(send)) {
                setSendHover();
                try {
                    send.updateUI();
                } catch (NullPointerException ignore) {
                }
            }

            try {
                getThis().updateUI();
            } catch (NullPointerException ignore) {
            }

        }

        @Override
        public void mouseEntered(MouseEvent e) {

            if (!menu.getItem(0).isShowing()) {
                button.setEnabled(true);
            }

            if (e.getSource().equals(pagePanel) || e.getSource().equals(requestName) || e.getSource().equals(requestMethod) || e.getSource().equals(panel)) {

                if (!button.isShowing() && button.isEnabled()) {
                    panel.add(button, BorderLayout.CENTER);
                    button.add(menuBar ,BorderLayout.WEST);
                    for (Request request : sideBar.getRequests()) {
                        if (!request.getPanel().equals(panel)) {
                            request.getPanel().remove(request.getButton());
                            try {
                                request.getPageLabel().updateUI();
                            } catch (NullPointerException ignore) {
                            }
                            if (!request.isSelected) {
                                request.setBackground();
                            }
                        }
                    }
                }

                if (!menu.getItem(0).isShowing()) {
                    setHover();
                }

            }

            if (e.getSource().equals(sideBar.getPanels()[0]) || e.getSource().equals(sideBar.getPanels()[1])) {
                panel.remove(button);
                if (!isSelected) {
                    setBackground();
                    button.add(menuBar,BorderLayout.WEST);
                } else {
                    panel.add(menuBar,BorderLayout.WEST);
                }
            }

            if (e.getSource().equals(send)) {
                setSendHover();
                try {
                    send.updateUI();
                } catch (NullPointerException ignore) {
                }
            }

            try {
                pagePanel.updateUI();
                getThis().updateUI();
            } catch (NullPointerException ignore) {
            }

        }

        @Override
        public void mouseExited(MouseEvent e) {

            if (e.getSource().equals(pagePanel) || e.getSource().equals(requestName) || e.getSource().equals(requestMethod)) {
                panel.remove(button);
                if (!isSelected) {
                    setBackground();
                } else {
                    panel.add(menuBar,BorderLayout.WEST);
                }
                try {
                    pagePanel.updateUI();
                } catch (NullPointerException ignore) {
                }
            }

            if (e.getSource().equals(send)) {
                setSendBackground();
            }
            try {
                getThis().updateUI();
            } catch (NullPointerException ignore) {
            }
        }

        @Override
        public void ancestorMoved(HierarchyEvent e) {
            setLayout(false);
        }

        @Override
        public void ancestorResized(HierarchyEvent e) {
            setLayout(false);
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getSource().equals(comboBox)) {

                if (comboBox.getSelectedIndex() == 7) {

                    String[] option = new String[sideBar.getMethodTypes().size()];

                    for (String string : sideBar.getMethodTypes()) {
                        option[sideBar.getMethodTypes().indexOf(string)] = string;
                    }

                    JComboBox[] comboBoxes = new JComboBox[2];
                    JTextField textField = new JTextField();

                    comboBoxes[0] = new JComboBox<>(new String[]{"Add Method", "Select Method", "Remove Method"});
                    comboBoxes[0].setBounds(300, 0, 125, 35);

                    if (option.length == 0) {
                        comboBoxes[0].removeItemAt(2);
                        comboBoxes[0].removeItemAt(1);
                    }

                    comboBoxes[1] = new JComboBox<>(option);
                    comboBoxes[1].setBounds(425, 0, 125, 35);
                    comboBoxes[1].setEnabled(false);

                    textField.setBounds(0, 0, 300, 35);

                    JPanel panel = new JPanel();
                    panel.setPreferredSize(new Dimension(550, 35));
                    panel.setLayout(null);
                    panel.add(textField);
                    panel.add(comboBoxes[0]);
                    panel.add(comboBoxes[1]);

                    comboBoxes[0].addActionListener(event -> {

                        if (comboBoxes[0].getSelectedIndex() == 0) {
                            textField.setEnabled(true);
                            comboBoxes[1].setEnabled(false);
                        } else {
                            textField.setEnabled(false);
                            comboBoxes[1].setEnabled(true);
                        }

                        try {
                            panel.updateUI();
                        } catch (NullPointerException ignore) {
                        }
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

                        if (comboBoxes[0].getSelectedIndex() == 0) {

                            sideBar.getMethodTypes().add(sideBar.getMethodTypes().size() - 1, textField.getText());
                            requestMethod.setText(textField.getText());

                        } else if (comboBoxes[0].getSelectedIndex() == 1) {

                            requestMethod.setText(((String) comboBoxes[1].getSelectedItem()));

                        } else if (comboBoxes[0].getSelectedIndex() == 2) {

                            sideBar.getMethodTypes().remove(7 + comboBoxes[1].getSelectedIndex());
                            comboBoxes[1].removeItemAt(7 + comboBoxes[1].getSelectedIndex());

                        }

                    }


                } else {
                    requestMethod.setText(((String) comboBox.getSelectedItem()));
                }
                setMethodColor(requestMethod.getText());
            }

            else if (e.getSource().equals(comboBoxResponses)) {

                if (comboBoxResponses.getSelectedIndex() == 0) {

                    responses.clear();
                    comboBoxResponses.removeAllItems();
                    comboBoxResponses.addItem("Clear History");
                    comboBoxResponses.addItem("Delete Current Response");
                    setDefaultResponse();

                } else {

                    if (comboBoxResponses.getSelectedIndex() == 1) {

                        Iterator<Response> it = responses.iterator();
                        while (it.hasNext()) {

                            Response response = it.next();

                            comboBoxResponses.removeItemAt(responses.indexOf(response) + 2);

                            if (response.getTabbedPane().equals(getParts()[3].getComponent(0))) {

                                it.remove();

                            } else {
                                String date = response.getDateOfSendingRequest();
                                comboBoxResponses.insertItemAt((responses.indexOf(response) + 1) + ")" + date + " " + response.getMethod() + " " + response.getOriginalUrl(), 2);
                            }

                        }

                        if (comboBoxResponses.getItemCount() > 2) {
                            comboBoxResponses.setSelectedIndex(2);
                        }

                    }

                    getParts()[2].removeAll();
                    getParts()[3].removeAll();
                    if (comboBoxResponses.getItemCount() > 2) {
                        getParts()[3].setLayout(new BorderLayout(0, 0));
                        getParts()[2].add(responses.get(comboBoxResponses.getSelectedIndex() - 2).getPanel(), BorderLayout.CENTER);
                        getParts()[2].add(comboBoxResponses, BorderLayout.EAST);
                        getParts()[3].add(responses.get(comboBoxResponses.getSelectedIndex() - 2).getTabbedPane(), BorderLayout.CENTER);

                        URL.setText(responses.get(comboBoxResponses.getSelectedIndex() - 2).getOriginalUrl());

                        comboBox.setSelectedItem(responses.get(comboBoxResponses.getSelectedIndex() - 2).getMethod());

                        chooseBodyType.setSelectedIndex(0);

                        setDefaultPanels0(true);

                        for (JPanel panel : responses.get(comboBoxResponses.getSelectedIndex() - 2).getFormBody()) {

                            if (responses.get(comboBoxResponses.getSelectedIndex() - 2).getFormBody().indexOf(panel) != responses.get(comboBoxResponses.getSelectedIndex() - 2).getFormBody().size() - 1) {
                                addField(0);
                                ((JTextField) arrayLists[0].get(responses.get(comboBoxResponses.getSelectedIndex() - 2).getFormBody().indexOf(panel)).getComponent(0)).setText(((JTextField) panel.getComponent(0)).getText());
                                arrayLists[0].get(responses.get(comboBoxResponses.getSelectedIndex() - 2).getFormBody().indexOf(panel)).getComponent(0).setForeground(panel.getComponent(0).getForeground());
                                ((JTextField) arrayLists[0].get(responses.get(comboBoxResponses.getSelectedIndex() - 2).getFormBody().indexOf(panel)).getComponent(1)).setText(((JTextField) panel.getComponent(1)).getText());
                                arrayLists[0].get(responses.get(comboBoxResponses.getSelectedIndex() - 2).getFormBody().indexOf(panel)).getComponent(1).setForeground(panel.getComponent(1).getForeground());
                                ((JCheckBox) arrayLists[0].get(responses.get(comboBoxResponses.getSelectedIndex() - 2).getFormBody().indexOf(panel)).getComponent(3)).setSelected(((JCheckBox) panel.getComponent(3)).isSelected());
                            }

                        }

                        textArea.setText(responses.get(comboBoxResponses.getSelectedIndex() - 2).getJson());

                        binaryFileAddress = responses.get(comboBoxResponses.getSelectedIndex() - 2).getBinaryFileAddress();

                        chooseBodyType.setSelectedIndex(responses.get(comboBoxResponses.getSelectedIndex() - 2).getBodyType());

                        setDefaultPanels2();

                        for (JPanel panel : responses.get(comboBoxResponses.getSelectedIndex() - 2).getQuery()) {
                            if (responses.get(comboBoxResponses.getSelectedIndex() - 2).getQuery().indexOf(panel) != responses.get(comboBoxResponses.getSelectedIndex() - 2).getQuery().size() - 1) {
                                addField(1);
                                ((JTextField) arrayLists[1].get(responses.get(comboBoxResponses.getSelectedIndex() - 2).getQuery().indexOf(panel)).getComponent(0)).setText(((JTextField) panel.getComponent(0)).getText());
                                arrayLists[1].get(responses.get(comboBoxResponses.getSelectedIndex() - 2).getQuery().indexOf(panel)).getComponent(0).setForeground(panel.getComponent(0).getForeground());
                                ((JTextField) arrayLists[1].get(responses.get(comboBoxResponses.getSelectedIndex() - 2).getQuery().indexOf(panel)).getComponent(1)).setText(((JTextField) panel.getComponent(1)).getText());
                                arrayLists[1].get(responses.get(comboBoxResponses.getSelectedIndex() - 2).getQuery().indexOf(panel)).getComponent(1).setForeground(panel.getComponent(1).getForeground());
                                ((JCheckBox) arrayLists[1].get(responses.get(comboBoxResponses.getSelectedIndex() - 2).getQuery().indexOf(panel)).getComponent(3)).setSelected(((JCheckBox) panel.getComponent(3)).isSelected());
                            }

                        }

                        setDefaultPanels3();

                        for (JPanel panel : responses.get(comboBoxResponses.getSelectedIndex() - 2).getHeaderTab()) {
                            if (responses.get(comboBoxResponses.getSelectedIndex() - 2).getHeaderTab().indexOf(panel) != responses.get(comboBoxResponses.getSelectedIndex() - 2).getHeaderTab().size() - 1) {
                                addField(2);
                                ((JTextField) arrayLists[2].get(responses.get(comboBoxResponses.getSelectedIndex() - 2).getHeaderTab().indexOf(panel)).getComponent(0)).setText(((JTextField) panel.getComponent(0)).getText());
                                arrayLists[2].get(responses.get(comboBoxResponses.getSelectedIndex() - 2).getHeaderTab().indexOf(panel)).getComponent(0).setForeground(panel.getComponent(0).getForeground());
                                ((JTextField) arrayLists[2].get(responses.get(comboBoxResponses.getSelectedIndex() - 2).getHeaderTab().indexOf(panel)).getComponent(1)).setText(((JTextField) panel.getComponent(1)).getText());
                                arrayLists[2].get(responses.get(comboBoxResponses.getSelectedIndex() - 2).getHeaderTab().indexOf(panel)).getComponent(1).setForeground(panel.getComponent(1).getForeground());
                                ((JCheckBox) arrayLists[2].get(responses.get(comboBoxResponses.getSelectedIndex() - 2).getHeaderTab().indexOf(panel)).getComponent(3)).setSelected(((JCheckBox) panel.getComponent(3)).isSelected());
                            }

                        }

                        setQuery();

                    } else {
                        setDefaultResponse();
                    }

                }

            }

            else if (e.getSource().equals(chooseBodyType)) {

                if (chooseBodyType.getSelectedIndex() == 0) {
                    for (JPanel panel : arrayLists[2]) {
                        if (panel.getComponentCount() == 5) {
                            if (((JTextField) panel.getComponent(0)).getText().equals("Content-Type")) {
                                panel.getComponent(0).setForeground(theme.getTextFieldTheme()[0][2]);
                                panel.getComponent(1).setForeground(theme.getTextFieldTheme()[0][2]);
                                ((JTextField) panel.getComponent(1)).setText("multipart/form-data");
                                ((JCheckBox) panel.getComponent(3)).setSelected(true);
                                break;
                            }
                        } else if (arrayLists[2].indexOf(panel) == arrayLists[2].size() - 1) {
                            addField(2);
                            ((JTextField) arrayLists[2].get(arrayLists[2].size() - 2).getComponent(0)).setText("Content-Type");
                            arrayLists[2].get(arrayLists[2].size() - 2).getComponent(0).setForeground(theme.getTextFieldTheme()[0][2]);
                            ((JTextField) arrayLists[2].get(arrayLists[2].size() - 2).getComponent(1)).setText("multipart/form-data");
                            arrayLists[2].get(arrayLists[2].size() - 2).getComponent(1).setForeground(theme.getTextFieldTheme()[0][2]);
                            break;
                        }
                    }
                } else if (chooseBodyType.getSelectedIndex() == 1) {
                    for (JPanel panel : arrayLists[2]) {
                        if (panel.getComponentCount() == 5) {
                            if (((JTextField) panel.getComponent(0)).getText().equals("Content-Type")) {
                                panel.getComponent(0).setForeground(theme.getTextFieldTheme()[0][2]);
                                panel.getComponent(1).setForeground(theme.getTextFieldTheme()[0][2]);
                                ((JTextField) panel.getComponent(1)).setText("application/x-www-form-urlencoded");
                                ((JCheckBox) panel.getComponent(3)).setSelected(true);
                                break;
                            }
                        } else if (arrayLists[2].indexOf(panel) == arrayLists[2].size() - 1) {
                            addField(2);
                            ((JTextField) arrayLists[2].get(arrayLists[2].size() - 2).getComponent(0)).setText("Content-Type");
                            arrayLists[2].get(arrayLists[2].size() - 2).getComponent(0).setForeground(theme.getTextFieldTheme()[0][2]);
                            ((JTextField) arrayLists[2].get(arrayLists[2].size() - 2).getComponent(1)).setText("application/x-www-form-urlencoded");
                            arrayLists[2].get(arrayLists[2].size() - 2).getComponent(1).setForeground(theme.getTextFieldTheme()[0][2]);
                            break;
                        }
                    }
                } else if (chooseBodyType.getSelectedIndex() == 2) {
                    for (JPanel panel : arrayLists[2]) {
                        if (panel.getComponentCount() == 5) {
                            if (((JTextField) panel.getComponent(0)).getText().equals("Content-Type")) {
                                panel.getComponent(0).setForeground(theme.getTextFieldTheme()[0][2]);
                                panel.getComponent(1).setForeground(theme.getTextFieldTheme()[0][2]);
                                ((JTextField) panel.getComponent(1)).setText("application/json");
                                ((JCheckBox) panel.getComponent(3)).setSelected(true);
                                break;
                            }
                        } else if (arrayLists[2].indexOf(panel) == arrayLists[2].size() - 1) {
                            addField(2);
                            ((JTextField) arrayLists[2].get(arrayLists[2].size() - 2).getComponent(0)).setText("Content-Type");
                            arrayLists[2].get(arrayLists[2].size() - 2).getComponent(0).setForeground(theme.getTextFieldTheme()[0][2]);
                            ((JTextField) arrayLists[2].get(arrayLists[2].size() - 2).getComponent(1)).setText("application/json");
                            arrayLists[2].get(arrayLists[2].size() - 2).getComponent(1).setForeground(theme.getTextFieldTheme()[0][2]);
                            break;
                        }
                    }
                } else if (chooseBodyType.getSelectedIndex() == 3) {
                    for (JPanel panel : arrayLists[2]) {
                        if (panel.getComponentCount() == 5) {
                            if (((JTextField) panel.getComponent(0)).getText().equals("Content-Type")) {
                                panel.getComponent(0).setForeground(theme.getTextFieldTheme()[0][2]);
                                panel.getComponent(1).setForeground(theme.getTextFieldTheme()[0][2]);
                                ((JTextField) panel.getComponent(1)).setText("application/octet-stream");
                                ((JCheckBox) panel.getComponent(3)).setSelected(true);
                                break;
                            }
                        } else if (arrayLists[2].indexOf(panel) == arrayLists[2].size() - 1) {
                            addField(2);
                            ((JTextField) arrayLists[2].get(arrayLists[2].size() - 2).getComponent(0)).setText("Content-Type");
                            arrayLists[2].get(arrayLists[2].size() - 2).getComponent(0).setForeground(theme.getTextFieldTheme()[0][2]);
                            ((JTextField) arrayLists[2].get(arrayLists[2].size() - 2).getComponent(1)).setText("application/octet-stream");
                            arrayLists[2].get(arrayLists[2].size() - 2).getComponent(1).setForeground(theme.getTextFieldTheme()[0][2]);
                            break;
                        }
                    }
                } else {
                    for (JPanel panel : arrayLists[2]) {
                        if (panel.getComponentCount() == 5) {
                            if (((JTextField) panel.getComponent(0)).getText().equals("Content-Type")) {
                                panels[3].remove(panel);
                                arrayLists[2].remove(panel);
                                break;
                            }
                        }
                    }
                }
                setDefaultPanels0(false);
            }

            else if(e.getSource().equals(menu.getItem(0))){

                if (getThis().isSelected) {
                    sideBar.setUnripePage(new UnripePage(theme, getThis().getSize()));
                    sideBar.getUnripePage().getNewRequest().addActionListener(sideBar.getHandler());
                    sideBar.getUnripePage().getImportFromFile().addActionListener(sideBar.getHandler());
                }
                sideBar.getRequests().remove(getThis());
                sideBar.getPanels()[1].remove(pagePanel);
                sideBar.setPageLabel();
                sideBar.getUnripePage().setLayout(false);

            }

            else if (e.getSource().equals(menu.getItem(1))) {

                savePrimaryData();
                String string = binaryFileAddress;

                int tempBodyType = chooseBodyType.getSelectedIndex();
                methodType = (String) comboBox.getSelectedItem();
                url = URL.getText();

                JFrame parentFrame = new JFrame();

                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Save Request");

                try {
                    if (fileChooser.showSaveDialog(parentFrame) == JFileChooser.APPROVE_OPTION) {
                        File fileToSave = fileChooser.getSelectedFile();
                        String fileAddress = fileToSave.getAbsolutePath();
                        if (!fileAddress.endsWith(".obj")) {
                            fileAddress = fileAddress + ".obj";
                        }
                        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(new File(fileAddress)))) {

                            out.writeObject(getThis());

                        } catch (IOException ignored) {
                        }

                        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(new File(fileAddress)))) {

                            comboBoxResponses.setSelectedIndex(0);

                            Request request = ((Request) in.readObject());

                            ArrayList<Response> loadResponses = request.getResponses();

                            for (Response response : loadResponses) {

                                sideBar.loadData(getThis(), response.getSaveFormBody(), response.getSaveQuery(), response.getSaveHeader(), response.getBinaryFileAddress(), response.getJson(), response.getBodyType());

                                responses.add(new Response(theme, response.getMethod(), response.getBodyType(), response.getUrl(), response.getOriginalUrl(), arrayLists[2], arrayLists[1], arrayLists[0], response.getBinaryFileAddress(), response.getJson()));

                                responses.get(responses.size() - 1).setLabels(response.getResponseCode(), response.getResponseStatus(), response.getSaveValue()[0], response.getSaveValue()[1]);

                                responses.get(responses.size() - 1).setSize(response.getSize());

                                String date = response.getDateOfSendingRequest();
                                comboBoxResponses.addItem((loadResponses.size() - loadResponses.indexOf(response)) + ")" + date + " " + response.getMethod() + " " + response.getOriginalUrl());

                                for (String key : response.getSaveCookie().keySet()) {

                                    responses.get(responses.size() - 1).getCookie().add(new JLabel(key));
                                    ((JScrollPane) responses.get(responses.size() - 1).getTabPanel()[2].getComponent(0)).add(responses.get(responses.size() - 1).getCookie().get(responses.get(responses.size() - 1).getCookie().size() - 1));

                                    responses.get(responses.size() - 1).getCookie().add(new JLabel(response.getSaveCookie().get(key)));
                                    ((JScrollPane) responses.get(responses.size() - 1).getTabPanel()[2].getComponent(0)).add(responses.get(responses.size() - 1).getCookie().get(responses.get(responses.size() - 1).getCookie().size() - 1));

                                }

                                for (String key : response.getSaveHeaderResponse().keySet()) {

                                    responses.get(responses.size() - 1).getHeader().add(new JLabel(key));
                                    ((JScrollPane) responses.get(responses.size() - 1).getTabPanel()[1].getComponent(0)).add(responses.get(responses.size() - 1).getHeader().get(responses.get(responses.size() - 1).getHeader().size() - 1));

                                    responses.get(responses.size() - 1).getHeader().add(new JLabel(response.getSaveHeaderResponse().get(key)));
                                    ((JScrollPane) responses.get(responses.size() - 1).getTabPanel()[1].getComponent(0)).add(responses.get(responses.size() - 1).getHeader().get(responses.get(responses.size() - 1).getHeader().size() - 1));

                                }

                                responses.get(responses.size() - 1).setDateOfSendingRequest(response.getDateOfSendingRequest());
                                responses.get(responses.size() - 1).setSaveValue(response.getSaveValue());
                                responses.get(responses.size() - 1).setTextArea1Text(response.getSaveResponseBody());
                                responses.get(responses.size() - 1).setSaveResponseBody(response.getSaveResponseBody());
                                responses.get(responses.size() - 1).setSaveCookie(response.getSaveCookie());
                                responses.get(responses.size() - 1).setSaveHeaderResponse(response.getSaveHeaderResponse());
                                responses.get(responses.size() - 1).setSize(response.getSize());

                                responses.get(responses.size() - 1).setSaveFormBody(response.getSaveFormBody());
                                responses.get(responses.size() - 1).setSaveQuery(response.getSaveQuery());
                                responses.get(responses.size() - 1).setSaveHeader(response.getSaveHeader());

                            }

                            if (comboBoxResponses.getItemCount() > 2) {
                                comboBoxResponses.setSelectedIndex(2);
                            }

                            URL.setText(url);

                            sideBar.loadData(getThis(), saveFormBody, saveQuery, saveHeader, string, json, tempBodyType);

                            setLayout(true);

                            setQuery();

                            comboBox.setSelectedItem(methodType);

                        } catch (IOException | ClassNotFoundException e2) {
                            e2.printStackTrace();
                        }

                    }

                } catch (ArrayIndexOutOfBoundsException e4) {
                    e4.printStackTrace();
                }
            }

            else if (e.getSource().equals(menu.getItem(2))) {

                JFrame parentFrame = new JFrame();

                if(comboBoxResponses.getItemCount() > 2) {
                    JComboBox<String> comboBox = new JComboBox<>();

                    for (int i = 2; i < comboBoxResponses.getItemCount(); i++) {
                        comboBox.addItem(comboBoxResponses.getItemAt(i));
                    }

                    comboBox.setSelectedItem(comboBoxResponses.getSelectedItem());

                   int i = JOptionPane.showOptionDialog(
                            parentFrame,
                            comboBox,
                            "Select Response Body",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            new String[]{"OK"},
                            "OK");

                    if (i == 0) {

                        int index = comboBox.getSelectedIndex();
                        JFileChooser fileChooser = new JFileChooser();
                        fileChooser.setDialogTitle("Save Response Body");

                        try {
                            if (fileChooser.showSaveDialog(parentFrame) == JFileChooser.APPROVE_OPTION) {
                                File fileToSave = fileChooser.getSelectedFile();
                                Thread thread = new Thread(() -> responses.get(index).outPut(new StringBuilder(fileToSave.getAbsolutePath())));
                                thread.start();
                            }
                        } catch (ArrayIndexOutOfBoundsException ignore) {
                        }

                    }

                } else {

                    JLabel label = new JLabel("there is no response body to save.");

                    JOptionPane.showOptionDialog(
                            parentFrame,
                            label,
                            "Error",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.ERROR_MESSAGE,
                            null,
                            new String[]{"OK"},
                            "OK");

                }

            }

            else if (e.getSource().equals(send)) {
                sendRequest();
            }

            setLayout(false);
        }

        @Override
        public void focusGained(FocusEvent e) {

            for (int i = 0; i < 3; i++) {
                for (JPanel panel : arrayLists[i]) {
                    if (panel.getComponentCount() == 5) {
                        for (int j = 0; j < 2; j++) {
                            if (e.getSource().equals(panel.getComponent(j))
                                    && panel.getComponent(j).getForeground().equals(theme.getTextFieldTheme()[0][1])) {

                                    ((JTextField) panel.getComponent(j)).setText("");

                                if(((JCheckBox) panel.getComponent(3)).isSelected()) {
                                    panel.getComponent(j).setForeground(theme.getTextFieldTheme()[0][2]);
                                } else {
                                    panel.getComponent(j).setForeground(theme.getTextFieldTheme()[1][2]);
                                }

                            }
                        }
                    }
                }

            }

        }

        @Override
        public void focusLost(FocusEvent e) {

            for (int i = 0; i < 3; i++) {
                for (JPanel panel : arrayLists[i]) {
                    if (panel.getComponentCount() == 5) {
                        for (int j = 0; j < 2; j++) {
                            if (e.getSource().equals(panel.getComponent(j))
                                    && ((JTextField) panel.getComponent(j)).getText().equals("")) {

                                if(j == 0) {
                                    ((JTextField) panel.getComponent(j)).setText("name");
                                } else {
                                    ((JTextField) panel.getComponent(j)).setText("value");
                                }

                                panel.getComponent(j).setForeground(theme.getTextFieldTheme()[0][1]);


                            }
                        }
                    }
                }

            }

        }

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {

            for (JPanel jPanel : arrayLists[1]) {
                if (e.getSource().equals(URL) || e.getSource().equals(jPanel.getComponent(0)) || e.getSource().equals(jPanel.getComponent(1))) {
                    setQuery();
                }
            }
        }

        @Override
        public void stateChanged(ChangeEvent e) {
            setLayout(false);
        }
    }

}
