package core.device.sensor;

import com.google.common.collect.EvictingQueue;
import core.Message;
import core.behavior.context.IContext;
import core.device.DataType;
import core.device.Device;
import core.utils.Utils;
import gnu.io.SerialPort;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Vector;

public class Sensor extends Device implements ISensor {

    protected Queue<Message> queue;
    protected int bufferSize;
    private DataType dataType;
    private List<IContext> listeners;

    public Sensor(String ID, SerialPort sp, int bufferSize, DataType dataType) {
        super(ID, sp);
        this.bufferSize = bufferSize;
        this.dataType = dataType;
        this.queue = EvictingQueue.create(bufferSize);
        this.listeners = new Vector<>();
    }


    @Override
    public void collect() {
        String[] values = new String[0];
        try {
            values = Utils.getStringFromInputStream(this.getSerialPort().getInputStream()).split(",");
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(values[0].length() == 0){
            return;
        }

        queue.clear();
        for(String strValueTimestamp : values){
            queue.add(
                    new Message(strValueTimestamp.split(" ")[0],
                            Integer.parseInt(strValueTimestamp.split(" ")[1]),
                            Long.parseLong(strValueTimestamp.split(" ")[2])
                    )
            );
        }

        notifyListeners(this, getData());
    }

    @Override
    public List<Message> getData() {
        return new LinkedList<>(queue);
    }

    @Override
    public DataType getDataType() {
        return this.dataType;
    }

    @Override
    public void addListener(IContext context) {
        this.listeners.add(context);
    }

    @Override
    public void notifyListeners(ISensor sensor, List<Message> messages) {
        System.out.println("[ISensor](" + this.getID() + ") updated data");
        listeners.forEach(c -> c.update(sensor, messages));
    }

    @Override
    public String toString() {
        return "Sensor{" +
                "id=" + getID() +
                ", dataType=" + dataType +
                '}';
    }
}
