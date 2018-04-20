package core.device;

import gnu.io.SerialPort;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Observable;

public abstract class Device extends Observable implements IDevice {
    protected String ID;
    protected SerialPort serialPort;
    protected OutputStream outputStream;
    protected InputStream inputStream;
    protected DataType dataType;

    public Device(String ID, SerialPort serialPort, OutputStream outputStream, InputStream inputStream, DataType dataType) {
        this.ID = ID;
        this.serialPort = serialPort;
        this.outputStream = outputStream;
        this.inputStream = inputStream;
        this.dataType = dataType;
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
