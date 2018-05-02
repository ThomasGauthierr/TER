package core.device.sensor;

import com.google.common.collect.EvictingQueue;
import core.Message;
import core.device.DataType;
import core.device.Device;
import gnu.io.SerialPort;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Queue;

public class FakeSensor extends Device implements ISensor {

    private Queue<Message> queue;

    public FakeSensor(String ID, SerialPort serialPort, OutputStream outputStream, InputStream inputStream, int bufferSize, DataType dataType) {
        super(ID, serialPort, outputStream, inputStream, dataType);
        queue = EvictingQueue.create(bufferSize);
    }

    @Override
    public void collect() {

    }

    @Override
    public List<Message> getData() {
        return null;
    }

    @Override
    public String getID() {
        return null;
    }

    @Override
    public DataType getDataType() {
        return null;
    }

    @Override
    public SerialPort getSerialPort() {
        return null;
    }

    @Override
    public OutputStream getOutputStream() {
        return null;
    }

    @Override
    public InputStream getInputStream() {
        return null;
    }

    @Override
    public void close() {

    }

    @Override
    public void writeString(String s) {

    }

    @Override
    public void writeInt(int i) {

    }
}
