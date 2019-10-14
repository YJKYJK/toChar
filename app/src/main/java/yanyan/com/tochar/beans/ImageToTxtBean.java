package yanyan.com.tochar.beans;

import java.util.List;

public class ImageToTxtBean{
    private boolean isGif;
    private Object img;
    private List<String> textList;


    public ImageToTxtBean() {
    }

    public List<String> getTextList() {
        return textList;
    }

    public void setTextList(List<String> textList) {
        this.textList = textList;
    }

    public ImageToTxtBean(boolean isGif, Object img) {
        this.isGif = isGif;
        this.img = img;
    }

    public boolean isGif() {
        return isGif;
    }

    public void setGif(boolean gif) {
        isGif = gif;
    }

    public Object getImg() {
        return img;
    }

    public void setImg(Object img) {
        this.img = img;
    }




}
