package main.java.devices;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;
import main.java.devices.interfaces.IDevice;

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
}