package main.java.devices;

import gnu.io.SerialPort;
import main.java.devices.interfaces.IActuator;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Actuator extends Device implements IActuator {
    public Actuator() {
        super();
    }

    public Actuator(String ID, SerialPort serialPort, OutputStream outputStream, InputStream inputStream) {
        super(ID, serialPort, outputStream, inputStream);
    }

    @Override
    public void sendValue(int value) {
        try {
            outputStream.write((Integer.toString(value) + "\n").getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
