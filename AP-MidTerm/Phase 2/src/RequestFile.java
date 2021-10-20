import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class RequestFile extends Thread {

    private HashMap<String , ArrayList<Request>> listRequests;

    private String fileListName;
    private String fileAddress;

    public RequestFile() {

        listRequests = new HashMap<>();

    }

    public void addList(String fileListName) {
        listRequests.put(fileListName ,new ArrayList<>());
    }

    public void addRequest(String listName ,Request request) {
        listRequests.get(listName).add(request);
    }

    /**
     * save a list of request with a thread
     */
    @Override
    public void run() {
        save();
    }

    /**
     * save a list of request
     */
    public void save() {

        try {

            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(new File(fileAddress)));
            out.writeObject(fileListName);
            out.writeObject(listRequests.get(fileListName));

            out.flush();
            out.close();

        } catch (IOException ignore) {
        }

    }

    /**
     * load a list of request
     */
    public void load() {

        try {

            System.out.println(fileAddress);
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(new File(fileAddress)));

            String string = (String) in.readObject();
            ArrayList<Request> arrayList = (ArrayList<Request>) in.readObject();

            in.close();

            listRequests.put(string,arrayList);

        } catch (IOException | ClassNotFoundException ignore) {
        }

    }

    /**
     *
     * @return the load request
     */
    public Request loadRequest() {

        StringBuilder stringBuilder = new StringBuilder(fileAddress);

        if (!stringBuilder.toString().endsWith(".obj")) {
            stringBuilder.append(".obj");
        }

        File file = new File(stringBuilder.toString());

        try {

            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
            Request request = ((Request) in.readObject());
            in.close();

            return request;

        } catch (IOException | ClassNotFoundException e){
            return null;
        }
    }

    /**
     *
     * @param fileListName the selected list name
     */
    public void showSelectedList(String fileListName) {

        try {
            for (Request request : listRequests.get(fileListName)) {
                System.out.println((listRequests.get(fileListName).indexOf(request) + 1) + ". URL: " + request.getUrlAddress() + " | method: " + request.getResponse().getMethod() + " | headers:" + request.toString());
            }
        } catch (NullPointerException ignored){}

    }

    /**
     * show all list name with there size
     */
    public void showAllList() {
        int index = 0;
        for (String string :listRequests.keySet()) {
            index++;
            System.out.println(index+". "+string+" size:"+listRequests.get(string).size());
        }
    }

    /**
     *
     * @return the list of all requests list
     */
    public HashMap<String, ArrayList<Request>> getListRequests() {
        return listRequests;
    }

    /**
     *
     * @param fileListName set the file list name
     */
    public void setFileListName(String fileListName) {
        this.fileListName = fileListName;
    }

    /**
     *
     * @param fileAddress set the file address
     */
    public void setFileAddress(String fileAddress) {
        this.fileAddress = fileAddress;
    }

    /**
     *
     * @return the file list name
     */
    public String getFileListName() {
        return fileListName;
    }
}
