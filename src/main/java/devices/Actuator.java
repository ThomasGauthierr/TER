package devices;

import devices.interfaces.IActuator;
import gnu.io.SerialPort;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Actuator extends Device implements IActuator {

    public Actuator(String ID, SerialPort serialPort) {
        super(ID, serialPort);
    }

    @Override
    public void sendValue(int value) throws IOException {

        getOutputStream().write(("v" + Integer.toString(value) + "\n").getBytes());

    }
}
