package yanyan.com.tochar.beans;

import android.graphics.Bitmap;

public class ToChanrBean {
    private String text;
    private Bitmap bitmap;

    public ToChanrBean(String text, Bitmap bitmap) {
        this.text = text;
        this.bitmap = bitmap;
    }

    public ToChanrBean() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
