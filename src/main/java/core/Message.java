package core;

import core.device.sensor.ISensor;

public class Message {
    private ISensor source;
    private String id;
    private double value;
    private long time;

    public Message(String id, double value, long time, ISensor source) {
        this.id = id;
        this.value = value;
        this.time = time;
        this.source = source;
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

    public ISensor getSource() {
        return source;
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
