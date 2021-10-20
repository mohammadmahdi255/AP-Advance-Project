import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Request extends Thread implements Serializable {

    private static final long serialVersionUID = 1434L;

    private String urlAddress;
    private URL url;
    private String creatDate;
    private HashMap<String ,String> header;

    private String fileAddress;

    private String saveResponseBodyAddress;

    private boolean saveRequest;

    private Response response;

    /**
     * constructor
     * @param urlAddress url to send request
     */
    public Request (String urlAddress) {

        Date date = new Date();

        JalaliCalendar jalaliCalendar = new JalaliCalendar();

        creatDate = jalaliCalendar.toString() + " " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();

        this.urlAddress = urlAddress;

        response = null;

        try {
            url = new URL(urlAddress);
        } catch (MalformedURLException ignored){}

    }

    /**
     *
     * @param method the method of request
     * @param formBody the data of form data and urlencoded
     * @param bodyType type of sending request
     * @param binaryFileAddress the address of binary request
     * @param json the string of json file
     */
    public void sendRequestSetting (String method ,ArrayList<String[]> formBody , int bodyType , String binaryFileAddress , String json){
        response = new Response(method ,formBody ,bodyType ,binaryFileAddress ,json);
    }

    /**
     *
     * @return the response data
     */
    public Response getResponse() {
        return response;
    }

    /**
     *
     * @return the url
     */
    public URL getUrl() {
        return url;
    }

    /**
     *
     * @return the header of request
     */
    public HashMap<String, String> getHeader() {
        return header;
    }

    /**
     *
     * @param header set the header of request
     */
    public void setHeader(HashMap<String, String> header) {
        this.header = header;
    }

    /**
     * save file with a thread
     */
    @Override
    public void run(){
        save();
    }

    /**
     * save the request in  a file
     */
    public void save(){

        File file = new File(fileAddress);

        StringBuilder stringBuilder = new StringBuilder(fileAddress);

            if (file.isDirectory()) {
                setSaveAddress(stringBuilder, creatDate);
            } else if (!stringBuilder.toString().contains("\\\\") && !stringBuilder.toString().contains("/") && !stringBuilder.toString().equals("")) {
                stringBuilder = new StringBuilder("src/request/").append(stringBuilder.toString());
            } else if (stringBuilder.toString().equals("") || !new File(file.getParent()).isDirectory()) {
                stringBuilder = new StringBuilder("src/request/");
                setSaveAddress(stringBuilder, creatDate);
            }

        stringBuilder.append(".obj");

        System.out.println(stringBuilder.toString());

        file = new File(stringBuilder.toString());

        try {

            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(this);
            out.close();

        } catch (IOException ignore){}

    }

    /**
     *
     * @param stringBuilder an address file to build
     * @param creatDate the date of creating this request
     */
    public static void setSaveAddress(StringBuilder stringBuilder, String creatDate) {
        if (!stringBuilder.toString().endsWith("\\") && !stringBuilder.toString().endsWith("//")) {
            stringBuilder.append("/");
        }
        stringBuilder.append("output_[");
        for (int i = 0; i < creatDate.split(":").length; i++) {
            stringBuilder.append(creatDate.split(":")[i]).append(" ");
        }
        stringBuilder.append("]");
    }

    /**
     *
     * @return the url address
     */
    public String getUrlAddress() {
        return urlAddress;
    }

    /**
     *
     * @param fileAddress set the save address file
     */
    public void setFileAddress(String fileAddress) {
        this.fileAddress = fileAddress;
    }

    /**
     *
     * @return the saveResponseBodyAddress
     */
    public String getSaveResponseBodyAddress() {
        return saveResponseBodyAddress;
    }

    /**
     *
     * @param saveResponseBodyAddress set the saveResponseBodyAddress
     */
    public void setSaveResponseBodyAddress(String saveResponseBodyAddress) {
        this.saveResponseBodyAddress = saveResponseBodyAddress;
    }

    /**
     *
     * @return the value of saveRequest
     */
    public boolean isSaveRequest() {
        return saveRequest;
    }

    /**
     *
     * @param saveRequest set the saveRequest
     */
    public void setSaveRequest(boolean saveRequest) {
        this.saveRequest = saveRequest;
    }

    /**
     *
     * @return the header string
     */
    @Override
    public String toString(){
        StringBuilder string = new StringBuilder();
        try {
            for (String s : header.keySet()) {
                string.append(string).append(" ").append(s).append(": ").append(header.get(s));
            }
        } catch (NullPointerException ignored){}

        return string.toString();

    }

}
