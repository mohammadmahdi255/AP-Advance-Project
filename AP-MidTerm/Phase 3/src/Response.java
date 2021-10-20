import com.sun.java.swing.plaf.windows.WindowsScrollBarUI;
import com.sun.java.swing.plaf.windows.WindowsScrollPaneUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.HierarchyBoundsListener;
import java.awt.event.HierarchyEvent;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.List;

/**
 * this class creat response for each request
 */
@SuppressWarnings("ALL")
public class Response implements Serializable , Runnable {

    private static final long serialVersionUID = 13445553L;

    private transient Theme theme;

    private String dateOfSendingRequest;

    private transient Handler handler;

    private transient JPanel panel;
    private transient JLabel[] labels;
    private transient JTabbedPane tabbedPane;
    private transient JPanel[] tabPanel;
    private transient ArrayList<JLabel> header;
    private transient ArrayList<JLabel> cookie;
    private transient JButton[] buttons;
    private transient JTextArea textArea1 = new JTextArea();
    private transient JTextArea textArea2 = new JTextArea();

    private String url;
    private String originalUrl;
    private String method;
    private int responseCode;
    private String responseStatus;
    private int bodyType;
    private String binaryFileAddress;
    private String json;

    private String[] saveValue;
    private String saveResponseBody;
    private ArrayList<String[]> saveFormBody;
    private ArrayList<String[]> saveQuery;
    private ArrayList<String[]> saveHeader;
    private HashMap<String ,String> saveHeaderResponse;
    private HashMap<String ,String> saveCookie;

    private transient HttpURLConnection urlConnection;

    private transient String boundary;
    private transient ArrayList<JPanel> headerTab;
    private transient ArrayList<JPanel> query;
    private transient ArrayList<JPanel> formBody;

    private int[] size;

    private transient String[] formats;

    /**
     * this is the constructor of Response class
     * @param theme the color theme
     * @param method the method name
     * @param bodyType the body type of request
     * @param url the URL address with query
     * @param originalUrl the prime URL address
     * @param header the header array list
     * @param query the query array list
     * @param formBodythe form body array list
     * @param binaryFileAddress the binary file address
     * @param json the json command string
     */
    public Response(Theme theme ,String method ,int bodyType ,String url ,String originalUrl ,ArrayList<JPanel> header ,ArrayList<JPanel> query ,ArrayList<JPanel> formBody ,String binaryFileAddress ,String json){

        this.theme = theme;

        handler = new Handler();
        panel = new JPanel();
        labels = new JLabel[3];
        tabbedPane = new JTabbedPane();
        tabPanel = new JPanel[4];
        buttons = new JButton[2];
        this.header = new ArrayList<>();
        cookie = new ArrayList<>();

        for (int i = 0; i < 3; i++) {

            labels[i] = new JLabel();
            labels[i].setOpaque(true);
            labels[i].setHorizontalAlignment(SwingConstants.CENTER);
            labels[i].setBorder(BorderFactory.createLineBorder(Color.GRAY));

            if (i != 2) {

                tabPanel[2*i] = new JPanel();
                tabPanel[2*i].setLayout(new BorderLayout(0,0));
                tabPanel[2*i].setBackground(Color.DARK_GRAY);
                tabPanel[2*i+1] = new JPanel();
                tabPanel[2*i+1].setLayout(new BorderLayout(0,0));
                tabPanel[2*i+1].setBackground(Color.DARK_GRAY);
                buttons[i] = new JButton();

            }

        }

        panel = new JPanel();
        panel.setBackground(theme.getTextFieldTheme()[2][0]);
        panel.setLayout(null);
        panel.add(labels[0]);
        panel.add(labels[1]);
        panel.add(labels[2]);

        tabbedPane.addTab("Preview",tabPanel[0]);
        tabbedPane.addTab("Header",tabPanel[1]);
        tabbedPane.addTab("Cookie",tabPanel[2]);
        tabbedPane.addTab("Timeline",tabPanel[3]);

        setDefaultTabPanel0();
        setDefaultTabPanel1();
        setDefaultTabPanel2();
        setDefaultTabPanel3();

        this.url = url;
        this.originalUrl = originalUrl;
        this.method = method;
        this.bodyType = bodyType;
        this.json = json;

        saveFormBody = new ArrayList<>();
        saveQuery = new ArrayList<>();
        saveHeader = new ArrayList<>();

        saveHeaderResponse = new HashMap<>();
        saveCookie = new HashMap<>();

        headerTab = header;
        this.query = query;
        this.formBody = formBody;
        this.binaryFileAddress = binaryFileAddress;

        formats = new String[]{"png","html","mkv","exe","jpg","pdf","zip","rar"};

        boundary = System.currentTimeMillis() + "";

        saveValue = new String[2];

    }

    /**
     * this is the seconde constructor of Response class
     * @param theme the color theme
     */
    public Response(Theme theme){
        this.theme = theme;
    }

    /**
     * send request with a thread
     */
    @Override
    public void run() {

        Date dateofSendRequest = new Date();

        dateOfSendingRequest = new JalaliCalendar().toString() + " " + dateofSendRequest.getHours() + ":" +dateofSendRequest.getMinutes() + ":" + dateofSendRequest.getSeconds();

        try {
            urlConnection = ((HttpURLConnection) new URL(url).openConnection());
            urlConnection.setRequestMethod(method);
            urlConnection.setReadTimeout(60000);
            urlConnection.setConnectTimeout(60000);
            setRequestSetting();
            setResponseHeader();
            showResponseInPreview();

            labels[1].setText(new Date().getTime() - dateofSendRequest.getTime() + " ms");

        } catch (NullPointerException | IOException ignore){

            labels[1].setText("0 ms");

            labels[2].setText("0 B");

        }

        saveData(headerTab , saveHeader);

        saveData(query, saveQuery);

        saveData(formBody, saveFormBody);

        setLabels(responseCode ,responseStatus ,labels[1].getText() ,labels[2].getText());

        try {
            ((JPanel) panel.getParent()).updateUI();
        } catch (NullPointerException | IllegalStateException ignore){}

        setLayout();

        saveResponseBody = textArea1.getText();

        for (int i = 0; i < cookie.size() - 1 ; i+=2) {
            saveCookie.put(cookie.get(i).getText() ,cookie.get(i+1).getText());
        }

        urlConnection.disconnect();

    }

    /**
     *
     * @param responseCode the response code of sending request
     * @param responseStatus the response status of sending request
     * @param s2 the connection time to mili secend in string
     * @param s3 the size of data recive by program to srting
     */
    public void setLabels(int responseCode ,String responseStatus,String s2 ,String s3) {

        labels[1].setText(s2);
        labels[2].setText(s3);

        for (int i = 0; i < 2 ; i++) {
            saveValue[i] = labels[i+1].getText();
        }

        if (Math.floorDiv(responseCode , 100) == 2) {
            labels[0].setBackground(new Color(0x5EE145));
        } else if (Math.floorDiv(responseCode , 100) == 3) {
            labels[0].setBackground(new Color(0x8776D5));
        }else if (Math.floorDiv(responseCode , 100) == 4) {
            labels[0].setBackground(new Color(0xEC8702));
        } else {
            labels[0].setBackground(new Color(0xE15251));
        }


        labels[0].setText(responseCode + " " + responseStatus);

        if (responseCode == 0) {
            labels[0].setText("Error");
        }

        labels[0].setBounds(10,10,8 * labels[0].getText().length(),25);
        labels[0].setForeground(Color.white);

        labels[1].setBounds(20 + labels[0].getWidth(),10,8 * labels[1].getText().length(),25);
        labels[1].setForeground(Color.GRAY);
        labels[1].setBackground(new Color(0xDADDD0));

        labels[2].setBounds(30 + labels[0].getWidth() + labels[1].getWidth(),10,8 * labels[2].getText().length(),25);
        labels[2].setForeground(Color.GRAY);
        labels[2].setBackground(new Color(0xDADDD0));

        this.responseCode = responseCode;
        this.responseStatus = responseStatus;

    }

    /**
     * this method set request setting
     * @throws IllegalStateException
     */
    private void setRequestSetting() throws IllegalStateException {

        try {
            for (int i = 0; i < headerTab.size() - 1; i++) {

                if (((JCheckBox) headerTab.get(i).getComponent(3)).isSelected()) {
                    String key = ((JTextField) headerTab.get(i).getComponent(0)).getText();
                    String value = ((JTextField) headerTab.get(i).getComponent(1)).getText();
                    if (key.equals("Content-Type") && value.equals("multipart/form-data")) {
                        urlConnection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
                    } else {
                        urlConnection.setRequestProperty(key, value);
                    }
                }

            }

        } catch (NullPointerException | IllegalStateException ignore){}

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
                    formUrlEncoded();
                } else if (bodyType == 2) {
                    json();
                } else if (bodyType == 3){
                    uploadBinary();
                }

                break;

        }

    }

    /**
     * this method save data for saveing request in file
     * @param arrayList a list of JPanel
     * @param saveArrayList a list of Strings
     */
    public void saveData(ArrayList<JPanel> arrayList, ArrayList<String[]> saveArrayList) {

        for (int i = 0; i < arrayList.size() - 1 ; i++) {

            String key = ((JTextField) arrayList.get(i).getComponent(0)).getText();
            String value = ((JTextField) arrayList.get(i).getComponent(1)).getText();

            saveArrayList.add(new String[]{
                    key + "",
                    fineIndexOfColor(arrayList.get(i).getComponent(0).getForeground()) + "",
                    value + "",
                    fineIndexOfColor(arrayList.get(i).getComponent(1).getForeground()) + "",
                    ((JCheckBox) arrayList.get(i).getComponent(3)).isSelected() + ""});

        }
    }

    /**
     * this method find the color of fourground
     * @param color the color of fourground
     * @return the index of theme
     */
    public int fineIndexOfColor(Color color) {
        for (int i = 0; i < 4 ; i++) {
            if (theme.getTextFieldTheme()[0][i].equals(color) || theme.getTextFieldTheme()[1][i].equals(color)) {
                return i;
            }
        }
        return 0;
    }

    /**
     * this method will set the header of request
     */
    public void setResponseHeader(){

        for (Map.Entry<String, List<String>> entry : urlConnection.getHeaderFields().entrySet()) {


            header.add(new JLabel(entry.getKey()));
            ((JScrollPane) tabPanel[1].getComponent(0)).add(header.get(header.size() - 1));

            StringBuilder stringBuilder = new StringBuilder();

            for (String string : entry.getValue()) {
                stringBuilder.append(string).append(" ");
            }

            if (entry.getKey() == null) {
                header.get(header.size() - 1).setText("Status");
                responseCode = Integer.parseInt(stringBuilder.toString().substring(9,12));
                responseStatus = stringBuilder.toString().substring(13);
            }

            header.add(new JLabel(stringBuilder.toString()));
            ((JScrollPane) tabPanel[1].getComponent(0)).add(header.get(header.size() - 1));

            if (entry.getKey() == null) {
                saveHeaderResponse.put("Status", stringBuilder.toString());
            } else {
                saveHeaderResponse.put(entry.getKey(), stringBuilder.toString());
            }

        }

    }

    /**
     * this method show response body in the preview
     * @throws IOException
     * @throws NullPointerException
     */
    public void showResponseInPreview() throws IOException , NullPointerException {

        StringBuilder fileAddress = new StringBuilder("src/temp/temp file");

        findFormat(fileAddress);

        BufferedInputStream in = new BufferedInputStream(urlConnection.getInputStream());

        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File(fileAddress.toString())));

        ArrayList<Integer> arrayList = new ArrayList();

        int b;
        while ((b = in.read()) != -1) {
            out.write(b);
            arrayList.add(b);
        }

        in.close();
        out.flush();
        out.close();

        size = new int[arrayList.size()];

        Thread thread = new Thread(() -> {

            for (int i = 0; i < size.length ; i++) {
                size[i] = arrayList.get(i).intValue();
            }

        });

        thread.start();


        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileAddress.toString()))));
        StringBuilder stringBuilder = new StringBuilder("");
        String readLine;

        while ((readLine = reader.readLine()) != null) {
            stringBuilder.append(readLine).append("\n");
        }

        textArea1.setText(String.valueOf(stringBuilder));

        reader.close();

        File file = new File(fileAddress.toString());

        labels[2].setText(file.length() + " B");

        if (file.exists()) {
            file.delete();
        }

    }

    /**
     * save the response body in the file
     * @param fileAddress file address of save
     */
    public void outPut(StringBuilder fileAddress) {

        try {

            findFormat(fileAddress);

            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File(fileAddress.toString())));

            for (int b : size) {
                out.write(b);
            }

            out.close();

        } catch (IOException ignore){}

    }

    /**
     * this method find format of response body file
     * @param fileAddress file address of save
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
     *
     * @return the date of creat in string form
     */
    public String getDateOfSendingRequest() {
        return dateOfSendingRequest;
    }

    /**
     *
     * @return the panel which contain all of North panel in the last request panel
     */
    public JPanel getPanel() {
        return panel;
    }

    /**
     *
     * @return the body type
     */
    public int getBodyType() {
        return bodyType;
    }

    /**
     *
     * @return the binary file address
     */
    public String getBinaryFileAddress() {
        return binaryFileAddress;
    }

    /**
     *
     * @return is the tab of response panel field
     */
    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    /**
     * set the tab 1 default form
     */
    private void setDefaultTabPanel0(){

        JScrollPane scrollPane = setTextAreaBackGround(textArea1);

        tabPanel[0].add(scrollPane ,BorderLayout.CENTER);
        tabPanel[0].getComponent(0).setBackground(theme.getBackGroundTheme()[2]);
        tabPanel[0].getComponent(0).setForeground(theme.getTextFieldTheme()[0][1]);

    }

    /**
     * this method set a text area in a JScrollPane
     * @param textArea the text area is going to set
     * @return the JScrollPane
     */
    private JScrollPane setTextAreaBackGround(JTextArea textArea) {
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setBackground(theme.getBackGroundTheme()[2]);
        textArea.setForeground(theme.getTextFieldTheme()[0][1]);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setUI(new WindowsScrollPaneUI());
        scrollPane.getHorizontalScrollBar().setUI(new WindowsScrollBarUI());
        scrollPane.getVerticalScrollBar().setUI(new WindowsScrollBarUI());

        return scrollPane;
    }

    /**
     * set the tab 2 default form
     */
    private void setDefaultTabPanel1(){

        JScrollPane scrollPane = setDefaultHeader(header ,0);

        buttons[0].setText("Copy to Clipboard");

        tabPanel[1].add(scrollPane ,BorderLayout.CENTER);
        tabPanel[1].setBorder(new EmptyBorder(20,20,20,20));

    }

    /**
     * set the tab 3 default form
     */
    private void setDefaultTabPanel2(){

        JScrollPane scrollPane = setDefaultHeader(cookie ,1);

        buttons[1].setText("Manage Cookies");

        tabPanel[2].add(scrollPane ,BorderLayout.CENTER);
        tabPanel[2].setBorder(new EmptyBorder(20,20,20,20));

    }

    /**
     * this method will set the header response and cookie
     * @param arrayList the Jlabel array list
     * @param index the index of array list
     * @return the JScrollPane
     */
    private JScrollPane setDefaultHeader(ArrayList<JLabel> arrayList ,int index) {

        arrayList.add(new JLabel("Name"));
        arrayList.add(new JLabel("Value"));

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBackground(theme.getBackGroundTheme()[2]);
        scrollPane.setUI(new WindowsScrollPaneUI());
        scrollPane.getHorizontalScrollBar().setUI(new WindowsScrollBarUI());
        scrollPane.getVerticalScrollBar().setUI(new WindowsScrollBarUI());
        scrollPane.addHierarchyBoundsListener(handler);
        scrollPane.setLayout(null);
        scrollPane.add(arrayList.get(0));
        scrollPane.add(arrayList.get(1));
        scrollPane.add(buttons[index]);

        arrayList.get(0).setBackground(theme.getLabelTheme()[3][0]);
        arrayList.get(0).setForeground(theme.getLabelTheme()[3][1]);
        arrayList.get(0).setBounds(0,0,335/2,30);

        arrayList.get(1).setBackground(theme.getLabelTheme()[3][0]);
        arrayList.get(1).setForeground(theme.getLabelTheme()[3][1]);
        arrayList.get(1).setBounds(335/2,0,335,30);

        buttons[index].setBounds(170,30,150,30);
        buttons[index].setBackground(theme.getButtonTheme()[0][0]);
        buttons[index].setForeground(theme.getButtonTheme()[0][1]);

        return scrollPane;

    }

    /**
     * set the tab 4 default form
     */
    private void setDefaultTabPanel3(){

        setTextAreaBackGround(textArea2);
        JScrollPane scrollPane = setTextAreaBackGround(textArea2);

        tabPanel[3].add(scrollPane ,BorderLayout.CENTER);
        tabPanel[3].getComponent(0).setBackground(theme.getBackGroundTheme()[2]);
        tabPanel[3].getComponent(0).setForeground(theme.getTextFieldTheme()[0][1]);

    }

    /**
     * set Color of them in the request and position of panels
     */
    public void setLayout(){

        for (JLabel label : header) {

            label.setOpaque(true);

            if (header.indexOf(label) % 2 == 0) {
                label.setBounds(0, 30 * (Math.floorDiv(header.indexOf(label), 2)), tabPanel[1].getWidth() / 2 - 20, 30);
            } else {
                label.setBounds(tabPanel[1].getWidth() / 2 - 20, 30 * (Math.floorDiv(header.indexOf(label), 2)), tabPanel[1].getWidth() / 2 - 20, 30);
            }

            setLabelBackGround(label, header);

        }

        for (JLabel label : cookie) {

            label.setOpaque(true);

            if (cookie.indexOf(label) % 2 == 0) {
                label.setBounds(0, 30 * (Math.floorDiv(cookie.indexOf(label), 2)), tabPanel[2].getWidth() / 2 - 20, 30);
            } else {
                label.setBounds(tabPanel[2].getWidth() / 2 - 20, 30 * (Math.floorDiv(cookie.indexOf(label), 2)), tabPanel[2].getWidth() / 2 - 20, 30);
            }

            setLabelBackGround(label, cookie);

        }

        buttons[0].setBounds(tabPanel[1].getWidth() - 190,30 * Math.floorDiv(header.size()+1,2) + 10,150,30);
        buttons[1].setBounds(tabPanel[2].getWidth() - 190,30 * Math.floorDiv(cookie.size()+1,2) + 10,150,30);

        setTheme();

    }

    /**
     * this method set theme
     */
    public void setTheme(){

        buttons[0].setBackground(theme.getButtonTheme()[0][0]);
        buttons[0].setForeground(theme.getButtonTheme()[0][1]);

        buttons[1].setBackground(theme.getButtonTheme()[0][0]);
        buttons[1].setForeground(theme.getButtonTheme()[0][1]);

        header.get(0).setBackground(theme.getLabelTheme()[3][0]);
        header.get(0).setForeground(theme.getLabelTheme()[3][1]);

        header.get(1).setBackground(theme.getLabelTheme()[3][0]);
        header.get(1).setForeground(theme.getLabelTheme()[3][1]);

        cookie.get(0).setBackground(theme.getLabelTheme()[3][0]);
        cookie.get(0).setForeground(theme.getLabelTheme()[3][1]);

        cookie.get(1).setBackground(theme.getLabelTheme()[3][0]);
        cookie.get(1).setForeground(theme.getLabelTheme()[3][1]);

        panel.setBackground(theme.getTextFieldTheme()[2][0]);

        textArea1.setBackground(theme.getBackGroundTheme()[2]);
        textArea1.setForeground(theme.getTextFieldTheme()[0][1]);

        tabPanel[1].setBackground(theme.getBackGroundTheme()[2]);
        tabPanel[1].getComponent(0).setBackground(theme.getBackGroundTheme()[2]);

        tabPanel[2].setBackground(theme.getBackGroundTheme()[2]);
        tabPanel[2].getComponent(0).setBackground(theme.getBackGroundTheme()[2]);

        textArea2.setBackground(theme.getBackGroundTheme()[2]);
        textArea2.setForeground(theme.getTextFieldTheme()[0][1]);

    }

    /**
     * this method will set the background of label
     * @param label the selected label
     * @param header the header of that label
     */
    private void setLabelBackGround(JLabel label, ArrayList<JLabel> header) {
        if(header.indexOf(label) > 1) {

            if ((Math.floorDiv(header.indexOf(label), 2) % 2 == 1)) {
                label.setBackground(theme.getLabelTheme()[3][0]);
            } else {
                label.setBackground(theme.getLabelTheme()[4][0]);
            }

            label.setForeground(theme.getLabelTheme()[3][2]);

        }
    }

    /**
     * form data method
     */
    public void formData() {
        try {
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(urlConnection.getOutputStream());
            for (int i = 0; i < formBody.size() - 1; i++) {

                if(((JCheckBox) formBody.get(i).getComponent(3)).isSelected()) {

                    String key = null;
                    String value = null;

                    if (formBody.get(i).getComponent(0).getForeground().equals(theme.getTextFieldTheme()[0][2])) {
                        key = ((JTextField) formBody.get(i).getComponent(0)).getText();
                    }
                    if(formBody.get(i).getComponent(1).getForeground().equals(theme.getTextFieldTheme()[0][2])) {
                        value = ((JTextField) formBody.get(i).getComponent(1)).getText();
                    }

                    if (key != null || value != null) {
                        if(key == null){
                            key = "";
                        } else if(value == null) {
                            value = "";
                        }
                    }

                    bufferedOutputStream.write(("--" + boundary + "\r\n").getBytes());
                    assert value != null;
                    File file = new File(value);
                    if (file.exists()) {
                        bufferedOutputStream.write(("Content-Disposition: form-data; name=\"" + key + "\"filename=\"" + (new File(value)).getName() + "\"\r\nContent-Type: application/octet-stream\r\n\r\n").getBytes());
                        try {
                            BufferedInputStream tempBufferedInputStream = new BufferedInputStream(new FileInputStream(new File(value)));
                            byte[] filesBytes = new byte[4096];

                            while (tempBufferedInputStream.available() > 0) {
                                int count = tempBufferedInputStream.read(filesBytes);
                                bufferedOutputStream.write(filesBytes, 0, count);
                            }

                            tempBufferedInputStream.close();
                            bufferedOutputStream.write("\r\n".getBytes());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        bufferedOutputStream.write(("Content-Disposition: form-data; name=\"" + key + "\"\r\n\r\n").getBytes());
                        bufferedOutputStream.write((value + "\r\n").getBytes());
                    }

                }
            }
            bufferedOutputStream.write(("--" + boundary + "--\r\n").getBytes());
            bufferedOutputStream.flush();
            bufferedOutputStream.close();

        } catch (Exception ignored) {}
    }

    /**
     * form urlencoded method
     */
    public void formUrlEncoded() {

        try {
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(urlConnection.getOutputStream());

            StringBuilder stringBuilder = new StringBuilder();

            for (int i = 0 , j = 0; i < formBody.size() - 1; i++) {

                if (((JCheckBox) formBody.get(i).getComponent(3)).isSelected()) {

                    String key = null;
                    String value = null;

                    if (formBody.get(i).getComponent(0).getForeground().equals(theme.getTextFieldTheme()[0][2])) {
                        key = ((JTextField) formBody.get(i).getComponent(0)).getText();
                    }
                    if (formBody.get(i).getComponent(1).getForeground().equals(theme.getTextFieldTheme()[0][2])) {
                        value = ((JTextField) formBody.get(i).getComponent(1)).getText();
                    }

                    if (value == null) {
                        value = "";
                    }

                    if (j != 0) {
                        stringBuilder.append("&");
                    }

                    if (key != null) {
                        stringBuilder.append(key).append("=").append(value);
                        j++;
                    }

                }
            }
            bufferedOutputStream.write(stringBuilder.toString().getBytes());
            bufferedOutputStream.flush();
            bufferedOutputStream.close();

        } catch (Exception ignore){}

    }

    /**
     * binary file upload method
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
     * json method
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
     * @param bodyResponse the response body data
     */
    public void setTextArea1Text(String bodyResponse) {
        textArea1.setText(bodyResponse);
    }

    /**
     *
     * @param header set the header array list
     */
    public void setHeader(ArrayList<JLabel> header) {
        this.header = header;
    }

    /**
     *
     * @param cookie set the cookie array list
     */
    public void setCookie(ArrayList<JLabel> cookie) {
        this.cookie = cookie;
    }

    /**
     *
     * @param saveHeaderResponse set save header response
     */
    public void setSaveHeaderResponse(HashMap<String, String> saveHeaderResponse) {
        this.saveHeaderResponse = saveHeaderResponse;
    }

    /**
     *
     * @param saveValue set the save value of time and recive data size
     */
    public void setSaveValue(String[] saveValue) {
        this.saveValue = saveValue;
    }

    /**
     *
     * @param saveQuery set the save query
     */
    public void setSaveQuery(ArrayList<String[]> saveQuery) {
        this.saveQuery = saveQuery;
    }

    /**
     *
     * @param saveFormBody set the save form body
     */
    public void setSaveFormBody(ArrayList<String[]> saveFormBody) {
        this.saveFormBody = saveFormBody;
    }

    /**
     *
     * @param saveResponseBody set the save response body
     */
    public void setSaveResponseBody(String saveResponseBody) {
        this.saveResponseBody = saveResponseBody;
    }

    /**
     *
     * @param saveHeader set the save header
     */
    public void setSaveHeader(ArrayList<String[]> saveHeader) {
        this.saveHeader = saveHeader;
    }

    /**
     *
     * @param saveCookie set the save cookie
     */
    public void setSaveCookie(HashMap<String, String> saveCookie) {
        this.saveCookie = saveCookie;
    }

    /**
     *
     * @param dateOfSendingRequest set the date of sending request
     */
    public void setDateOfSendingRequest(String dateOfSendingRequest) {
        this.dateOfSendingRequest = dateOfSendingRequest;
    }

    /**
     *
     * @param size set the size of response body
     */
    public void setSize(int[] size) {
        this.size = size;
    }

    /**
     *
     * @param bodyType set the body type
     */
    public void setBodyType(int bodyType) {
        this.bodyType = bodyType;
    }

    /**
     *
     * @return the response body data
     */
    public int[] getSize() {
        return size;
    }

    /**
     *
     * @return the header
     */
    public ArrayList<JLabel> getHeader() {
        return header;
    }

    /**
     *
     * @return the cookie
     */
    public ArrayList<JLabel> getCookie() {
        return cookie;
    }

    /**
     *
     * @return the tab panel
     */
    public JPanel[] getTabPanel() {
        return tabPanel;
    }

    /**
     *
     * @return the url with query
     */
    public String getUrl() {
        return url;
    }

    /**
     *
     * @return the orginal url
     */
    public String getOriginalUrl() {
        return originalUrl;
    }

    /**
     *
     * @return the method type
     */
    public String getMethod() {
        return method;
    }

    /**
     *
     * @return the form body
     */
    public ArrayList<JPanel> getFormBody() {
        return formBody;
    }

    /**
     *
     * @return the query
     */
    public ArrayList<JPanel> getQuery() {
        return query;
    }

    /**
     *
     * @return the header tab array list
     */
    public ArrayList<JPanel> getHeaderTab() {
        return headerTab;
    }

    /**
     *
     * @return the save cookie
     */
    public HashMap<String, String> getSaveCookie() {
        return saveCookie;
    }

    /**
     *
     * @return the save response
     */
    public HashMap<String, String> getSaveHeaderResponse() {
        return saveHeaderResponse;
    }

    /**
     *
     * @return the save value
     */
    public String[] getSaveValue() {
        return saveValue;
    }

    /**
     *
     * @return the save response body
     */
    public String getSaveResponseBody() {
        return saveResponseBody;
    }

    /**
     *
     * @return the json command string
     */
    public String getJson() {
        return json;
    }

    /**
     *
     * @return the save query
     */
    public ArrayList<String[]> getSaveQuery() {
        return saveQuery;
    }

    /**
     *
     * @return the save form body
     */
    public ArrayList<String[]> getSaveFormBody() {
        return saveFormBody;
    }

    /**
     *
     * @return the save header
     */
    public ArrayList<String[]> getSaveHeader() {
        return saveHeader;
    }

    /**
     *
     * @return the response code
     */
    public int getResponseCode() {
        return responseCode;
    }

    /**
     *
     * @return the response status
     */
    public String getResponseStatus() {
        return responseStatus;
    }


    /**
     * an inner class which control events
     */
    private class Handler implements HierarchyBoundsListener{

        @Override
        public void ancestorMoved(HierarchyEvent e) {

        }

        @Override
        public void ancestorResized(HierarchyEvent e) {
            setLayout();
        }
    }

}