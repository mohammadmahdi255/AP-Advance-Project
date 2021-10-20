

import com.sun.java.swing.plaf.windows.WindowsScrollBarUI;
import com.sun.java.swing.plaf.windows.WindowsScrollPaneUI;

import javax.swing.*;
import javax.swing.plaf.basic.BasicMenuBarUI;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
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
     * @param theme the color theme
     * @param sideBarSize the size side bar should have
     * @param unripePageSize the size of unripePageSize
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
        unripePage.getImportFromFile().addActionListener(handler);

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
        menus[0].add(new JMenuItem("Create Workspace"));
        menus[0].add(new JMenuItem("Switch Requests"));
        menus[0].getItem(1).setAccelerator(KeyStroke.getKeyStroke("ctrl P"));


        menus[1].setMenuLocation(-18, -7 + buttons[0].getHeight());
        menus[1].add(new JMenuItem("No Environment"));
        menus[1].add(new JMenuItem("Manage Environments"));

        menus[2].setMenuLocation(-18, -7 + buttons[2].getHeight());
        menus[2].add(new JMenuItem("New Request"));
        menus[2].add(new JMenuItem("Load Request"));
        menus[2].getItem(0).setIcon(new ImageIcon(theme.getAddressIcons()+"icons8_joyent_15px.png"));
        menus[2].getItem(0).setAccelerator(KeyStroke.getKeyStroke("ctrl N"));
        menus[2].getItem(1).setIcon(new ImageIcon(theme.getAddressIcons()+"icons8_doctors_folder_15px.png"));
        menus[2].getItem(1).setAccelerator(KeyStroke.getKeyStroke("shift ctrl N"));

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

    /**
     *
     * @return all of method Type
     */
    public ArrayList<String> getMethodTypes() {
        return methodTypes;
    }

    /**
     *
     * @return the filter JTextField
     */
    public JTextField getFilter() {
        return filter;
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

        if (unripePage instanceof Request) {
            unripePage.setLayout(true);
        }

    }

    /**
     * set the request label in the request list
     */
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

        try {
            updateUI();
        } catch (NullPointerException ignore){}
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

        for (Request request : requests) {
            for (Response response : request.getResponses()) {
                response.setTheme();
                response.setLayout();
            }
            if (request.getComboBoxResponses().getItemCount() == 2) {
                request.setDefaultResponse();
            }
            request.setTheme(true);
        }

        menuBars[0].setBackground(theme.getInsomniaTheme()[0]);
        menuBars[0].setBorder(BorderFactory.createMatteBorder(1,0,1,0,theme.getInsomniaTheme()[3]));

        menus[0].setForeground(theme.getInsomniaTheme()[1]);

        menus[0].setIcon(new ImageIcon(theme.getAddressIcons()+"icons8_list_30px.png"));

        buttons[2].setIcon(new ImageIcon(theme.getAddressIcons()+"icons8_sort_down_&&_plus_25px.png"));

        menus[2].getItem(0).setIcon(new ImageIcon(theme.getAddressIcons()+"icons8_joyent_15px.png"));
        menus[2].getItem(1).setIcon(new ImageIcon(theme.getAddressIcons()+"icons8_doctors_folder_15px.png"));

        filter.setBackground(theme.getTextFieldTheme()[0][0]);
        filter.setForeground(theme.getTextFieldTheme()[0][1]);

    }

    /**
     * load the request data from save file
     * @param request the request data will be save
     * @param arrayList0 the form urlencoded and form data array list
     * @param arrayList1 the query array list
     * @param arrayList2 the header array list
     * @param binaryFileAddress binary file address data
     * @param json json command data
     * @param bodyType the body type of request
     */
    public void loadData (Request request,ArrayList<String[]> arrayList0 ,ArrayList<String[]> arrayList1 ,ArrayList<String[]> arrayList2 ,String binaryFileAddress ,String json , int bodyType) {

        request.getChooseBodyType().setSelectedIndex(0);

        request.setDefaultPanels0(true);

        assert arrayList0 != null;
        for (String[] strings : arrayList0) {
                request.addField(0);
                    ((JTextField) request.getArrayLists()[0].get(arrayList0.indexOf(strings)).getComponent(0)).setText(strings[0]);
                    if (Boolean.parseBoolean(strings[4])) {
                        request.getArrayLists()[0].get(arrayList0.indexOf(strings)).getComponent(0).setForeground(theme.getTextFieldTheme()[0][Integer.parseInt(strings[1])]);
                    } else {
                        request.getArrayLists()[0].get(arrayList0.indexOf(strings)).getComponent(0).setForeground(theme.getTextFieldTheme()[1][Integer.parseInt(strings[1])]);
                    }
                    ((JTextField) request.getArrayLists()[0].get(arrayList0.indexOf(strings)).getComponent(1)).setText(strings[2]);
                    if (Boolean.parseBoolean(strings[4])) {
                        request.getArrayLists()[0].get(arrayList0.indexOf(strings)).getComponent(1).setForeground(theme.getTextFieldTheme()[0][Integer.parseInt(strings[3])]);
                    } else {
                        request.getArrayLists()[0].get(arrayList0.indexOf(strings)).getComponent(1).setForeground(theme.getTextFieldTheme()[1][Integer.parseInt(strings[3])]);
                    }
                    ((JCheckBox)  requests.get(0).getArrayLists()[0].get(arrayList0.indexOf(strings)).getComponent(3)).setSelected(Boolean.parseBoolean(strings[4]));
                }

        request.getTextArea().setText(json);

        request.setBinaryFileAddress(binaryFileAddress);

        request.getChooseBodyType().setSelectedIndex(bodyType);

        request.setDefaultPanels0(false);

        request.setDefaultPanels2();

        for (String[] strings : arrayList1) {
            request.addField(1);
                ((JTextField) request.getArrayLists()[1].get(arrayList1.indexOf(strings)).getComponent(0)).setText(strings[0]);
                if (Boolean.parseBoolean(strings[4])) {
                    request.getArrayLists()[1].get(arrayList1.indexOf(strings)).getComponent(0).setForeground(theme.getTextFieldTheme()[0][Integer.parseInt(strings[1])]);
                } else {
                    request.getArrayLists()[1].get(arrayList1.indexOf(strings)).getComponent(0).setForeground(theme.getTextFieldTheme()[1][Integer.parseInt(strings[1])]);
                }
                ((JTextField) request.getArrayLists()[1].get(arrayList1.indexOf(strings)).getComponent(1)).setText(strings[2]);
                if (Boolean.parseBoolean(strings[4])) {
                    request.getArrayLists()[1].get(arrayList1.indexOf(strings)).getComponent(1).setForeground(theme.getTextFieldTheme()[0][Integer.parseInt(strings[3])]);
                } else {
                    request.getArrayLists()[1].get(arrayList1.indexOf(strings)).getComponent(1).setForeground(theme.getTextFieldTheme()[1][Integer.parseInt(strings[3])]);
                }
                ((JCheckBox)  request.getArrayLists()[1].get(arrayList1.indexOf(strings)).getComponent(3)).setSelected(Boolean.parseBoolean(strings[4]));
        }

        request.setDefaultPanels3();

        for (String[] strings : arrayList2) {
            request.addField(2);
                ((JTextField) request.getArrayLists()[2].get(arrayList2.indexOf(strings)).getComponent(0)).setText(strings[0]);
                if (Boolean.parseBoolean(strings[4])) {
                    request.getArrayLists()[2].get(arrayList2.indexOf(strings)).getComponent(0).setForeground(theme.getTextFieldTheme()[0][Integer.parseInt(strings[1])]);
                } else {
                    request.getArrayLists()[2].get(arrayList2.indexOf(strings)).getComponent(0).setForeground(theme.getTextFieldTheme()[1][Integer.parseInt(strings[1])]);
                }
                ((JTextField) request.getArrayLists()[2].get(arrayList2.indexOf(strings)).getComponent(1)).setText(strings[2]);
                if (Boolean.parseBoolean(strings[4])) {
                    request.getArrayLists()[2].get(arrayList2.indexOf(strings)).getComponent(1).setForeground(theme.getTextFieldTheme()[0][Integer.parseInt(strings[3])]);
                } else {
                    request.getArrayLists()[2].get(arrayList2.indexOf(strings)).getComponent(1).setForeground(theme.getTextFieldTheme()[1][Integer.parseInt(strings[3])]);
                }
                ((JCheckBox)  request.getArrayLists()[2].get(arrayList2.indexOf(strings)).getComponent(3)).setSelected(Boolean.parseBoolean(strings[4]));
        }

    }

    /**
     *
     * @param fileToOpen the file of saving request
     */
    public void loadRequest(File fileToOpen) {

            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileToOpen))){

                Request request = ((Request) in.readObject());

                requests.add(0, new Request(theme, request.getRequestNameString(), request.getMethodType(), unripePage.getSize(), getThis()));

                ArrayList<Response> loadResponses = request.getResponses();

                ArrayList<Response> saveResponses = requests.get(0).getResponses();

                for (Response response : loadResponses) {

                    loadData(requests.get(0),response.getSaveFormBody(),response.getSaveQuery(),response.getSaveHeader(),response.getJson(),response.getBinaryFileAddress(),response.getBodyType());

                    saveResponses.add(new Response(theme ,response.getMethod() ,response.getBodyType() ,response.getUrl() ,response.getOriginalUrl() ,requests.get(0).getArrayLists()[2] ,requests.get(0).getArrayLists()[1] ,requests.get(0).getArrayLists()[0] ,response.getBinaryFileAddress() , response.getJson()));

                    String date = response.getDateOfSendingRequest();
                    requests.get(0).getComboBoxResponses().addItem((loadResponses.size() - loadResponses.indexOf(response)) + ")" +date + " "+ response.getMethod() + " " + response.getOriginalUrl());

                    saveResponses.get(saveResponses.size() - 1).setLabels(response.getResponseCode(),response.getResponseStatus(),response.getSaveValue()[0],response.getSaveValue()[1]);

                    saveResponses.get(saveResponses.size() - 1).setSize(response.getSize());

                    for (String key : response.getSaveHeaderResponse().keySet()) {

                        saveResponses.get(saveResponses.size() - 1).getHeader().add(new JLabel(key));
                        ((JScrollPane) saveResponses.get(saveResponses.size() - 1).getTabPanel()[1].getComponent(0)).add(saveResponses.get(saveResponses.size() - 1).getHeader().get(saveResponses.get(saveResponses.size() - 1).getHeader().size() - 1));

                        saveResponses.get(saveResponses.size() - 1).getHeader().add(new JLabel(response.getSaveHeaderResponse().get(key)));
                        ((JScrollPane) saveResponses.get(saveResponses.size() - 1).getTabPanel()[1].getComponent(0)).add(saveResponses.get(saveResponses.size() - 1).getHeader().get(saveResponses.get(saveResponses.size() - 1).getHeader().size() - 1));

                    }

                    for (String key : response.getSaveCookie().keySet()) {

                        saveResponses.get(saveResponses.size() - 1).getCookie().add(new JLabel(key));
                        ((JScrollPane) saveResponses.get(saveResponses.size() - 1).getTabPanel()[2].getComponent(0)).add(saveResponses.get(saveResponses.size() - 1).getCookie().get(saveResponses.get(saveResponses.size() - 1).getCookie().size() - 1));

                        saveResponses.get(saveResponses.size() - 1).getCookie().add(new JLabel(response.getSaveCookie().get(key)));
                        ((JScrollPane) saveResponses.get(saveResponses.size() - 1).getTabPanel()[2].getComponent(0)).add(saveResponses.get(saveResponses.size() - 1).getCookie().get(saveResponses.get(saveResponses.size() - 1).getCookie().size() - 1));

                    }

                    saveResponses.get(saveResponses.size() - 1).setDateOfSendingRequest(response.getDateOfSendingRequest());
                    saveResponses.get(saveResponses.size() - 1).setSaveValue(response.getSaveValue());
                    saveResponses.get(saveResponses.size() - 1).setTextArea1Text(response.getSaveResponseBody());
                    saveResponses.get(saveResponses.size() - 1).setSaveResponseBody(response.getSaveResponseBody());
                    saveResponses.get(saveResponses.size() - 1).setSaveCookie(response.getSaveCookie());
                    saveResponses.get(saveResponses.size() - 1).setSaveHeaderResponse(response.getSaveHeaderResponse());
                    saveResponses.get(saveResponses.size() - 1).setSize(response.getSize());

                    saveResponses.get(saveResponses.size() - 1).setSaveFormBody(response.getSaveFormBody());
                    saveResponses.get(saveResponses.size() - 1).setSaveQuery(response.getSaveQuery());
                    saveResponses.get(saveResponses.size() - 1).setSaveHeader(response.getSaveHeader());

                }

                if (requests.get(0).getComboBoxResponses().getItemCount() > 2) {
                    requests.get(0).getComboBoxResponses().setSelectedIndex(2);
                }

                requests.get(0).getURL().setText(request.getUrl());

                loadData(requests.get(0),request.getSaveFormBody(),request.getSaveQuery(),request.getSaveHeader(),request.getBinaryFileAddress() ,request.getJson() ,request.getBodyType());

                requests.get(0).getChooseBodyType().setSelectedIndex(request.getBodyType());

                setUnripePage(requests.get(0));

                ((Request) unripePage).getRequestName().addMouseListener(handler);
                ((Request) unripePage).getRequestName().addKeyListener(handler);

                panels[1].add(requests.get(0).getPageLabel(), 0);

                setPageLabel();

                requests.get(0).setQuery();

                requests.get(0).getComboBox().setSelectedItem(requests.get(0).getMethodType());

            } catch (IOException | ClassNotFoundException e2){
                e2.printStackTrace();
            }

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

                comboBoxes[0] = new JComboBox<>(new String[]{"GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "HEAD", "Custom Method"});
                comboBoxes[0].setBounds(400, 0, 150, 35);

                comboBoxes[1] = new JComboBox<>(new String[]{"Add Method", "Select Method", "Remove Method"});
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

                    try {
                        panel.updateUI();
                    } catch (NullPointerException ignore) {
                    }
                });


                comboBoxes[1].addActionListener(event -> {
                    if (comboBoxes[1].isEnabled()) {

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

                    if (comboBoxes[1].isEnabled()) {

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

            else if (e.getSource().equals(menus[2].getItem(1)) || e.getSource().equals(unripePage.getImportFromFile())) {

                JFrame parentFrame = new JFrame();

                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Open Request");

                try {
                    if (fileChooser.showOpenDialog(parentFrame) == JFileChooser.APPROVE_OPTION) {
                        File fileToOpen = fileChooser.getSelectedFile();
                        if (fileToOpen.getAbsolutePath().endsWith(".obj")) {
                            Thread thread = new Thread(() -> loadRequest(fileToOpen));
                            thread.start();
                        } else {

                            JLabel label = new JLabel("this file is not a request file");

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
                } catch (ArrayIndexOutOfBoundsException e1) {
                    e1.printStackTrace();
                }

            }

            else if (e.getSource().equals(menus[0].getItem(1))) {

                JFrame parentFrame = new JFrame();

                    if (requests.size() > 0) {

                        JComboBox<String> comboBox = new JComboBox<>();

                        for (Request request : requests) {
                            comboBox.addItem(request.getRequestName().getText());
                            if (request.isSelected()) {
                                comboBox.setSelectedItem(request.getRequestName().getText());
                            }
                        }

                        JOptionPane.showOptionDialog(
                                parentFrame,
                                comboBox,
                                "Switch Requests",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.PLAIN_MESSAGE,
                                null,
                                new String[]{"OK"},
                                "OK");

                        int index = comboBox.getSelectedIndex();

                        setUnripePage(requests.get(index));
                        requests.get(index).setHover();
                        requests.get(index).setSelected(true);
                        requests.get(index).setOtherButtonDeSelect();

                    } else {

                        JLabel label = new JLabel("there is no request to choose.");

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

            try {
                getThis().updateUI();
            } catch (NullPointerException ignore) {
            }

        }

        @Override
        public void mouseClicked(MouseEvent e) {

            for (Request request : requests) {

                if (e.getSource().equals(request.getRequestName())) {
                    if (e.getClickCount() > 1) {
                        request.getRequestName().setEditable(true);
                        request.getRequestName().setFocusable(true);
                        request.getRequestName().setForeground(theme.getTextFieldTheme()[0][2]);
                        request.getRequestName().setFont(new Font("Normal", Font.ITALIC, request.getRequestName().getFont().getSize()));
                        try {
                            request.getRequestName().updateUI();
                        } catch (NullPointerException ignore) {
                        }
                    }
                } else if (request.getRequestName().isEnabled()) {
                    request.getRequestName().setEditable(false);
                    request.getRequestName().setFocusable(false);
                    request.getRequestName().setForeground(theme.getTextFieldTheme()[0][1]);
                    request.getRequestName().setFont(new Font("Normal", Font.PLAIN, request.getRequestName().getFont().getSize()));
                    request.setRequestNameString(request.getRequestName().getText());
                    try {
                        request.getRequestName().updateUI();
                    } catch (NullPointerException ignore) {
                    }
                }

            }

            try {
                updateUI();
            } catch (NullPointerException ignore) {
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

            if (e.getSource().equals(menus[0])) {
                menuBars[0].setBackground(theme.getInsomniaTheme()[2]);
            }

            try {
                updateUI();
            } catch (NullPointerException ignore) {
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {

            if (e.getSource().equals(buttons[0]) && !buttons[0].isSelected()) {
                menus[1].doClick();
                buttons[0].setSelected(true);
            } else {
                buttons[0].setSelected(false);
            }

            if (e.getSource().equals(buttons[2]) && !buttons[2].isSelected()) {
                menus[2].doClick();
                buttons[2].setSelected(true);
            } else {
                buttons[2].setSelected(false);
            }

            try {
                getThis().updateUI();
            } catch (NullPointerException ignore) {
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {

            if (e.getSource().equals(menus[0])) {
                menuBars[0].setBackground(theme.getInsomniaTheme()[2]);
            }

            try {
                getThis().updateUI();
            } catch (NullPointerException ignore) {
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {

            if (e.getSource().equals(menus[0])) {
                menuBars[0].setBackground(theme.getInsomniaTheme()[0]);
            }

            try {
                getThis().updateUI();
            } catch (NullPointerException ignore) {
            }
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
                    char[] search = filter.getText().toLowerCase().toCharArray();

                    for (char c : search) {

                        if (name.indexOf(c) != -1) {
                            name = name.substring(name.indexOf(c));
                        } else {
                            panels[1].remove(request.getPageLabel());
                            break;
                        }

                    }

                }


                setPageLabel();

            }

            try {
                getThis().updateUI();
            } catch (NullPointerException ignore) {
            }

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
