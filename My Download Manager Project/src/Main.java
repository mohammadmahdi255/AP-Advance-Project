import DownloadAndUploadManager.Download;
import GUI.StartDownloadGUI;

public class Main {

    public static void main(String[] args) {

//        Thread thread = new Thread(new Download("amin", "https://aut.ac.ir/files/site1/yekta_program/contents_images/5571.jpg", "D:\\", 8,""));
//        thread.start();

        Thread thread = new Thread(StartDownloadGUI::new);
        thread.start();

    }

}
