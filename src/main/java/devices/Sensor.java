package devices;

import devices.interfaces.ISensor;
import gnu.io.SerialPort;
import utils.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Sensor extends Device implements ISensor {

    public Sensor(String ID, SerialPort serialPort) {
        super(ID, serialPort);
    }

    @Override
    public Integer getValue() throws IOException {
        Integer value = 0;

            //Asking the sensor to send the value
            getOutputStream().write("v\n".getBytes());

            InputStream inputStream = getInputStream();
            while (inputStream.available() == 0) {
                //waiting the sensor to reply
            }

            //Reading the value
            value = Integer.parseInt(Utils.getStringFromInputStream(inputStream));

        return value;
    }

}
