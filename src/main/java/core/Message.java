package core;

import core.device.DataType;
import core.device.sensor.ISensor;

public class Message {
    private ISensor source;
    private DataType dataType;
    private String id;
    private double value;
    private long time;

    public Message(String id, double value, long time, ISensor source, DataType dataType) {
        this.id = id;
        this.value = value;
        this.time = time;
        this.source = source;
        this.dataType = dataType;
    }

    public DataType getDatatype() {
        return dataType;
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
