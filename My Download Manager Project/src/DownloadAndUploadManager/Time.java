package DownloadAndUploadManager;

class Time {

    private int day;
    private int hour;
    private int min;
    private int second;

    public Time(int time) {

        day = time/86400;
        time %= 86400;
        hour = time/3600;
        time %= 3600;
        min = time/60;
        time %= 60;
        second = time;

    }

}

