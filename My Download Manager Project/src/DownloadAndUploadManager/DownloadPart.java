package DownloadAndUploadManager;

import javax.net.ssl.HttpsURLConnection;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadPart implements Runnable {

    private DownloadStatusAndInfo downloadStatusAndInfo;

    private int partNumber;

    private long startByte;
    private long endByte;

    private long size;
    private double percent;

    public DownloadPart(DownloadStatusAndInfo downloadStatusAndInfo,long startByte,long endByte,int partNumber){

        this.downloadStatusAndInfo = downloadStatusAndInfo;

        this.startByte = startByte;
        this.endByte = endByte;

        this.partNumber = partNumber;

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

            connection.setRequestMethod("POST");

        } catch (IOException EX){
            System.err.println("FAILED TO OPEN CONNECTION!\n" + EX);
            return;
        }

        connection.setRequestProperty("Range","bytes=" + startByte + "-" + endByte);

        try(InputStream in = connection.getInputStream();
            FileOutputStream out = new FileOutputStream(
                    new File("src/Download temp/" + downloadStatusAndInfo.getFileName() + "_" + partNumber))){


            size = connection.getContentLength();
            System.out.println(size);
            System.out.println(connection.getContentType());
            byte[] buffer = new byte[256];

            while (true){

                long read = in.read(buffer);

                if(read == -1)
                    break;

                out.write(buffer,0, (int) read);
                startByte += read;

                if(size != -1) {
                    percent += ((double)read/(double)size)*100;
                    downloadStatusAndInfo.addPercent(((double)read/(double)size)*100);
                    System.out.printf("Download>>part %d complete is %.2f \n", partNumber, percent);
                }

            }

        } catch (IOException EX){
            System.err.println(EX);
        }

    }

    public double getPercent() {
        return percent;
    }

}
