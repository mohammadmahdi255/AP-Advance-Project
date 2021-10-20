package GUI;

import DownloadAndUploadManager.Download;
import DownloadAndUploadManager.DownloadStatusAndInfo;

import javax.swing.*;
import javax.swing.plaf.ProgressBarUI;
import javax.swing.plaf.basic.BasicProgressBarUI;
import java.awt.*;
import java.net.MalformedURLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DownloadGUI extends JFrame {

    private JPanel mainPanel;
    private JPanel firstPanel;
    private JPanel secondPanel;
    private JProgressBar progressBar;

    private Download download;
    private DownloadStatusAndInfo downloadStatusAndInfo;

    public DownloadGUI(String fileName,String url,String saveTo,int part,String description) {

        try {

            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if(info.getName().equals("Windows")){
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }

        } catch (Exception ignore){
        }

        mainPanel = new JPanel();
        firstPanel = new JPanel();
        secondPanel = new JPanel();
        progressBar = new JProgressBar();

        mainPanel.setLayout(new BorderLayout(10,10));
        mainPanel.setBackground(Color.blue);
        firstPanel.setLayout(new BorderLayout(10,10));
        firstPanel.setBackground(Color.GREEN);
        secondPanel.setLayout(new GridLayout(3,1,10,10));
        secondPanel.setBackground(Color.MAGENTA);

        mainPanel.add(firstPanel,BorderLayout.CENTER);
        mainPanel.add(secondPanel,BorderLayout.SOUTH);
        secondPanel.add(progressBar);

        mainPanel.setPreferredSize(new Dimension(600,200));
        secondPanel.setPreferredSize(new Dimension(600,90));
        progressBar.setSize(570,35);

        progressBar.addChangeListener(e -> {

            if(progressBar.getValue() == progressBar.getMaximum()){
                downloadStatusAndInfo.mergingParts();
            }

        });

        progressBar.setBorderPainted(true);

        setAlwaysOnTop(true);
        setSize(600,600);
        setMinimumSize(new Dimension(600,400));
        setLayout(new BorderLayout(0,0));
        setVisible(true);
        add(mainPanel,BorderLayout.NORTH);

        try {
            downloadStatusAndInfo = new DownloadStatusAndInfo(fileName,url,saveTo,part,description,progressBar);
            download = new Download(downloadStatusAndInfo);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        ExecutorService executor = Executors.newCachedThreadPool();
        executor.execute(download);

    }

}
