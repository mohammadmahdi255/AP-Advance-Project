package GUI;

import DownloadAndUploadManager.Download;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.net.MalformedURLException;

public class StartDownloadGUI extends JFrame {

    private JPanel panel;

    private JLabel groupLabel;
    private JLabel saveToLabel;
    private JLabel urlLabel;
    private JLabel descriptionLabel;

    private JTextField group;
    private JTextField saveTo;
    private JTextField url;
    private JTextField description;

    private JButton browse;
    private JButton downloadLater;
    private JButton startDownload;
    private JButton cancel;

    private String fileName;


    public StartDownloadGUI (){

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                System.out.println(info.getName());
                if ("Windows".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) {
        }

        panel = new JPanel();

        groupLabel = new JLabel();
        saveToLabel = new JLabel("Save As");
        urlLabel = new JLabel("URL");
        descriptionLabel = new JLabel("Description");

        group = new JTextField();
        saveTo = new JTextField();
        url = new JTextField();
        description = new JTextField();

        browse = new JButton("...");
        downloadLater = new JButton("Download Later");
        startDownload = new JButton("Start Download");
        cancel = new JButton("Cancel");

        panel.setSize(610,230);
        panel.setLocation(0,0);
        panel.setLayout(null);
        panel.add(group);
        panel.add(saveToLabel);
        panel.add(saveTo);
        panel.add(urlLabel);
        panel.add(url);
        panel.add(descriptionLabel);
        panel.add(description);
        panel.add(browse);
        panel.add(downloadLater);
        panel.add(startDownload);
        panel.add(cancel);

        urlLabel.setBounds(65,10,30,20);
        url.setBounds(100,10,390,20);
        saveToLabel.setBounds(45,70,50,20);
        saveTo.setBounds(100,70,360,20);
        browse.setBounds(470,70,20,20);
        descriptionLabel.setBounds(35,100,60,20);
        description.setBounds(100,100,390,20);
        downloadLater.setBounds(100,130,120,25);
        startDownload.setBounds(235,130,120,25);
        cancel.setBounds(370,130,120,25);

        groupLabel.setHorizontalAlignment(JTextField.RIGHT);
        urlLabel.setHorizontalAlignment(JTextField.RIGHT);
        saveToLabel.setHorizontalAlignment(JTextField.RIGHT);
        descriptionLabel.setHorizontalAlignment(JTextField.RIGHT);

        browse.addActionListener(e -> {

            JFileChooser saveTo = new JFileChooser();
            JFrame parentFrame = new JFrame();

            if (saveTo.showSaveDialog(parentFrame) == JFileChooser.APPROVE_OPTION) {
                this.saveTo.setText(saveTo.getCurrentDirectory().getAbsolutePath());
                fileName = saveTo.getSelectedFile().getName();
                System.out.println(fileName);
            }

        });

        downloadLater.addActionListener(e -> closeDisplay());

        startDownload.addActionListener(e -> {

            closeDisplay();
            DownloadGUI downloadGUI = new DownloadGUI(fileName,url.getText(),saveTo.getText(),8,description.getText());

        });

        cancel.addActionListener(e -> closeDisplay());

        add(panel);
        setAlwaysOnTop(true);
        setResizable(false);
        setSize(panel.getSize());
        setLayout(null);
        setVisible(true);

    }

    private void closeDisplay(){

        WindowEvent winClosingEvent = new WindowEvent(this,WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosingEvent);

    }

}
