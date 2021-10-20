import java.io.IOException;
import java.util.*;

public class ConsoleReader {

    private Scanner scanner;
    private String command;

    private RequestFile requestFile;

    private Request request;
    private HashMap<String, String> header;
    private ArrayList<String[]> formBody;
    private boolean saveResponseBody;
    private boolean showResponseHeader;
    private boolean saveRequest;

    private int bodyType;
    private String binaryFileAddress;
    private String json;

    private String saveResponseBodyAddress;

    /**
     * constructor of ConsoleReader
     */
    public ConsoleReader() {

        scanner = new Scanner(System.in);

        requestFile = new RequestFile();

        saveResponseBody = false;
        showResponseHeader = false;
        saveRequest = false;

    }

    /**
     * run the console command field
     */
    public void Console() {

        while (true) {

            bodyType = 4;
            header = new HashMap<>();
            formBody = new ArrayList<>();

            command = scanner.nextLine();
            readCommand();

        }

    }

    /**
     * read the command that write in console
     */
    private void readCommand() {

        try {

            ArrayList<String> splitCommand = new ArrayList<>();

            String method = "GET";

            {
                ArrayList<String> temp = new ArrayList<>(Arrays.asList(command.split(" ")));
                Iterator<String> it = temp.iterator();
                while (it.hasNext()) {
                    StringBuilder string = new StringBuilder(it.next());
                    if (string.toString().startsWith("\"") && !string.toString().endsWith("\"")) {
                        while (!string.toString().endsWith("\"")) {
                            string.append(" ").append(it.next());
                        }
                    }
                    splitCommand.add(string.toString());
                }

            }

            if (splitCommand.get(0).equals("jurl")) {

                splitCommand.remove(0);

                String[] allMethod = new String[]{"GET", "POST", "PUT", "PATCH", "DELETE"};

                if (!splitCommand.get(0).equals("list")
                        && !splitCommand.get(0).equals("fire")
                        && !splitCommand.get(0).equals("creat")
                        && !splitCommand.get(0).equals("save")
                        && !splitCommand.get(0).equals("load")
                        && !splitCommand.get(0).equals("remove")
                        && !splitCommand.get(0).equals("-load")) {

                    if (command.contains("-h") || command.contains(" --help ")) {
                        System.out.println("-O --output \"file name or file address\" save response in a file");
                        System.out.println("-i show response body");
                        System.out.println("-M --method methodtype the Default method is GET");
                        System.out.println("-H --header \"key1:value1;key2:value2;...\"");
                        System.out.println("-d --data \"key1=value1&key2=value2&...\"");
                        System.out.println("--data-urlencoded \"key1=value1&key2=value2&...\"");
                        System.out.println("-j -json \"{key1:value1,key2:value2,...}\"");
                        System.out.println("--upload \"file address\"");
                        System.out.println("-S --save \"file name or file address\" save request in a file");
                        System.out.println("-save \"request list name\" save the request in the request list you can use (\") in start and the end of list name if list name have space between words in name list");
                        System.out.println("list \"request list name\" show you all the list file name with there size if request list name is empty");
                        System.out.println("creat \"request list name to creat\" you can use (\") in start and the end of list name if list name have space between words in name list");
                        System.out.println("save \"request list name\" \"file address\" save the selected request list");
                        System.out.println("load \"file address of request list\"");
                        System.out.println("remove \"request list name\" \"request index\" remove the selected index of request list if request list is empty the request list will be remove");
                        System.out.println("-load \"file address of request\" -save \"request list name\" load a save request and if tou want to save it in a list file use -save and give the request list name");
                        System.out.println("-h --help show all commands in this program");
                        return;
                    }

                    Iterator<String> it = splitCommand.iterator();
                    while (it.hasNext()) {
                        String string = it.next();
                        if (string.contains("http://") || string.contains("https://") || string.contains(".com") || string.contains("www.") || string.contains(".ir") || string.contains(".net")) {
                            request = new Request(string);
                            it.remove();
                            break;
                        } else if (!it.hasNext()) {
                            request = new Request(splitCommand.get(0));
                            splitCommand.remove(0);
                            break;
                        }
                    }

                    try {
                        if (request.getUrlAddress() == null) {
                            return;
                        }
                    } catch (NullPointerException ignored) {
                        System.out.println("no URL specified!");
                        System.out.println("try --help or -h to see more information");
                        return;
                    }

                    it = splitCommand.iterator();

                    String string = "";
                    if (it.hasNext()) {
                        string = it.next();
                    }

                    label:
                    do {

                        switch (string) {
                            case "-O":
                            case "--output":
                                saveResponseBody = true;
                                it.remove();
                                if (it.hasNext()) {
                                    string = it.next();
                                } else {
                                    break label;
                                }

                                if (!string.startsWith("-")) {
                                    if (string.startsWith("\"") && string.endsWith("\"")) {

                                        saveResponseBodyAddress = string.substring(1, string.length() - 1);

                                    } else {
                                        System.out.println("-O or --output parameter is invalid");
                                        System.out.println("try --help or -h to see more information");
                                        return;
                                    }
                                } else {
                                    System.out.println("-O or --output requires parameter");
                                    System.out.println("try --help or -h to see more information");
                                    return;
                                }
                                it.remove();
                                if (it.hasNext()) {
                                    string = it.next();
                                } else {
                                    break label;
                                }
                                break;
                            case "-i":
                                showResponseHeader = true;
                                it.remove();
                                if (it.hasNext()) {
                                    string = it.next();
                                } else {
                                    break label;
                                }
                                break;
                            case "-M":
                            case "--method":

                                it.remove();
                                if (it.hasNext()) {
                                    string = it.next();
                                } else {
                                    break label;
                                }

                                if (!string.startsWith("-")) {
                                    for (String m : allMethod) {
                                        if (string.equals(m)) {
                                            method = string;
                                            it.remove();
                                            if (it.hasNext()) {
                                                string = it.next();
                                            } else {
                                                break label;
                                            }
                                            break;
                                        } else if (m.equals("DELETE")) {
                                            System.out.println("-M or --method parameter is invalid");
                                            System.out.println("try --help or -h to see more information");
                                            return;
                                        }
                                    }
                                } else {
                                    System.out.println("-M or --method requires parameter");
                                    System.out.println("try --help or -h to see more information");
                                    return;
                                }

                                break;
                            case "-H":
                            case "--header":

                                it.remove();
                                if (it.hasNext()) {
                                    string = it.next();
                                } else {
                                    break label;
                                }
                                header = new HashMap<>();

                                if (!string.startsWith("-") && !string.equals("")) {
                                    if (string.startsWith("\"") && string.endsWith("\"")) {

                                        String[] keyValue = string.substring(1, string.length() - 1).split(";");

                                        for (String s : keyValue) {
                                            String[] temp = s.split(":");
                                            if (temp.length == 2) {
                                                header.put(temp[0], temp[1]);
                                            } else {
                                                System.out.println("-H or --header is badly used here");
                                                System.out.println("try --help or -h to see more information");
                                                return;
                                            }
                                        }

                                        it.remove();
                                        if (it.hasNext()) {
                                            string = it.next();
                                        } else {
                                            break label;
                                        }

                                    } else {
                                        System.out.println("-H or --header is badly used here");
                                        System.out.println("try --help or -h to see more information");
                                        return;
                                    }
                                } else {
                                    System.out.println("-H or --header requires parameter");
                                    System.out.println("try --help or -h to see more information");
                                    return;
                                }
                                break;
                            case "-d":
                            case "--data":
                                it.remove();
                                if (it.hasNext()) {
                                    string = it.next();
                                } else {
                                    break label;
                                }

                                if (bodyType == 4) {

                                    bodyType = 0;

                                    if (!string.startsWith("-") && !string.equals("")) {
                                        if (string.startsWith("\"") && string.endsWith("\"")) {

                                            String[] keyValue = string.substring(1, string.length() - 1).split("&");

                                            for (String s : keyValue) {
                                                String[] temp = s.split("=");
                                                if (temp.length == 2) {
                                                    formBody.add(new String[]{temp[0], temp[1]});
                                                } else if (s.endsWith("=") && temp.length == 1 && !temp[0].equals("")) {
                                                    formBody.add(new String[]{temp[0], ""});
                                                } else if (!s.contains("=") && temp.length != 0) {
                                                    System.out.println("-d or --data is badly used here");
                                                    System.out.println("try --help or -h to see more information");
                                                    return;
                                                }
                                            }

                                            it.remove();
                                            if (it.hasNext()) {
                                                string = it.next();
                                            } else {
                                                break label;
                                            }

                                        } else {
                                            System.out.println("-d or --data is badly used here");
                                            System.out.println("try --help or -h to see more information");
                                            return;
                                        }

                                    } else {
                                        System.out.println("-d or --data is requires parameter");
                                        System.out.println("try --help or -h to see more information");
                                        return;
                                    }

                                } else if (!string.startsWith("-")) {
                                    it.remove();
                                    if (it.hasNext()) {
                                        string = it.next();
                                    } else {
                                        break label;
                                    }
                                }

                                break;
                            case "--data-urlencoded":

                                it.remove();
                                if (it.hasNext()) {
                                    string = it.next();
                                } else {
                                    break label;
                                }

                                if (bodyType == 4) {

                                    bodyType = 1;

                                    if (!string.startsWith("-") && !string.equals("")) {
                                        if (string.startsWith("\"") && string.endsWith("\"")) {

                                            String[] keyValue = string.substring(1, string.length() - 1).split("&");

                                            for (String s : keyValue) {
                                                String[] temp = s.split("=");
                                                if (temp.length == 2) {
                                                    formBody.add(new String[]{temp[0], temp[1]});
                                                } else if (s.contains("=") && temp.length == 1) {
                                                    formBody.add(new String[]{temp[0], ""});
                                                } else if (!s.contains("=") && temp.length != 0) {
                                                    System.out.println("--data-urlencoded is badly used here");
                                                    System.out.println("try --help or -h to see more information");
                                                    return;
                                                }
                                            }

                                            it.remove();
                                            if (it.hasNext()) {
                                                string = it.next();
                                            } else {
                                                break label;
                                            }

                                        } else {
                                            System.out.println("--data-urlencoded is badly used here");
                                            System.out.println("try --help or -h to see more information");
                                            return;
                                        }

                                    } else {
                                        System.out.println("--data-urlencoded is requires parameter");
                                        System.out.println("try --help or -h to see more information");
                                        return;
                                    }

                                } else if (!string.startsWith("-")) {
                                    it.remove();
                                    if (it.hasNext()) {
                                        string = it.next();
                                    } else {
                                        break label;
                                    }
                                }

                                break;
                            case "-j":
                            case "-json":
                                it.remove();
                                if (it.hasNext()) {
                                    string = it.next();
                                } else {
                                    break label;
                                }

                                if (bodyType == 4) {

                                    bodyType = 2;

                                    if (!string.startsWith("-") && !string.equals("")) {
                                        if (string.startsWith("\"{") && string.endsWith("}\"")) {

                                            String[] keyValue = string.substring(2, string.length() - 2).split(",");
                                            StringBuilder stringBuilder = new StringBuilder("{");

                                            for (String s : keyValue) {
                                                String[] temp = s.split(":");
                                                if (temp.length == 2) {
                                                    if (!s.equals(keyValue[0])) {
                                                        stringBuilder.append(",");
                                                    }
                                                    stringBuilder.append("\"").append(temp[0]).append("\":\"").append(temp[1]).append("\"");
                                                } else if (s.contains(":") && temp.length == 1) {
                                                    if (!s.equals(keyValue[0])) {
                                                        stringBuilder.append(",");
                                                    }
                                                    stringBuilder.append("\"").append(temp[0]).append("\":\"").append("\"");
                                                } else if (s.contains(":") && temp.length == 0) {
                                                    if (!s.equals(keyValue[0])) {
                                                        stringBuilder.append(",");
                                                    }
                                                    stringBuilder.append("\"").append("\":\"").append("\"");
                                                } else {
                                                    System.out.println("-j or -json is badly used here");
                                                    System.out.println("try --help or -h to see more information");
                                                    return;
                                                }
                                            }

                                            stringBuilder.append("}");

                                            json = stringBuilder.toString();

                                            it.remove();
                                            if (it.hasNext()) {
                                                string = it.next();
                                            } else {
                                                break label;
                                            }

                                        } else {
                                            System.out.println("-j or -json is requires parameter");
                                            System.out.println("try --help or -h to see more information");
                                            return;
                                        }

                                    } else {
                                        System.out.println("-j or -json is requires parameter");
                                        System.out.println("try --help or -h to see more information");
                                        return;
                                    }
                                } else if (!string.startsWith("-")) {
                                    it.remove();
                                    if (it.hasNext()) {
                                        string = it.next();
                                    } else {
                                        break label;
                                    }
                                }

                                break;
                            case "--upload":
                                it.remove();
                                if (it.hasNext()) {
                                    string = it.next();
                                } else {
                                    break label;
                                }

                                if (bodyType == 4) {

                                    bodyType = 3;

                                    if (!string.startsWith("-") && !string.equals("")) {
                                        if (string.startsWith("\"") && string.endsWith("\"")) {

                                            binaryFileAddress = string.substring(1, string.length() - 1);

                                            it.remove();
                                            if (it.hasNext()) {
                                                string = it.next();
                                            } else {
                                                break label;
                                            }

                                        } else {
                                            System.out.println("--upload is requires parameter");
                                            System.out.println("try --help or -h to see more information");
                                            return;
                                        }
                                    } else {
                                        System.out.println("--upload is requires parameter");
                                        System.out.println("try --help or -h to see more information");
                                        return;
                                    }
                                } else if (!string.startsWith("-")) {
                                    it.remove();
                                    if (it.hasNext()) {
                                        string = it.next();
                                    } else {
                                        break label;
                                    }
                                }

                                break;
                            case "-S":
                            case "--save":
                                it.remove();
                                if (it.hasNext()) {
                                    string = it.next();
                                } else {
                                    break label;
                                }

                                if (!string.startsWith("-")) {
                                    if (string.startsWith("\"") && string.endsWith("\"")) {

                                        request.setFileAddress(string.substring(1, string.length() - 1));
                                        saveRequest = true;

                                    } else {
                                        System.out.println("-S or --save parameter is invalid");
                                        System.out.println("try --help or -h to see more information");
                                        return;
                                    }
                                } else {
                                    System.out.println("-S or --save requires parameter");
                                    System.out.println("try --help or -h to see more information");
                                    return;
                                }

                                it.remove();
                                if (it.hasNext()) {
                                    string = it.next();
                                } else {
                                    break label;
                                }

                                break;
                            case "-save":
                                it.remove();
                                if (it.hasNext()) {
                                    string = it.next();
                                } else {
                                    System.out.println("-save needs a file name");
                                    break label;
                                }

                                if (string.startsWith("\"") && string.endsWith("\"")) {
                                    string = string.substring(1, string.length() - 1);
                                }

                                if (requestFile.getListRequests().get(string) != null) {
                                    requestFile.addRequest(string, request);
                                } else {
                                    System.out.println("file with this name dont exist");
                                    return;
                                }
                                it.remove();
                                if (it.hasNext()) {
                                    string = it.next();
                                } else {
                                    break label;
                                }
                                break;
                            default:
                                System.out.println("Command Not Found");
                                System.out.println("try --help or -h to see more information");
                                return;
                        }

                    } while (it.hasNext());

                    request.sendRequestSetting(method, formBody, bodyType, binaryFileAddress, json);

                    request.setHeader(header);

                    request.getResponse().setShowResponseHeader(showResponseHeader);
                    request.getResponse().setSaveResponseBody(saveResponseBody);

                    request.setSaveRequest(saveRequest);

                    request.setSaveResponseBodyAddress(saveResponseBodyAddress);

                    if (saveRequest) {
                        request.start();
                        saveRequest = false;
                    }

                    request.getResponse().setHttpSetting(request.getUrl(), request.getHeader());

                    try {
                        request.getResponse().getUrlConnection().connect();
                    } catch (IOException ignore) {
                    }


                    if (showResponseHeader) {
                        request.getResponse().showResponseHeader();
                        showResponseHeader = false;
                    }

                    request.getResponse().showResponse();

                    if (saveResponseBody) {
                        request.getResponse().outPut(saveResponseBodyAddress);
                        saveResponseBody = false;
                    }

                    request.getResponse().getUrlConnection().disconnect();

                } else {

                    switch (splitCommand.get(0)) {
                        case "list":
                            if (splitCommand.size() == 2) {
                                findFileListName(splitCommand);
                                if (requestFile.getListRequests().get(requestFile.getFileListName()) != null) {
                                    requestFile.showSelectedList(requestFile.getFileListName());
                                } else {
                                    System.out.println("there is no file with this name");
                                }
                            } else if (splitCommand.size() == 1) {
//                                findFileListName(splitCommand);
                                requestFile.showAllList();
                            } else {
                                System.out.println("list is badly used here");
                                System.out.println("try --help or -h to see more information");
                                return;
                            }
                            break;
                        case "fire":
                            if (splitCommand.size() > 2) {
                                findFileListName(splitCommand);
                                if (requestFile.getListRequests().get(requestFile.getFileListName()) != null) {
                                    for (int i = 2; i < splitCommand.size(); i++) {
                                        try {
                                            int index = Integer.parseInt(splitCommand.get(i));
                                            if (0 < index && index <= requestFile.getListRequests().get(splitCommand.get(1)).size()) {

                                                if (requestFile.getListRequests().get(requestFile.getFileListName()).get(index - 1).isSaveRequest()) {
                                                    requestFile.getListRequests().get(requestFile.getFileListName()).get(index - 1).start();
                                                }

                                                requestFile.getListRequests().get(requestFile.getFileListName()).get(index - 1).getResponse().setHttpSetting(
                                                        requestFile.getListRequests().get(requestFile.getFileListName()).get(index - 1).getUrl(),
                                                        requestFile.getListRequests().get(requestFile.getFileListName()).get(index - 1).getHeader());

                                                try {
                                                    requestFile.getListRequests().get(requestFile.getFileListName()).get(index - 1).getResponse().getUrlConnection().connect();
                                                } catch (IOException ignore) {
                                                }

                                                if (requestFile.getListRequests().get(requestFile.getFileListName()).get(index - 1).getResponse().isShowResponseHeader()) {
                                                    requestFile.getListRequests().get(requestFile.getFileListName()).get(index - 1).getResponse().showResponseHeader();
                                                }

                                                requestFile.getListRequests().get(requestFile.getFileListName()).get(index - 1).getResponse().showResponse();

                                                if (requestFile.getListRequests().get(requestFile.getFileListName()).get(index - 1).getResponse().isSaveResponseBody()) {
                                                    requestFile.getListRequests().get(requestFile.getFileListName()).get(index - 1).getResponse().outPut(requestFile.getListRequests().get(requestFile.getFileListName()).get(index - 1).getSaveResponseBodyAddress());
                                                }

                                                requestFile.getListRequests().get(requestFile.getFileListName()).get(index - 1).getResponse().getUrlConnection().disconnect();

                                            } else {
                                                System.out.println("there is no request with index:" + index + " in the file:" + splitCommand.get(1));
                                            }
                                        } catch (NumberFormatException ignore) {
                                        }
                                    }
                                } else {
                                    System.out.println("there is no file with this name");
                                }
                            } else {
                                System.out.println("fire is badly used here");
                                System.out.println("try --help or -h to see more information");
                                return;
                            }
                            break;
                        case "creat":
                            if (splitCommand.size() == 2) {
                                findFileListName(splitCommand);
                                requestFile.addList(requestFile.getFileListName());
                            } else {
                                System.out.println("creat is badly used here");
                                System.out.println("try --help or -h to see more information");
                                return;
                            }
                            break;
                        case "remove":
                            if (splitCommand.size() > 1) {
                                findFileListName(splitCommand);
                                if (requestFile.getListRequests().get(requestFile.getFileListName()) != null) {
                                    if (splitCommand.size() == 2) {
                                        requestFile.getListRequests().remove(requestFile.getFileListName());

                                    } else if (splitCommand.size() > 2) {
                                        for (int i = 2; i < splitCommand.size(); i++) {
                                            try {
                                                int index = Integer.parseInt(splitCommand.get(i));
                                                if (0 < index && index <= requestFile.getListRequests().get(splitCommand.get(1)).size()) {
                                                    requestFile.getListRequests().get(requestFile.getFileListName()).remove(index - 1);
                                                } else {
                                                    System.out.println("there is no request with index:" + index + " in the file:" + splitCommand.get(1));
                                                }
                                            } catch (NumberFormatException ignore) {
                                            }
                                        }
                                    }
                                } else {
                                    System.out.println("there is no file with this name");
                                }
                            } else {
                                System.out.println("load is badly used here");
                                System.out.println("try --help or -h to see more information");
                                return;
                            }
                            break;
                        case "save":
                            if (splitCommand.size() == 3) {
                                findFileListName(splitCommand);
                                requestFile.save();
                            } else {
                                System.out.println("save is badly used here");
                                System.out.println("try --help or -h to see more information");
                                return;
                            }
                            break;
                        case "load":
                            if (splitCommand.size() == 2) {
                                findFileListName(splitCommand);
                                requestFile.setFileAddress(requestFile.getFileListName());
                                requestFile.load();

                            } else {
                                System.out.println("load is badly used here");
                                System.out.println("try --help or -h to see more information");
                                return;
                            }
                            break;
                        case "-load":
                            if (splitCommand.size() > 1) {
                                if (splitCommand.size() == 2) {
                                    findFileListName(splitCommand);
                                    requestFile.setFileAddress(requestFile.getFileListName());
                                    Request request = requestFile.loadRequest();

                                    sendRequest(request);

                                } else if (splitCommand.size() == 4) {
                                    findFileListName(splitCommand);
                                    requestFile.setFileAddress(requestFile.getFileListName());
                                    Request request = requestFile.loadRequest();

                                    if (splitCommand.get(2).equals("-save")) {
                                        if (requestFile.getListRequests().get(splitCommand.get(3)) != null) {
                                            String string = splitCommand.get(3);
                                            if (string.startsWith("\"") && string.endsWith("\"")) {
                                                string = string.substring(1, string.length() - 1);
                                            }
                                            requestFile.addRequest(string, request);
                                        } else {
                                            System.out.println("file with this name dont exist");
                                            return;
                                        }
                                    } else {
                                        System.out.println("Command Not Found");
                                        System.out.println("try --help or -h to see more information");
                                        return;
                                    }

                                    sendRequest(request);

                                } else {

                                    if (splitCommand.size() == 3 && splitCommand.get(2).equals("-save")) {
                                        System.out.println("-save is badly used here");
                                        System.out.println("try --help or -h to see more information");
                                        return;
                                    }

                                    System.out.println("-load is badly used here");
                                    System.out.println("try --help or -h to see more information");
                                    return;
                                }
                            } else {
                                System.out.println("-load is badly used here");
                                System.out.println("try --help or -h to see more information");
                                return;
                            }
                            break;
                        default:
                            System.out.println("Command Not Found");
                            System.out.println("try --help or -h to see more information");
                            break;
                    }
                }

            } else {
                System.out.println("Command Not Found");
                System.out.println("try --help or -h to see more information");
            }

        } catch (NullPointerException e) {
            System.out.println("Command Not Found");
            System.out.println("try --help or -h to see more information");
        }

    }

    /**
     *
     * @param request sent the given request and give the response
     */
    private void sendRequest(Request request) {
        if (request.isSaveRequest()) {
            request.start();
        }

        request.getResponse().setHttpSetting(request.getUrl(), request.getHeader());

        try {
            request.getResponse().getUrlConnection().connect();
        } catch (IOException ignore) {}

        if (request.getResponse().isShowResponseHeader()) {
            request.getResponse().showResponseHeader();
        }

        request.getResponse().showResponse();

        if (request.getResponse().isSaveResponseBody()) {
            request.getResponse().outPut(request.getSaveResponseBodyAddress());
        }

        request.getResponse().getUrlConnection().disconnect();
    }

    /**
     *
     * @param splitCommand find address file and file list name in command
     */
    private void findFileListName(ArrayList<String> splitCommand) {
        if (splitCommand.get(1).startsWith("\"") && splitCommand.get(1).endsWith("\"")) {
            requestFile.setFileListName(splitCommand.get(1).substring(1,splitCommand.get(1).length() - 1));
        } else {
            requestFile.setFileListName(splitCommand.get(1));
        }
        if (splitCommand.size() == 3) {
            try {
                requestFile.setFileAddress(splitCommand.get(2).substring(1, splitCommand.get(2).length() - 1));
            } catch (StringIndexOutOfBoundsException ignored){}
        }
    }


}
