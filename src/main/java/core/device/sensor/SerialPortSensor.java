package core.device.sensor;

import core.Message;
import core.device.DataType;
import core.device.SerialPortDevice;
import gnu.io.SerialPort;

import java.util.List;
import java.util.TooManyListenersException;
import java.util.Vector;

public class SerialPortSensor extends SerialPortDevice implements ISensor {

    private DataType dataType;
    private Message lastMessage;
    private List<ISensorObserver> observers;
    private Integer lastSentValue;

    public SerialPortSensor(String identifier, SerialPort serialPort, int bufferSize, DataType dataType) throws TooManyListenersException {
        super(identifier, serialPort);

        this.dataType = dataType;
        observers = new Vector<>();
        setReadDataWhenAvailable(true);

    }

    @Override
    protected void onDataAvailable(String data) {
        String[] values = data.split(" ");

        if (values.length != 3)
            return;

        // System.out.println("Received data : " + data);

        lastMessage = new Message(values[0], Double.valueOf(values[1]), Long.valueOf(values[2]), this, dataType);
        notifyObservers();
        //if (lastSentValue == null || lastSentValue + 10 < Integer.valueOf(values[1]) || lastSentValue - 10 > Integer.valueOf(values[1])) {
        //    lastSentValue = Integer.valueOf(values[1]);
            helper.insertSensorMeasurement(this, Integer.valueOf(values[1]));
        //    System.out.println("sent : " + lastSentValue);
        //}
    }

    @Override
    public DataType getDataType() {
        return dataType;
    }

    @Override
    public Message getLastMessage() {
        return lastMessage;
    }

    @Override
    public void notifyObservers() {
        observers.forEach(o -> o.update(this, lastMessage));
    }

    @Override
    public void addObserver(ISensorObserver observer) {
        this.observers.add(observer);
    }

    @Override
    public void removeObserver(ISensorObserver observer) {
        this.observers.remove(observer);
    }
}
