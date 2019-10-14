package yanyan.com.tochar.beans;

import java.util.List;

public class MoreImgeToChar {
    private String gifPath;
    private List<String> texts;

    public MoreImgeToChar(String gifPath, List<String> texts) {
        this.gifPath = gifPath;
        this.texts = texts;
    }

    public String getGifPath() {
        return gifPath;
    }

    public void setGifPath(String gifPath) {
        this.gifPath = gifPath;
    }

    public List<String> getTexts() {
        return texts;
    }

    public void setTexts(List<String> texts) {
        this.texts = texts;
    }
}
