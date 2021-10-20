import java.io.*;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.*;

public class Response implements Serializable {

    private static final long serialVersionUID = 1123L;

    private String method;

    private transient HttpURLConnection urlConnection;

    private String[] formats;

    private String boundary;
    private ArrayList<String[]> formBody;

    private String sendRequestDate;
    private boolean saveResponseBody;
    private boolean showResponseHeader;

    private int bodyType;

    private String binaryFileAddress;
    private String json;

    /**
     *
     * @param method the method of request
     * @param formBody the data of form data and urlencoded
     * @param bodyType type of sending request
     * @param binaryFileAddress the address of binary request
     * @param json the string of json file
     */
    public Response (String method ,ArrayList<String[]> formBody ,int bodyType ,String binaryFileAddress ,String json){

        if (method == null) {
            this.method = "GET";
        } else {
            this.method = method;
        }

        if (formBody == null) {
            this.formBody = new ArrayList<>();
        } else {
            this.formBody = formBody;
        }

        this.bodyType = bodyType;
        this.binaryFileAddress = binaryFileAddress;
        this.json = json;

        formats = new String[]{"png","html","mkv","exe","jpg","pdf","zip","rar"};

        boundary = System.currentTimeMillis() + "";

    }

    /**
     *
     * @return the Http URL Connection
     */
    public HttpURLConnection getUrlConnection() {
        return urlConnection;
    }

    /**
     *
     * @param url set url from request to this response
     * @param header set header of request to this response
     */
    public void setHttpSetting(URL url , HashMap<String ,String> header) {

        Date date = new Date();
        JalaliCalendar jalaliCalendar = new JalaliCalendar();
        sendRequestDate = jalaliCalendar.toString() + " " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();

        try {
            urlConnection = ((HttpURLConnection) url.openConnection());
            urlConnection.setConnectTimeout(60000);
            urlConnection.setReadTimeout(60000);
            setRequestMethod();
            setRequestHeader(header);
        } catch (NullPointerException | IOException ignore){}
    }

    /**
     * set the request method to Http URL Connection
     */
    private void setRequestMethod() {

        try {
            urlConnection.setRequestMethod(method);
        } catch (ProtocolException ignore){}

    }

    /**
     *
     * @param header set the header of request for response
     */
    private void setRequestHeader(HashMap<String ,String> header) {

        try {
            for (String key : header.keySet()) {
                System.out.println(key+"-:-"+header.get(key));
                if (key.equals("Content-Type") && header.get(key).equals("multipart/form-data")) {
                    urlConnection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
                } else {
                    urlConnection.setRequestProperty(key, header.get(key));
                }
            }
        } catch (NullPointerException ignore) {
        }

        switch (method) {
            case "Get":

                urlConnection.setDoInput(true);
                urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
                urlConnection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

                break;
            case "DELETE":

                urlConnection.setDoInput(true);

                break;
            case "POST":
            case "PUT":

                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                if (bodyType == 0) {
                    formData();
                } else if (bodyType == 1) {
                    urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    formUrlEncoded();
                } else if (bodyType == 2) {
                    json();
                } else if (bodyType == 3) {
                    uploadBinary();
                }

        }
    }

    /**
     * show response header in console
     */
    public void showResponseHeader (){

        System.out.println("\n"+ urlConnection.getRequestMethod() + " URL : " + urlConnection.getURL());

        for (Map.Entry<String, List<String>> entry : urlConnection.getHeaderFields().entrySet()) {
            if (entry.getKey() != null) {
                System.out.print(entry.getKey() + " : ");
            }
            for (String string : entry.getValue()) {
                System.out.print(string + " ");
            }
            System.out.println();
        }

    }

    /**
     * show response body in console
     */
    public void showResponse() {

        try {

            StringBuilder fileAddress = new StringBuilder("src/temp/temp file");

            findFormat(fileAddress);

            BufferedInputStream in = new BufferedInputStream(urlConnection.getInputStream());

            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File(fileAddress.toString())));

            copy(in,out);

            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(String.valueOf(fileAddress)))));
            String string;

            while ((string = reader.readLine()) != null) {
                System.out.println(string);
            }

            reader.close();

        } catch (IOException ignore){}

    }

    /**
     * find the fomat of the response body for save
     * @param fileAddress the address of file
     */
    private void findFormat(StringBuilder fileAddress) {
        try {
            for (String string : formats) {

                if (urlConnection.getHeaderField("Content-Type").contains(string)) {
                    fileAddress.append(".").append(string);
                    break;
                } else if (formats[7].equals(string)) {
                    fileAddress.append(".txt");
                    break;
                }

            }
        } catch (NullPointerException e){
            fileAddress.append(".html");
        }
    }

    /**
     * save response body in a file
     * @param saveResponseBodyAddress save response body address
     */
    public void outPut (String saveResponseBodyAddress) {

        try {

            StringBuilder fileAddress = new StringBuilder(saveResponseBodyAddress);

            File file = new File(fileAddress.toString());

            if (file.isDirectory()) {
                Request.setSaveAddress(fileAddress, sendRequestDate);
            } else if (!fileAddress.toString().contains("\\\\") && !fileAddress.toString().contains("/") && !fileAddress.toString().equals("")) {
                fileAddress = new StringBuilder("src/response body/").append(fileAddress.toString());
            } else if (fileAddress.toString().equals("") || !new File(file.getParent()).isDirectory()) {
                fileAddress = new StringBuilder("src/response body/");
                Request.setSaveAddress(fileAddress, sendRequestDate);
            }


            findFormat(fileAddress);

            StringBuilder stringBuilder = new StringBuilder("src/temp/temp file");

            findFormat(stringBuilder);

            BufferedInputStream in = new BufferedInputStream(new FileInputStream(new File(stringBuilder.toString())));

            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File(fileAddress.toString())));

            copy(in, out);

            file = new File(String.valueOf(stringBuilder));
            file.delete();

        } catch (IOException ignore) {
        }

    }

    /**
     * copy a file in other file
     * @param in the buffered input stream file
     * @param out the buffered output stream file
     * @throws IOException input output exception
     */
    private void copy(BufferedInputStream in ,BufferedOutputStream out) throws IOException{
        int b;
        while ((b = in.read()) != -1) {
            out.write(b);
        }

        in.close();
        out.flush();
        out.close();
    }

    /**
     * send form data in request
     */
    public void formData() {
        try {
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(urlConnection.getOutputStream());
            for (String[] string : formBody) {
                bufferedOutputStream.write(("--" + boundary + "\r\n").getBytes());
                File file = new File(string[0]);
                if (file.exists()) {
                    bufferedOutputStream.write(("Content-Disposition: form-data; filename=\"" + (new File(string[1])).getName() + "\"\r\nContent-Type: Auto\r\n\r\n").getBytes());
                    try {
                        BufferedInputStream tempBufferedInputStream = new BufferedInputStream(new FileInputStream(new File(string[1])));
                        byte[] filesBytes = new byte[4096];

                        while (tempBufferedInputStream.available() > 0) {
                            int count = tempBufferedInputStream.read(filesBytes);
                            bufferedOutputStream.write(filesBytes,0,count);
                        }

                        tempBufferedInputStream.close();
                        bufferedOutputStream.write("\r\n".getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    bufferedOutputStream.write(("Content-Disposition: form-data; name=\"" + string[0] + "\"\r\n\r\n").getBytes());
                    bufferedOutputStream.write((string[1] + "\r\n").getBytes());
                }
            }
            bufferedOutputStream.write(("--" + boundary + "--\r\n").getBytes());
            bufferedOutputStream.flush();
            bufferedOutputStream.close();

        } catch (Exception ignored) {}
    }

    /**
     * send url encoded in request
     */
    public void formUrlEncoded() {

        try {
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(urlConnection.getOutputStream());

            StringBuilder stringBuilder = new StringBuilder();

            int j = 0;

            for (String[] string : formBody) {

                    if (j != 0) {
                        stringBuilder.append("&");
                    }

                    if (string[0] != null) {
                        stringBuilder.append(string[0]).append("=").append(string[1]);
                        j++;
                    }
            }
            bufferedOutputStream.write(stringBuilder.toString().getBytes());
            bufferedOutputStream.flush();
            bufferedOutputStream.close();

        } catch (Exception ignore){}

    }

    /**
     * upload binary file in request
     */
    public void uploadBinary() {

        try {

            File file = new File(binaryFileAddress);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(urlConnection.getOutputStream());
            BufferedInputStream fileInputStream = new BufferedInputStream(new FileInputStream(file));

            int b;
            while ((b = fileInputStream.read()) > -1) {
                bufferedOutputStream.write(b);
            }

            bufferedOutputStream.flush();
            bufferedOutputStream.close();

        } catch (IOException | NullPointerException ignore) {}

    }

    /**
     * send json request
     */
    public void json() {

        try {
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(urlConnection.getOutputStream());

            bufferedOutputStream.write(json.getBytes());
            bufferedOutputStream.flush();
            bufferedOutputStream.close();

        } catch (Exception ignore){}

    }

    /**
     *
     * @param saveResponseBody set saveResponseBody boolean
     */
    public void setSaveResponseBody(boolean saveResponseBody) {
        this.saveResponseBody = saveResponseBody;
    }

    /**
     *
     * @param showResponseHeader set showResponseHeader boolean
     */
    public void setShowResponseHeader(boolean showResponseHeader) {
        this.showResponseHeader = showResponseHeader;
    }

    /**
     *
     * @return the value of saveResponseBody
     */
    public boolean isSaveResponseBody() {
        return saveResponseBody;
    }

    /**
     *
     * @return the value of showResponseHeader
     */
    public boolean isShowResponseHeader() {
        return showResponseHeader;
    }

    /**
     *
     * @return the method type
     */
    public String getMethod() {
        return method;
    }

}
