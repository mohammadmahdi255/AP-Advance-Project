package DownloadAndUploadManager;

class Size {

    private long content;
    private double size;
    private String unit;
    private int power;

    public Size(long content){
        unitSize(content);
    }

    public long getContent() {
        return content;
    }

    public double getSize() {
        return size;
    }

    public String getUnit() {
        return unit;
    }

    public int getPower() {
        return power;
    }

    public void addSize(long content){
        unitSize(this.content+content);
    }

    public void unitSize(long content){

        int count = 0;
        this.content = content;

        while (content > 1024){
            content /= 1024;
            count++;
        }

        power = count * 3;
        size = (double) this.content / Math.pow(2,power) + this.content % Math.pow(2,power);

        switch (count){

            case 0:
                unit = "B";
                break;
            case 1:
                unit = "KB";
                break;
            case 2:
                unit = "MB";
                break;
            case 3:
                unit = "GB";
                break;
            case 4:
                unit = "TB";
                break;
            case 5:
                unit = "PB";
                break;
            case 6:
                unit = "EB";
                break;
            case 7:
                unit = "ZB";
                break;
            case 8:
                unit = "YB";
                break;

        }

    }

}
