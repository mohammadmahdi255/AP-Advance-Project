import sun.awt.AWTAccessor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.HierarchyBoundsListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.KeyListener;
import java.io.Serializable;

/**
 * this class is creat the start page
 */
public class UnripePage extends JPanel implements Runnable , Serializable {

    private static final long serialVersionUID = 13443245553L;

    protected transient Theme theme;

    private transient JPanel[] parts;
    private transient JPanel[] hold;

    private transient JPanel panel;
    private transient JLabel[] labels;
    private transient JButton importFromFile;
    private transient JButton newRequest;

    /**
     * the constructor of this class
     * @param size is the size of page
     */
    public UnripePage (Theme theme, Dimension size) {

        this.theme = theme;

        parts = new JPanel[4];
        hold = new JPanel[2];

        hold[0] = new JPanel();
        hold[1] = new JPanel();

        importFromFile = new JButton("Import From File");
        newRequest = new JButton("New Request");

        labels = new JLabel[6];
        panel = new JPanel();

        for (int i = 0; i < 4; i++) {

            parts[i] = new JPanel();

            if (i % 2 == 0) {
                parts[i].setSize(size.width/2, 50);
                parts[i].setPreferredSize(new Dimension(size.width/2, 50));
                parts[i].setBackground(theme.getBackGroundTheme()[1]);
            } else {
                parts[i].setSize(size.width/2, size.height - 50);
                parts[i].setPreferredSize(new Dimension(size.width/2, size.height - 50));
                parts[i].setBackground(theme.getBackGroundTheme()[2]);
            }

        }

        labels[0] = new JLabel("New Request");

        labels[1] = new JLabel("Ctrl+N");

        labels[2] = new JLabel("Switch Requests");

        labels[3] = new JLabel("Ctrl+P");


        for (int i = 0; i < 2; i++) {
            labels[2*i+1].setHorizontalAlignment(SwingConstants.CENTER);
        }

        importFromFile.setSize(130,30);

        newRequest.setSize(90,30);

        for (int i = 0; i < 4; i++) {

            panel.add(labels[i]);
            labels[i].setOpaque(true);

        }

        panel.setLayout(new GridLayout(3,2,10,10));
        panel.add(importFromFile);
        panel.add(newRequest);

        parts[0].setLayout(new BorderLayout(0,0));

        parts[1].setLayout(null);
        parts[1].add(panel);

        parts[2].setLayout(new BorderLayout(0,0));

        parts[3].setLayout(null);

        hold[0].setSize(size.width/2,size.height);
        hold[0].setLayout(new BorderLayout(0, 0));
        hold[0].add(parts[0], BorderLayout.NORTH);
        hold[0].add(parts[1], BorderLayout.CENTER);

        hold[1].setSize(size.width/2,size.height);
        hold[1].setLayout(new BorderLayout(0, 0));
        hold[1].add(parts[2], BorderLayout.NORTH);
        hold[1].add(parts[3], BorderLayout.CENTER);

        setLayout(new BorderLayout(0, 0));
        setSize(size.width , size.height);
        add(hold[0], BorderLayout.CENTER);
        add(hold[1], BorderLayout.EAST);
        addHierarchyBoundsListener(new Handler());

        setTheme();

    }

    /**
     *
     * @return two panel for work space and response
     */
    public JPanel[] getHold() {
        return hold;
    }

    /**
     *
     * @return the four part of this JPanel
     */
    public JPanel[] getParts() {
        return parts;
    }

    /**
     *
     * @return the information of new request button
     */
    public JButton getNewRequest() {
        return newRequest;
    }

    /**
     *
     * @return the importFromFile button
     */
    public JButton getImportFromFile() {
        return importFromFile;
    }

    /**
     *
     * @return the JPanel of labels
     */
    public JPanel getPanel() {
        return panel;
    }

    /**
     * set a theme
     */
    public void setTheme(){

        for (int i = 0; i < 4; i++) {

            if (i % 2 == 0) {
                parts[i].setBackground(theme.getBackGroundTheme()[1]);
            } else {
                parts[i].setBackground(theme.getBackGroundTheme()[2]);
            }

        }

        for (int i = 0; i < 2; i++) {

            labels[2 * i + 1].setBackground(theme.getLabelTheme()[1][0]);
            labels[2 * i + 1].setForeground(theme.getLabelTheme()[1][1]);
            labels[2 * i + 1].setBorder(BorderFactory.createLineBorder(theme.getLabelTheme()[1][3]));

            labels[2 * i].setBackground(theme.getBackGroundTheme()[2]);
            labels[2 * i].setForeground(theme.getLabelTheme()[1][1]);

        }

        importFromFile.setBackground(theme.getButtonTheme()[0][0]);
        importFromFile.setForeground(theme.getButtonTheme()[0][1]);

        newRequest.setBackground(theme.getButtonTheme()[0][0]);
        newRequest.setForeground(theme.getButtonTheme()[0][1]);

        panel.setBackground(theme.getBackGroundTheme()[2]);

        parts[0].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 0, theme.getLabelTheme()[1][3]));

        parts[1].setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, theme.getLabelTheme()[1][3]));

        parts[2].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 0, theme.getLabelTheme()[1][3]));

        parts[3].setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, theme.getLabelTheme()[1][3]));

        updateUI();

    }

    /**
     * set every components that not use Layout Manager
     * @param update if the sidebar is Toggle the value is true
     */
    protected void setLayout(boolean update) {

        removeAll();

        setSize(this.getParent().getWidth() - this.getParent().getComponent(0).getWidth() , this.getParent().getHeight());

        if (getWidth() > (950 - getParent().getComponent(0).getWidth())) {

            add(hold[0], BorderLayout.CENTER);
            add(hold[1], BorderLayout.EAST);
            for (int i = 0; i < 2; i++) {
                hold[i].setPreferredSize(new Dimension((this.getParent().getWidth() - this.getParent().getComponent(0).getWidth()) / 2, this.getParent().getHeight()));
                hold[i].setSize(new Dimension((this.getParent().getWidth() - this.getParent().getComponent(0).getWidth()) / 2, this.getParent().getHeight()));
            }

            if (update) {
                for (int i = 0; i < 4; i++) {
                    if (i % 2 == 0) {
                        parts[i].setPreferredSize(new Dimension((this.getParent().getWidth() - this.getParent().getComponent(0).getWidth()) / 2, 50));
                        parts[i].setSize(new Dimension((this.getParent().getWidth() - this.getParent().getComponent(0).getWidth()) / 2, 50));
                    } else {
                        parts[i].setPreferredSize(new Dimension((this.getParent().getWidth() - this.getParent().getComponent(0).getWidth()) / 2, this.getParent().getHeight() - 50));
                        parts[i].setSize(new Dimension((this.getParent().getWidth() - this.getParent().getComponent(0).getWidth()) / 2, this.getParent().getHeight() - 50));
                    }
                }
            }

        } else {

            add(hold[0], BorderLayout.NORTH);
            add(hold[1], BorderLayout.CENTER);
            for (int i = 0; i < 2; i++) {
                hold[i].setPreferredSize(new Dimension(this.getParent().getWidth() - this.getParent().getComponent(0).getWidth(), this.getParent().getHeight() / 2));
                hold[i].setSize(new Dimension(this.getParent().getWidth() - this.getParent().getComponent(0).getWidth(), this.getParent().getHeight() / 2));
            }

            if (update) {
                for (int i = 0; i < 4; i++) {
                    if (i % 2 == 0) {
                        parts[i].setPreferredSize(new Dimension(this.getParent().getWidth() - this.getParent().getComponent(0).getWidth(), 50));
                        parts[i].setSize(new Dimension(this.getParent().getWidth() - this.getParent().getComponent(0).getWidth(), 50));
                    } else {
                        parts[i].setPreferredSize(new Dimension(this.getParent().getWidth() - this.getParent().getComponent(0).getWidth(), this.getParent().getHeight() / 2 - 25));
                        parts[i].setSize(new Dimension(this.getParent().getWidth() - this.getParent().getComponent(0).getWidth(), this.getParent().getHeight() / 2 - 25));
                    }
                }
            }

        }

        panel.setBounds(hold[1].getPreferredSize().width/2 - 155, parts[1].getHeight()/2 - 65,310,130);

        try {
            ((JPanel) this.getParent()).updateUI();
        } catch (NullPointerException ignore){}

    }

    /**
     * update this JPanel
     */
    @Override
    public void run() {
        setLayout(false);
        try {
            updateUI();
        } catch (NullPointerException ignore){}
    }

    /**
     *  an inner class which control events
     */
    private class Handler implements HierarchyBoundsListener , Serializable {

        @Override
        public void ancestorMoved(HierarchyEvent e) {

        }

        @Override
        public void ancestorResized(HierarchyEvent e) {
            setLayout(true);
        }

    }

}
