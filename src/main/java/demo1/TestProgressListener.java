package demo1;

import org.apache.commons.fileupload.ProgressListener;

/**
 * Created by AlexY on 2016/6/27.
 */
public class TestProgressListener implements ProgressListener{


//    每次读取100KB的时候，就将读取过的字节数/100 赋值给num100ks
    private long num100ks = 0;

//     已经读取的字节
    private long theBytesRead = 0;

//    文件大小
    private long theContentLength = -1;


    private int whichItem = 0;

//    读取文件的百分比
    private int percentDone = 0;




    public void update(long bytesRead, long contentLength, int items) {

//        判断文件大小是否可知
        if ( contentLength > -1){
            contentLengthKnown = true;

        }

//        已经读取的字节
        theBytesRead = bytesRead;

//        文件的大小
        theContentLength = contentLength;

//        当前的FileItem对象
        whichItem = items;

        long nowNum100Ks = bytesRead / 100000;
//        每次读取100k才执行一次getMessage方法
        if ( nowNum100Ks > num100ks){


            num100ks = nowNum100Ks;

            if ( contentLengthKnown){

                percentDone = (int)Math.round(100.00 * bytesRead / contentLength);
            }

            System.out.println(getMessage());


        }



    }


    public String getMessage(){

        if (theContentLength == -1){

            return ""+ theBytesRead + " of Unknown-Total bytes have been read.";
        }else {
            return "" + theBytesRead + " of " + theContentLength + " bytes have been read ("+ percentDone + "% done).";
        }
    }






//    文件大小是否已知
    private boolean contentLengthKnown = false;

    public boolean isContentLengthKnown() {
        return contentLengthKnown;
    }

    public void setContentLengthKnown(boolean contentLengthKnown) {
        this.contentLengthKnown = contentLengthKnown;
    }

    public long getNum100ks() {
        return num100ks;
    }

    public void setNum100ks(long num100ks) {
        this.num100ks = num100ks;
    }

    public long getTheBytesRead() {
        return theBytesRead;
    }

    public void setTheBytesRead(long theBytesRead) {
        this.theBytesRead = theBytesRead;
    }

    public long getTheContentLength() {
        return theContentLength;
    }

    public void setTheContentLength(long theContentLength) {
        this.theContentLength = theContentLength;
    }

    public int getWhichItem() {
        return whichItem;
    }

    public void setWhichItem(int whichItem) {
        this.whichItem = whichItem;
    }

    public int getPercentDone() {
        return percentDone;
    }

    public void setPercentDone(int percentDone) {
        this.percentDone = percentDone;
    }




}
