package core;

public class Message {
	private String id;
    private double value;
    private long time;

    public Message(String id, double value, long time) {
        this.id = id;
        this.value = value;
        this.time = time;
    }

    public double getValue() {
        return value;
    }

    public long getTime() {
        return time;
    }
    
    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "ValueTimestamp{" +
                "id=" + id +
                ", value=" + value +
                ", time=" + time +
                '}';
    }
}
