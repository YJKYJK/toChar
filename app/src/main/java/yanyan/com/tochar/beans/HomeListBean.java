package yanyan.com.tochar.beans;

import android.app.Activity;
import android.graphics.Color;

public class HomeListBean {
    private Integer image;
    private String title;
    private String text;
    private Integer color;
    private Activity activity;

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public HomeListBean(){
        color=Color.WHITE;
    }

    public HomeListBean(String title, String text, int color) {
        this.title = title;
        this.text = text;
        this.color = color;
    }

    public HomeListBean(String title, String text, Integer color, Activity activity) {
        this.title = title;
        this.text = text;
        this.color = color;
        this.activity = activity;
    }

    public HomeListBean(Integer image, String title, String text, Integer color, Activity activity) {
        this.image = image;
        this.title = title;
        this.text = text;
        this.color = color;
        this.activity = activity;
    }

    public Integer getColor() {
        return color;
    }

    public void setColor(Integer color) {
        this.color = color;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
