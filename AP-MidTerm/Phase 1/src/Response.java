import com.sun.java.swing.plaf.windows.WindowsScrollBarUI;
import com.sun.java.swing.plaf.windows.WindowsScrollPaneUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.HierarchyBoundsListener;
import java.awt.event.HierarchyEvent;
import java.util.ArrayList;
import java.util.Date;

/**
 * this class creat response for each request
 */
public class Response {

    private Theme theme;

    private Date dateOfSendingResponse;

    private Handler handler;

    private JPanel panel;
    private JLabel[] labels;
    private JTabbedPane tabbedPane;
    private JPanel[] tabPanel;
    private ArrayList<JLabel> header;
    private ArrayList<JLabel> cookie;
    private JButton[] buttons;
    private JTextArea textArea1 = new JTextArea();
    private JTextArea textArea2 = new JTextArea();

    /**
     * this the constructor of Response class
     */
    public Response(Theme theme){

        this.theme = theme;

        dateOfSendingResponse = new Date();

        handler = new Handler();
        panel = new JPanel();
        labels = new JLabel[3];
        tabbedPane = new JTabbedPane();
        tabPanel = new JPanel[4];
        buttons = new JButton[2];
        header = new ArrayList<>();
        cookie = new ArrayList<>();

        for (int i = 0; i < 3; i++) {

            labels[i] = new JLabel();
            labels[i].setOpaque(true);
            labels[i].setHorizontalAlignment(SwingConstants.CENTER);
            labels[i].setBorder(BorderFactory.createLineBorder(Color.GRAY));

            if (i != 2) {

                tabPanel[2*i] = new JPanel();
                tabPanel[2*i].setLayout(new BorderLayout(0,0));
                tabPanel[2*i].setBackground(Color.DARK_GRAY);
                tabPanel[2*i+1] = new JPanel();
                tabPanel[2*i+1].setLayout(new BorderLayout(0,0));
                tabPanel[2*i+1].setBackground(Color.DARK_GRAY);
                buttons[i] = new JButton();

            }

        }

        panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(null);
        panel.add(labels[0]);
        panel.add(labels[1]);
        panel.add(labels[2]);

        labels[0].setText("Error");
        labels[0].setBounds(10,10,50,25);
        labels[0].setForeground(Color.white);
        labels[0].setBackground(new Color(0xE15251));

        labels[1].setText("0 ms");
        labels[1].setBounds(70,10,50,25);
        labels[1].setForeground(Color.GRAY);
        labels[1].setBackground(new Color(0xDADDD0));

        labels[2].setText("0 B");
        labels[2].setBounds(130,10,50,25);
        labels[2].setForeground(Color.GRAY);
        labels[2].setBackground(new Color(0xDADDD0));

        tabbedPane.addTab("Preview",tabPanel[0]);
        tabbedPane.addTab("Header",tabPanel[1]);
        tabbedPane.addTab("Cookie",tabPanel[2]);
        tabbedPane.addTab("Timeline",tabPanel[3]);

        setDefaultTabPanel0();
        setDefaultTabPanel1();
        setDefaultTabPanel2();
        setDefaultTabPanel3();

    }

    public Date getDateOfSendingResponse() {
        return dateOfSendingResponse;
    }

    /**
     *
     * @return the panel which contain all of North panel in the last request panel
     */
    public JPanel getPanel() {
        return panel;
    }

    /**
     *
     * @return is the tab of response panel field
     */
    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    /**
     * set the tab 1 default form
     */
    private void setDefaultTabPanel0(){

        textArea1.setBackground(theme.getBackGroundTheme()[2]);

        JScrollPane scrollPane = new JScrollPane(textArea1);
        scrollPane.setUI(new WindowsScrollPaneUI());
        scrollPane.getHorizontalScrollBar().setUI(new WindowsScrollBarUI());
        scrollPane.getVerticalScrollBar().setUI(new WindowsScrollBarUI());

        tabPanel[0].add(scrollPane ,BorderLayout.CENTER);
        tabPanel[0].getComponent(0).setBackground(theme.getBackGroundTheme()[2]);
        tabPanel[0].getComponent(0).setForeground(theme.getTextFieldTheme()[0][1]);

    }

    /**
     * set the tab 2 default form
     */
    private void setDefaultTabPanel1(){

        header.add(new JLabel("Name"));
        header.add(new JLabel("Value"));

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBackground(theme.getBackGroundTheme()[2]);
        scrollPane.setUI(new WindowsScrollPaneUI());
        scrollPane.getHorizontalScrollBar().setUI(new WindowsScrollBarUI());
        scrollPane.getVerticalScrollBar().setUI(new WindowsScrollBarUI());
        scrollPane.addHierarchyBoundsListener(handler);
        scrollPane.setLayout(null);
        scrollPane.add(header.get(0));
        scrollPane.add(header.get(1));
        scrollPane.add(buttons[0]);

        header.get(0).setBackground(theme.getLabelTheme()[3][0]);
        header.get(0).setForeground(theme.getLabelTheme()[3][1]);
        header.get(0).setBounds(0,0,335/2,30);

        header.get(1).setBackground(theme.getLabelTheme()[3][0]);
        header.get(1).setForeground(theme.getLabelTheme()[3][1]);
        header.get(1).setBounds(335/2,0,335,30);

        buttons[0].setBounds(170,30,150,30);
        buttons[0].setText("Copy to Clipboard");
        buttons[0].setBackground(theme.getButtonTheme()[0][0]);
        buttons[0].setForeground(theme.getButtonTheme()[0][1]);

        tabPanel[1].add(scrollPane ,BorderLayout.CENTER);
        tabPanel[1].setBorder(new EmptyBorder(20,20,20,20));

    }

    /**
     * set the tab 3 default form
     */
    private void setDefaultTabPanel2(){

        cookie.add(new JLabel("Name"));
        cookie.add(new JLabel("Value"));

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBackground(theme.getBackGroundTheme()[2]);
        scrollPane.setUI(new WindowsScrollPaneUI());
        scrollPane.getHorizontalScrollBar().setUI(new WindowsScrollBarUI());
        scrollPane.getVerticalScrollBar().setUI(new WindowsScrollBarUI());
        scrollPane.addHierarchyBoundsListener(handler);
        scrollPane.setLayout(null);
        scrollPane.add(cookie.get(0));
        scrollPane.add(cookie.get(1));
        scrollPane.add(buttons[1]);

        cookie.get(0).setBackground(theme.getLabelTheme()[3][0]);
        cookie.get(0).setForeground(theme.getLabelTheme()[3][1]);
        cookie.get(0).setBounds(0,0,335/2,30);

        cookie.get(1).setBackground(theme.getLabelTheme()[3][0]);
        cookie.get(1).setForeground(theme.getLabelTheme()[3][1]);
        cookie.get(1).setBounds(335/2,0,335,30);

        buttons[1].setBounds(170,30,150,30);
        buttons[1].setText("Manage Cookies");
        buttons[1].setBackground(theme.getButtonTheme()[0][0]);
        buttons[1].setForeground(theme.getButtonTheme()[0][1]);

        tabPanel[2].add(scrollPane ,BorderLayout.CENTER);
        tabPanel[2].setBorder(new EmptyBorder(20,20,20,20));

    }

    /**
     * set the tab 4 default form
     */
    private void setDefaultTabPanel3(){

        textArea2.setBackground(theme.getBackGroundTheme()[2]);

        JScrollPane scrollPane = new JScrollPane(textArea2);
        scrollPane.setUI(new WindowsScrollPaneUI());
        scrollPane.getHorizontalScrollBar().setUI(new WindowsScrollBarUI());
        scrollPane.getVerticalScrollBar().setUI(new WindowsScrollBarUI());

        tabPanel[3].add(scrollPane ,BorderLayout.CENTER);
        tabPanel[3].getComponent(0).setBackground(theme.getBackGroundTheme()[2]);
        tabPanel[3].getComponent(0).setForeground(theme.getTextFieldTheme()[0][1]);

    }

    /**
     * set Color of them in the request and position of panels
     */
    public void setLayout(){

        for (JLabel label : header) {

            label.setOpaque(true);

            if (header.indexOf(label) % 2 == 0) {
                label.setBounds(0, 30 * (Math.floorDiv(header.indexOf(label), 2)), tabPanel[1].getWidth() / 2 - 20, 30);
            } else {
                label.setBounds(tabPanel[1].getWidth() / 2 - 20, 30 * (Math.floorDiv(header.indexOf(label), 2)), tabPanel[1].getWidth() / 2 - 20, 30);
            }

            if(header.indexOf(label) > 1) {

                if ((Math.floorDiv(header.indexOf(label), 2) % 2 == 1)) {
                    label.setBackground(theme.getLabelTheme()[3][0]);
                } else {
                    label.setBackground(theme.getLabelTheme()[4][0]);
                }

                label.setForeground(theme.getLabelTheme()[3][2]);

            }

        }

        for (JLabel label : cookie) {

            label.setOpaque(true);

            if (cookie.indexOf(label) % 2 == 0) {
                label.setBounds(0, 30 * (Math.floorDiv(cookie.indexOf(label), 2)), tabPanel[2].getWidth() / 2 - 20, 30);
            } else {
                label.setBounds(tabPanel[2].getWidth() / 2 - 20, 30 * (Math.floorDiv(cookie.indexOf(label), 2)), tabPanel[2].getWidth() / 2 - 20, 30);
            }

            if(cookie.indexOf(label) > 1) {

                if ((Math.floorDiv(header.indexOf(label), 2) % 2 == 1)) {
                    label.setBackground(theme.getLabelTheme()[3][0]);
                } else {
                    label.setBackground(theme.getLabelTheme()[4][0]);
                }

                label.setForeground(theme.getLabelTheme()[3][2]);

            }

        }

        buttons[0].setBounds(tabPanel[1].getWidth() - 190,30 * Math.floorDiv(header.size()+1,2) + 10,150,30);
        buttons[1].setBounds(tabPanel[2].getWidth() - 190,30 * Math.floorDiv(header.size()+1,2) + 10,150,30);

        buttons[0].setBackground(theme.getButtonTheme()[0][0]);
        buttons[0].setForeground(theme.getButtonTheme()[0][1]);

        buttons[1].setBackground(theme.getButtonTheme()[0][0]);
        buttons[1].setForeground(theme.getButtonTheme()[0][1]);

        textArea1.setBackground(theme.getBackGroundTheme()[2]);
        textArea1.setForeground(theme.getTextFieldTheme()[0][1]);

        tabPanel[1].setBackground(theme.getBackGroundTheme()[2]);
        tabPanel[1].getComponent(0).setBackground(theme.getBackGroundTheme()[2]);

        tabPanel[2].setBackground(theme.getBackGroundTheme()[2]);
        tabPanel[2].getComponent(0).setBackground(theme.getBackGroundTheme()[2]);

        textArea2.setBackground(theme.getBackGroundTheme()[2]);
        textArea2.setForeground(theme.getTextFieldTheme()[0][1]);


    }

    /**
     * an inner class which control events
     */
    private class Handler implements HierarchyBoundsListener{

        @Override
        public void ancestorMoved(HierarchyEvent e) {

        }

        @Override
        public void ancestorResized(HierarchyEvent e) {
            setLayout();
        }
    }

}
