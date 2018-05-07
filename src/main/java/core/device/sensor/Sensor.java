package core.device.sensor;

import com.google.common.collect.EvictingQueue;
import core.Message;
import core.device.DataType;
import core.device.Device;
import core.utils.Utils;
import gnu.io.SerialPort;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

public class Sensor extends Observable implements ISensor {

    Queue<Message> queue;
    int bufferSize;
    protected String ID;
    protected SerialPort serialPort;
    protected OutputStream outputStream;
    protected InputStream inputStream;
    protected DataType dataType;

    public Sensor(String ID, SerialPort serialPort, OutputStream outputStream, InputStream inputStream, int bufferSize, DataType dataType) {
        this.bufferSize = bufferSize;
        this.ID = ID;
        this.serialPort = serialPort;
        this.outputStream = outputStream;
        this.inputStream = inputStream;
        this.dataType = dataType;
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

        queue = EvictingQueue.create(bufferSize);
        for(String strValueTimestamp : values){
            queue.add(
                    new Message(strValueTimestamp.split(" ")[0],
                            Integer.parseInt(strValueTimestamp.split(" ")[1]),
                            Long.parseLong(strValueTimestamp.split(" ")[2])
                    )
            );
        }

        setChanged();
        notifyObservers(this);
    }

    @Override
    public List<Message> getData() {
        return new LinkedList<>(queue);
    }

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public DataType getDataType() { return dataType; }

    @Override
    public SerialPort getSerialPort() {
        return serialPort;
    }

    @Override
    public OutputStream getOutputStream() {
        return outputStream;
    }

    @Override
    public InputStream getInputStream() {
        return inputStream;
    }

    @Override
    public void close() {
        try {
            outputStream.close();
            inputStream.close();
            serialPort.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writeString(String s) {
        try {
            outputStream.write((s + "\n").getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writeInt(int i) {
        writeString(Integer.toString(i));
    }

}
