package devices;

import devices.interfaces.IDevice;
import gnu.io.SerialPort;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class Device implements IDevice {
    protected String ID;
    protected SerialPort serialPort;
    protected OutputStream outputStream;
    protected InputStream inputStream;

    public Device() {
        ID = null;
        serialPort = null;
        outputStream = null;
        inputStream = null;
    }

    public Device(String ID, SerialPort serialPort, OutputStream outputStream, InputStream inputStream) {
        this.ID = ID;
        this.serialPort = serialPort;
        this.outputStream = outputStream;
        this.inputStream = inputStream;
    }

    public String getID() {
        return ID;
    }

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
