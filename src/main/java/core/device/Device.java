package core.device;

import gnu.io.SerialPort;

import java.io.IOException;
import java.util.Observable;

public abstract class Device extends Observable implements IDevice {
    private String ID;
    private SerialPort serialPort;

    public Device(String ID, SerialPort serialPort) {
        this.ID = ID;
        this.serialPort = serialPort;
    }

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public SerialPort getSerialPort() {
        return serialPort;
    }

    @Override
    public void writeString(String s) {
        try {
            getSerialPort().getOutputStream().write((s + "\n").getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
