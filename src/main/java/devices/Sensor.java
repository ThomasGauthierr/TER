package devices;

import devices.interfaces.ISensor;
import gnu.io.SerialPort;
import utils.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Sensor extends Device implements ISensor {
    public Sensor() {
        super();
    }

    public Sensor(String ID, SerialPort serialPort, OutputStream outputStream, InputStream inputStream) {
        super(ID, serialPort, outputStream, inputStream);
    }

    @Override
    public Integer getValue() {
        Integer value = 0;

        try {
            //Asking the sensor to send the value
            outputStream.write("v\n".getBytes());

            while (inputStream.available() == 0) {
                //waiting the sensor to reply
            }

            //Reading the value
            value = Integer.parseInt(Utils.getStringFromInputStream(inputStream));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return value;
    }

}
