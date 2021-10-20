import com.sun.java.swing.plaf.windows.WindowsScrollBarUI;
import com.sun.java.swing.plaf.windows.WindowsScrollPaneUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

/**
 * this class creat each request work space
 */
public class Request extends UnripePage {

    private Handler handler;

    private SideBar sideBar;
    private String methodType;
    private JLabel requestMethod;
    private JTextField requestName;
    private JPanel pagePanel;
    private JPanel panel;
    private JButton button;
    private JMenuBar menuBar;
    private JMenu menu;
    private boolean isSelected;

    private JComboBox<? extends String> comboBox;
    private JTextField URL;
    private JButton send;

    private JTabbedPane tabbedPane;
    private JScrollPane[] scrollPanes;
    private JPanel[] panels;
    private ArrayList<JPanel>[] arrayLists;

    private ArrayList<Response> responses;
    private JComboBox<String> comboBoxResponses;

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

        comboBox = new JComboBox<>(new String[]{"GET","POST","PUT","PATCH","DELETE","OPTIONS","HEAD","Custom Method"});
        URL = new JTextField();
        send = new JButton("Send");

        tabbedPane = new JTabbedPane();
        scrollPanes = new JScrollPane[5];
        arrayLists = new ArrayList[2];
        panels = new JPanel[5];

        responses = new ArrayList<>();
        comboBoxResponses = new JComboBox<>(new String[]{"Delete Current Response","Clear History"});

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

        button.setFocusable(false);
        button.setBounds(0,3,35,29);
        button.setBackground(theme.getButtonTheme()[0][0]);
        button.setLayout(new BorderLayout(0, 0));
        button.addMouseListener(handler);
        button.add(menuBar, BorderLayout.WEST);
        button.setIcon(new ImageIcon(theme.getAddressIcons()+"icons8_sort_down_10px.png"));

        menuBar.setPreferredSize(new Dimension(0, 0));
        menuBar.add(menu);

        menu.add(new JMenuItem("Delete"));
        menu.getItem(0).addMouseListener(handler);
        menu.getItem(0).setIcon(new ImageIcon(theme.getAddressIcons()+"icons8_trash_can_15px.png"));
        menu.setMenuLocation(-18, -10 + panel.getPreferredSize().height);

        setOtherButtonDeSelect();

        comboBox.setPreferredSize(new Dimension(100, 50));
        comboBox.setBorder(new EmptyBorder(0, 0, 0, 0));
        comboBox.setBackground(theme.getComboBoxTheme()[0]);
        comboBox.setForeground(theme.getComboBoxTheme()[1]);
        comboBox.setSelectedItem(methodType);
        comboBox.addActionListener(handler);

        URL.setBackground(theme.getTextFieldTheme()[2][0]);
        URL.setForeground(theme.getTextFieldTheme()[2][1]);
        URL.setFont(new Font("Normal", Font.PLAIN, URL.getFont().getSize() + 4));
        URL.setBorder(new EmptyBorder(0, 0, 0, 0));

        send.setPreferredSize(new Dimension(60, 50));
        send.setUI(new BasicButtonUI());
        setSendBackground();
        send.addMouseListener(handler);
        send.updateUI();

        tabbedPane.addTab("Body", scrollPanes[0]);
        tabbedPane.addTab("Auth", scrollPanes[1]);
        tabbedPane.addTab("Query", scrollPanes[2]);
        tabbedPane.addTab("Header", scrollPanes[3]);
        tabbedPane.addTab("Docs", scrollPanes[4]);

        setDefaultPanels0();

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
        comboBoxResponses.setBackground(theme.getComboBoxTheme()[0]);
        comboBoxResponses.setBorder(new EmptyBorder(0, 0, 0, 0));
        comboBoxResponses.addActionListener(handler);

        sideBar.getPanels()[0].addMouseListener(handler);
        sideBar.getPanels()[1].addMouseListener(handler);

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
                if(methodType.length() >= 4) {
                    requestMethod.setText(methodType.substring(0, 4));
                } else {
                    requestMethod.setText(methodType);
                }
                break;
        }


    }

    /**
     *
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

    public JPanel getPanel(){
        return panel;
    }

    /**
     * set the tab 1 default form
     */
    private void setDefaultPanels0() {

        JPanel panel = new JPanel();
        JLabel label = new JLabel("Select a body type from above");
        JLabel label1 = new JLabel(new ImageIcon(theme.getAddressIcons()+"icons8_hand_peace_160px.png"));

        setTabbedPane(panel, label, label1);

        panels[0].removeAll();
        panels[0].add(panel);

    }

    /**
     * set the tab 2 default form
     */
    private void setDefaultPanels1() {

        JPanel panel = new JPanel();
        JLabel label = new JLabel("Select an auth type from above");
        JLabel label1 = new JLabel(new ImageIcon(theme.getAddressIcons()+"icons8_padlock_160px.png"));

        setTabbedPane(panel, label, label1);

        panels[1].removeAll();
        panels[1].add(panel);

    }

    /**
     * since tab 1 and 2 is use the same way to creat so they use this method
     */
    private void setTabbedPane(JPanel panel, JLabel label, JLabel label1) {

        panel.setLayout(null);
        panel.setBounds(getHold()[0].getWidth() / 2 - 100, getHold()[0].getHeight() / 2 - 125, 200, 200);
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
    private void setDefaultPanels2() {

        JLabel label = new JLabel("URL : "+URL.getText());

        label.setBackground(theme.getLabelTheme()[1][0]);
        label.setForeground(theme.getLabelTheme()[1][1]);
        label.setOpaque(true);
        label.setFont(new Font("Normal", Font.PLAIN, label.getFont().getSize() + 4));
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setBorder(BorderFactory.createLineBorder(theme.getLabelTheme()[1][3], 2));

        arrayLists[0].clear();

        panels[2].add(label);

        addField(0);

    }

    /**
     * set the tab 3 default form
     */
    private void setDefaultPanels3() {

        arrayLists[1].clear();

        addField(1);

    }

    private void addField(int index) {

        int i = 1;

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(theme.getBackGroundTheme()[2]);

        if(arrayLists[0].size() == 0 || arrayLists[1].size() == 0){
            panel.add(new JButton(new ImageIcon(theme.getAddressIcons() + "icons8_settings_15px.png")));
            ((JButton) panel.getComponent(0)).setHorizontalAlignment(SwingConstants.CENTER);
            panel.getComponent(0).addMouseListener(handler);
            panel.getComponent(0).setBackground(theme.getBackGroundTheme()[2]);
            arrayLists[index].add(panel);
            panels[index + 2].add(panel);
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
            panels[index + 2].add(panel, panels[index + 2].getComponentCount() - 1);
        }

        panel.add(new JTextField() ,1 - i);
        panel.getComponent(1 - i).addMouseListener(handler);
        panel.getComponent(1 - i).setBackground(theme.getTextFieldTheme()[0][0]);
        panel.getComponent(1 - i).setForeground(theme.getTextFieldTheme()[0][1]);

        panel.add(new JTextField() , 2 - i);
        panel.getComponent(2 - i).addMouseListener(handler);
        panel.getComponent(2 - i).setBackground(theme.getTextFieldTheme()[0][0]);
        panel.getComponent(2 - i).setForeground(theme.getTextFieldTheme()[0][1]);

        if(i == 0) {

            panel.getComponent(1).setFocusable(false);
            ((JTextField) panel.getComponent(1)).setText("New name");

            panel.getComponent(2).setFocusable(false);
            ((JTextField) panel.getComponent(2)).setText("New value");

        } else {

            panel.getComponent(0).addFocusListener(handler);
            ((JTextField) panel.getComponent(0)).setText("name");

            panel.getComponent(1).addFocusListener(handler);
            ((JTextField) panel.getComponent(1)).setText("value");
        }

        setLayout();

    }

    /**
     * set the tab 5 default form
     */
    private void setDefaultPanels4() {

        JPanel panel = new JPanel();
        JButton button = new JButton("Add Description");
        JLabel label1 = new JLabel(new ImageIcon(theme.getAddressIcons()+"icons8_document_160px.png"));

        panel.setLayout(null);
        panel.setBounds(getHold()[0].getWidth() / 2 - 100, getHold()[0].getHeight() / 2 - 125, 200, 200);
        panel.setBackground(theme.getBackGroundTheme()[2]);
        panel.add(label1);
        panel.add(button);

        label1.setBounds(0, 0, 200, 160);

        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setBounds(0, 160, 200, 40);
        button.setFont(new Font("Normal", Font.BOLD, button.getFont().getSize()));
        button.setBackground(color1);
        button.setForeground(color2);

        label1.setHorizontalAlignment(SwingConstants.CENTER);

        panels[4].add(panel);

    }

    /**
     * set the default page for response
     */
    private void setDefaultResponse() {

        getParts()[2].removeAll();
        getParts()[3].removeAll();
        getParts()[3].setLayout(null);

        JLabel[] labels = new JLabel[8];
        JPanel panel = new JPanel();

        panel.setLayout(new GridLayout(4,2,10,10));
        panel.setBackground(theme.getBackGroundTheme()[2]);

        labels[0] = new JLabel("Send Request");

        labels[1] = new JLabel("Ctrl+Enter");

        labels[2] = new JLabel("Focus Url Bar");

        labels[3] = new JLabel("Ctrl+L");

        labels[4] = new JLabel("Manage Cookies");

        labels[5] = new JLabel("Ctrl+K");

        labels[6] = new JLabel("Edit Environments");

        labels[7] = new JLabel("Ctrl+E");

        for (int i = 0; i < 4; i++) {

            labels[2 * i + 1].setBackground(theme.getLabelTheme()[1][0]);
            labels[2 * i + 1].setForeground(theme.getLabelTheme()[1][1]);
            labels[2 * i + 1].setBorder(BorderFactory.createLineBorder(theme.getLabelTheme()[1][3]));
            labels[2*i+1].setHorizontalAlignment(SwingConstants.CENTER);

            labels[2 * i].setBackground(theme.getBackGroundTheme()[2]);
            labels[2 * i].setForeground(theme.getLabelTheme()[1][1]);

        }

        for (int i = 0; i < 8; i++) {
            panel.add(labels[i]);
            labels[i].setOpaque(true);
        }

        getParts()[3].add(panel);

        setLayout();

    }

    /**
     * when we select a new request from list of request the other request panels must be deselected
     */
    private void setOtherButtonDeSelect() {
        for (Request request : sideBar.getRequests()) {
            if (!request.equals(this)) {
                request.setSelected(false);
                request.getButton().setEnabled(true);
                request.getPageLabel().remove(request.getButton());
                request.setBackground();
            }
        }
    }

    /**
     * set every components that not use Layout Manager
     */
    @Override
    protected void setLayout() {

        try {
            super.setLayout();
        } catch (NullPointerException ignored){}

        if(responses.size() == 0 && getParts()[3].getComponentCount() != 0) {
            getParts()[3].getComponent(0).setBounds(getHold()[1].getWidth()/2 - 155, getParts()[3].getHeight() / 2 - 90, 310, 180);
        }

        for (int index = 0; index < 2; index++) {

            for (JPanel jPanel : arrayLists[index]) {
                jPanel.setBounds(0, 45 - 40 * index + 40 * arrayLists[index].indexOf(jPanel), getHold()[0].getWidth(), 35);
            }

            if( 5 + 40 * panels[index + 2].getComponentCount() > this.getHeight() - 70){
                for (JPanel jPanel : arrayLists[index]) {
                    if(arrayLists[index].indexOf(jPanel) < arrayLists[index].size() - 1) {

                        jPanel.getComponent(0).setBounds(38, 0, 100 + (getHold()[0].getWidth() - 375)/2, 35);
                        jPanel.getComponent(1).setBounds(141 + (getHold()[0].getWidth() - 375)/2, 0, 100 + (getHold()[1].getWidth() - 375)/2, 35);
                        jPanel.getComponent(2).setBounds(244 + (getHold()[0].getWidth() - 375), 5, 33, 25);
                        jPanel.getComponent(3).setBounds(277 + (getHold()[0].getWidth() - 375), 5, 30, 25);
                        jPanel.getComponent(4).setBounds(307 + (getHold()[0].getWidth() - 375), 5, 33, 25);

                    } else {

                        jPanel.getComponent(0).setBounds(3, 5, 33, 25);
                        jPanel.getComponent(1).setBounds(38, 0, 100 + (getHold()[0].getWidth() - 375)/2, 35);
                        jPanel.getComponent(2).setBounds(141 + (getHold()[1].getWidth() - 375)/2, 0, 100 + (getHold()[1].getWidth() - 375)/2, 35);

                    }
                }

                if (index == 0) {
                    panels[2].getComponent(0).setBounds(5,5,getHold()[0].getWidth() - 27,35);
                }

                panels[index + 2].setPreferredSize(new Dimension(getHold()[0].getWidth() - 20,5 + 40 * panels[index + 2].getComponentCount()));

            } else {
                for (JPanel jPanel : arrayLists[index]) {
                    if(arrayLists[index].indexOf(jPanel) < arrayLists[index].size() - 1) {

                        jPanel.getComponent(0).setBounds(40, 0, 100 + (getHold()[0].getWidth() - 375)/2, 35);
                        jPanel.getComponent(1).setBounds(145 + (getHold()[0].getWidth() - 375)/2, 0, 100 + (getHold()[1].getWidth() - 375)/2, 35);
                        jPanel.getComponent(2).setBounds(250 + (getHold()[0].getWidth() - 375), 5, 33, 25);
                        jPanel.getComponent(3).setBounds(285 + (getHold()[0].getWidth() - 375), 5, 30, 25);
                        jPanel.getComponent(4).setBounds(317 + (getHold()[0].getWidth() - 375), 5, 33, 25);

                    } else {

                        jPanel.getComponent(0).setBounds(5, 5, 33, 25);
                        jPanel.getComponent(1).setBounds(40, 0, 100 + (getHold()[0].getWidth() - 375)/2, 35);
                        jPanel.getComponent(2).setBounds(145 + (getHold()[0].getWidth() - 375)/2, 0, 100 + (getHold()[1].getWidth() - 375)/2, 35);

                    }
                }

                if (index == 0) {
                    panels[2].getComponent(0).setBounds(5,5,getHold()[0].getWidth() - 12,35);
                }

                panels[index + 2].setPreferredSize(new Dimension(getHold()[0].getWidth() - 2,5 + 40 * panels[index + 2].getComponentCount()));

            }

            panels[index + 2].updateUI();

        }

    }

    /**
     * set page panel of request lighter when the pointer is on page panel
     */
    private void setHover() {
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

    private void setSendHover() {
        send.setBackground(theme.getButtonTheme()[1][2]);
        send.setForeground(theme.getButtonTheme()[1][1]);
    }

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
     * an inner class which control events
     */
    private class Handler implements MouseListener, HierarchyBoundsListener, ActionListener , FocusListener {

        @Override
        public void mouseClicked(MouseEvent e) {

            if (e.getSource().equals(button) && button.isEnabled()) {
                menu.doClick();
                button.setEnabled(false);
                panel.remove(button);
                if (isSelected) {
                    setHover();
                } else {
                    setBackground();
                }
            } else if (e.getSource().equals(send)) {

                responses.add(0 ,new Response(theme));

                Date date = responses.get(0).getDateOfSendingResponse();
                comboBoxResponses.insertItemAt(responses.size()+")"+date.getYear()+"/"+date.getMonth()+"/"+date.getDay()+" "+date.getHours()+":"+date.getMinutes() ,2);
                comboBoxResponses.setSelectedIndex(2);

                getParts()[3].removeAll();
                getParts()[3].setLayout(new BorderLayout(0,0));
                getParts()[2].add(responses.get(0).getPanel(), BorderLayout.CENTER);
                getParts()[2].add(comboBoxResponses, BorderLayout.EAST);
                getParts()[3].add(responses.get(0).getTabbedPane(), BorderLayout.CENTER);

            }

            for (int i = 0; i < 2; i++) {
                if (e.getSource().equals(arrayLists[i].get(arrayLists[i].size() - 1).getComponent(1))
                        || e.getSource().equals(arrayLists[i].get(arrayLists[i].size() - 1).getComponent(2))) {
                    addField(i);
                }
            }

            getThis().updateUI();

        }

        @Override
        public void mousePressed(MouseEvent e) {

            if (e.getSource().equals(menu.getItem(0))) {

                sideBar.setUnripePage(new UnripePage(theme,getThis().getSize()));
                sideBar.getUnripePage().getNewRequest().addActionListener(sideBar.getHandler());
                sideBar.getRequests().remove(getThis());
                sideBar.getPanels()[1].remove(pagePanel);
                sideBar.setPageLabel();
                sideBar.getUnripePage().setLayout();

            } else if ((e.getSource().equals(pagePanel) || e.getSource().equals(requestMethod) || e.getSource().equals(requestName) || e.getSource().equals(panel)) && button.isEnabled()) {
                sideBar.setUnripePage(getThis());
                setHover();
                isSelected = true;
                setOtherButtonDeSelect();
            }

            for (int i = 0; i < 2; i++) {
                for (JPanel panel : arrayLists[i]) {
                    if (panel.getComponentCount() == 5) {
                        if (e.getSource().equals(panel.getComponent(4))) {
                            arrayLists[i].remove(panel);
                            panels[i + 2].remove(panel);
                            setLayout();
                            break;
                        } else if (e.getSource().equals(panel.getComponent(3))) {
                            for (int j = 0; j < 2; j++) {
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
                            if(!panel.getComponent(1).getForeground().equals(theme.getTextFieldTheme()[0][1])) {
                                textArea.setText(((JTextField) panel.getComponent(1)).getText());
                            }

                            textArea.setBackground(theme.getTextFieldTheme()[0][0]);

                            if(((JCheckBox) panel.getComponent(3)).isSelected()) {
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
                                if(!textArea.getText().equals("")) {
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
                send.updateUI();
            }

            getThis().updateUI();

        }

        @Override
        public void mouseReleased(MouseEvent e) {

            if (e.getSource().equals(panel)) {
                panel.add(button, BorderLayout.CENTER);
                setHover();
            }

            if (e.getSource().equals(send)) {
                setSendHover();
                send.updateUI();
            }

            getThis().updateUI();

        }

        @Override
        public void mouseEntered(MouseEvent e) {

            if (!menu.getItem(0).isShowing()) {
                button.setEnabled(true);
            }

            if (e.getSource().equals(pagePanel) || e.getSource().equals(requestName) || e.getSource().equals(requestMethod) || e.getSource().equals(panel)) {

                if (!button.isShowing() && button.isEnabled()) {
                    panel.add(button, BorderLayout.CENTER);
                    for (Request request : sideBar.getRequests()) {
                        if (!request.getPanel().equals(panel)) {
                            request.getPanel().remove(request.getButton());
                            request.getPageLabel().updateUI();
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

            if(e.getSource().equals(sideBar.getPanels()[0]) || e.getSource().equals(sideBar.getPanels()[1])) {
                panel.remove(button);
                if (!isSelected) {
                    setBackground();
                }
            }

            if (e.getSource().equals(send)) {
                setSendHover();
                send.updateUI();
            }

            pagePanel.updateUI();
            getThis().updateUI();
        }

        @Override
        public void mouseExited(MouseEvent e) {

            if (e.getSource().equals(pagePanel) || e.getSource().equals(requestName) || e.getSource().equals(requestMethod)) {
                panel.remove(button);
                if (!isSelected) {
                    setBackground();
                }
                pagePanel.updateUI();
            }

            if (e.getSource().equals(send)) {
                setSendBackground();
            }

            getThis().updateUI();
        }

        @Override
        public void ancestorMoved(HierarchyEvent e) {
            setLayout();
        }

        @Override
        public void ancestorResized(HierarchyEvent e) {
            setLayout();
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

                    comboBoxes[0] = new JComboBox<>(new String[]{"Add Method" ,"Select Method" ,"Remove Method"});
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

                    if(result == 0) {

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
            } else if (e.getSource().equals(comboBoxResponses)){

                if (comboBoxResponses.getSelectedIndex() == 0) {

                    responses.clear();
                    comboBoxResponses.removeAllItems();
                    comboBoxResponses.addItem("Delete Current Response");
                    comboBoxResponses.addItem("Clear History");
                    setDefaultResponse();

                } else if (comboBoxResponses.getSelectedIndex() == 1) {

                    Iterator<Response> it = responses.iterator();
                    while (it.hasNext()) {

                        Response response = it.next();

                        comboBoxResponses.removeItemAt(responses.indexOf(response) + 2);

                        if(response.getTabbedPane().equals(getParts()[3].getComponent(0))){

                            it.remove();

                            getParts()[3].removeAll();
                            getParts()[3].setLayout(new BorderLayout(0, 0));

                            try {
                                getParts()[2].add(responses.get(0).getPanel(), BorderLayout.CENTER);
                                getParts()[2].add(comboBoxResponses, BorderLayout.EAST);
                                getParts()[3].add(responses.get(0).getTabbedPane(), BorderLayout.CENTER);
                                comboBoxResponses.setSelectedIndex(2);
                            } catch (IndexOutOfBoundsException ex){
                                setDefaultResponse();
                            }
                        } else {

                            Date date = response.getDateOfSendingResponse();
                            comboBoxResponses.insertItemAt(responses.indexOf(response) + 1 + ")" + date.getYear() + "/" + date.getMonth() + "/" + date.getDay() + " " + date.getHours() + ":" + date.getMinutes(), 2);
                        }
                    }


                } else {

                    getParts()[3].removeAll();
                    getParts()[3].setLayout(new BorderLayout(0, 0));
                    getParts()[2].add(responses.get(comboBoxResponses.getSelectedIndex() - 2).getPanel(), BorderLayout.CENTER);
                    getParts()[2].add(comboBoxResponses, BorderLayout.EAST);
                    getParts()[3].add(responses.get(comboBoxResponses.getSelectedIndex() - 2).getTabbedPane(), BorderLayout.CENTER);

                }
            }
            getThis().updateUI();
        }

        @Override
        public void focusGained(FocusEvent e) {

            for (int i = 0; i < 2; i++) {
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

            for (int i = 0; i < 2; i++) {
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
    }

}
