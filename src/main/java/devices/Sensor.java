package main.java.devices;

import gnu.io.SerialPort;
import main.java.devices.interfaces.ISensor;
import main.java.utils.Utils;

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
        Integer value = null;

        try {
            outputStream.write("value\n".getBytes());

            while (inputStream.available() > 0) {
                while (inputStream.available() == 0) {
                    //waiting to get the sensor value
                }

                //ToDo : Is the second while necessary ?
                while (inputStream.available() > 0) {
                    value = Integer.parseInt(Utils.getStringFromInputStream(inputStream));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return value;
    }

}
