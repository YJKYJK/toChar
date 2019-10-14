package yanyan.com.tochar.beans;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ThreadInfo {
    private String fileName;
    private Bitmap bitmap;
    private boolean isColor;
    private String txt;
    private Context context;
    private int width;
    private int heigth;


    private static ArrayList<String> textList=null;
    private  static HashMap<Integer,String> paths=null;

    /**
     * 初始化
     */
    public static void initMap(){
        paths=new HashMap<>();
        textList=new ArrayList<>();
    }

    public static ArrayList<String> getTextList() {
        return textList;
    }

    public synchronized static void  addText(String text) {
       textList.add(text);
    }

    /**
     * 根据list大小获取进度
     * @return
     */
    public synchronized static int getIndex(){
        return paths.size();
    }

    /**
     * 添加缓存路径
     * @param path
     */
    public synchronized static void setIndex(String path,String text){
       int start= path.lastIndexOf("/")+1;
       int end =path.indexOf("-",start);

       String index=path.substring(start,end);
       paths.put(Integer.parseInt(index),path);
        textList.add(text);
    }

    /**
     * 获取map
     * @return
     */
    public static HashMap<Integer,String> getMap(){
        return paths;
    }


    public ThreadInfo() {
    }

    public ThreadInfo(Context context,String fileName, Bitmap bitmap, boolean isColor, String txt) {
        this.context=context;
        this.fileName = fileName;
        this.bitmap = bitmap;
        this.isColor = isColor;
        this.txt = txt;
    }


    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public boolean isColor() {
        return isColor;
    }

    public void setColor(boolean color) {
        isColor = color;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeigth() {
        return heigth;
    }

    public void setHeigth(int heigth) {
        this.heigth = heigth;
    }
}
