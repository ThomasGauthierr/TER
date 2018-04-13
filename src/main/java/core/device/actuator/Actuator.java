package core.device.actuator;

import core.behavior.contract.UnrespectedContractEvent;
import core.device.Device;
import gnu.io.SerialPort;

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
            //Sending the value to the actuator
            outputStream.write(("v" + Integer.toString(value) + "\n").getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        try {
            outputStream.write("c\n".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.close();
    }

    @Override
    public void unrespectedContractEventReceived(UnrespectedContractEvent evt) {
        System.out.println(ID + " : should be checking if able to repair");
        System.out.println("The event is " + evt.getActionType().name() + " and i have ??? action over the sensor " + evt.getSensor().getID());
    }
}
