package DownloadAndUploadManager;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

public class DownloadStatusAndInfo {

    private String fileName;
    private Size size;
    private Status status;
    private double percent;

    private Time timeLeft;
    private Date lastTryDate;
    private Date dataAdded;

    private String description;

    private URL url;
    private File saveTo;
    private String format;
    private int part;

    private JProgressBar progressBar;

    public DownloadStatusAndInfo (String fileName, String url, String saveTo, int part, String description, JProgressBar progressBar) throws MalformedURLException {

        this.fileName = fileName;
        status = Status.Incomplete;
        percent = 0.0;

        this.url = new URL(url);
        this.saveTo = new File(saveTo);
        this.description = description;
        this.part = part;

        format = url.contains(".") && !url.endsWith(".") ? url.substring(url.lastIndexOf('.')+1) : "html";

        this.progressBar = progressBar;

        System.out.println(fileName+"."+format);

    }

    public String getFileName() {
        return fileName;
    }

    public int getPart() {
        return part;
    }

    public URL getUrl() {
        return url;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        if(size.getContent() > 0) {
            this.size = size;
        }
    }

    public void addPercent(double percent) {
        this.percent += percent/part;
        updateJProgressBar();
    }

    private void updateJProgressBar(){
        progressBar.setValue((int) Math.ceil(percent));
    }

    public void mergingParts(){

        try (FileOutputStream out = new FileOutputStream(new File(saveTo.getAbsolutePath() + "/" + fileName + "."+format))) {

            for (int i = 0; i < part; i++) {
                FileInputStream in = new FileInputStream(new File("src/Download temp/" + fileName + "_" + i));

                byte[] buffer = new byte[4096];

                while (true) {

                    long read = in.read(buffer);

                    if (read == -1)
                        break;

                    out.write(buffer, 0, (int) read);

                }

                in.close();
                new File("src/Download temp/" + fileName + "_" + i).delete();

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
