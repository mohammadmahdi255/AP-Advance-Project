package DownloadAndUploadManager;

import javax.net.ssl.HttpsURLConnection;
import javax.swing.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Download implements Runnable {

    private DownloadStatusAndInfo downloadStatusAndInfo;

    public Download (DownloadStatusAndInfo downloadStatusAndInfo) {

        this.downloadStatusAndInfo = downloadStatusAndInfo;

    }

    @Override
    public void run() {

        HttpURLConnection connection;

        try {
            if ("http".equals(downloadStatusAndInfo.getUrl().getProtocol())) {
                connection = (HttpURLConnection) downloadStatusAndInfo.getUrl().openConnection();
            } else if ("https".equals(downloadStatusAndInfo.getUrl().getProtocol())) {
                connection = (HttpsURLConnection) downloadStatusAndInfo.getUrl().openConnection();
            } else {
                System.err.println("UNSUPPORTED PROTOCOL!");
                return;
            }
        } catch (IOException EX) {
            System.err.println("FAILED TO OPEN CONNECTION!\n" + EX);
            return;
        }

        downloadStatusAndInfo.setSize(new Size(connection.getContentLengthLong()));
        connection.disconnect();

        ExecutorService executor = Executors.newCachedThreadPool();
        DownloadPart[] downloadParts = new DownloadPart[downloadStatusAndInfo.getPart()];

        if (downloadStatusAndInfo.getSize().getContent() != -1) {

            long range = Math.floorDiv(downloadStatusAndInfo.getSize().getContent(), downloadStatusAndInfo.getPart());

            for (int i = 0; i < downloadStatusAndInfo.getPart(); i++) {
                downloadParts[i] = new DownloadPart(downloadStatusAndInfo, i * range, i == downloadStatusAndInfo.getPart() - 1 ? downloadStatusAndInfo.getSize().getContent() : (i + 1) * range - 1, i);
                executor.execute(downloadParts[i]);
            }

        }

        executor.shutdown();

    }


}
