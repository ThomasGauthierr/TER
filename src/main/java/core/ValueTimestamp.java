package core;

public class ValueTimestamp {
    private int value;
    private long time;

    public ValueTimestamp(int value, long time) {
        this.value = value;
        this.time = time;
    }

    public int getValue() {
        return value;
    }

    public long getTime() {
        return time;
    }

}
