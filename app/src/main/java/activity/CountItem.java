package activity;

public class CountItem {

    private int Count;
    private int index;

    public int getCount() {
        return Count+1;
    }

    public void setCount(int count) {
        Count = count;
    }


    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
