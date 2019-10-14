package yanyan.com.tochar.beans;

import java.util.List;

public class Page {
    private int size;
    private int index;
    private List<String> testList;


    public Page(int index,int size , List<String> testList) {
        this.size = size;
        this.index = index;
        this.testList = testList;
    }

    public Page() {
    }

    public int getSize() {
        return testList.size();
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public List<String> getTestList() {
        return testList;
    }

    public void setTestList(List<String> testList) {
        this.testList = testList;
    }
}
