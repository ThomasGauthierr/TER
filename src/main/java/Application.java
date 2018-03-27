package main.java;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import main.java.devices.Actuator;
import main.java.devices.Sensor;
import main.java.devices.interfaces.IActuator;
import main.java.devices.interfaces.IDevice;
import main.java.devices.interfaces.ISensor;
import main.java.utils.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.concurrent.TimeUnit;

public class Application {
    private static ArrayList<ISensor> sensors = new ArrayList<>();
    private static ArrayList<IActuator> actuators = new ArrayList<>();

    private static final String IDMessage = "i\n";

    private static final String portNames[] = {
            //ToDo : Handle every port on all OS
            "COM3",
            "COM4",
            "COM5"
    };

    private void init() {
        CommPortIdentifier portID;
        Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

        while (portEnum.hasMoreElements()) {
            CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();

            for (String portName : portNames) {
                if (currPortId.getName().equals(portName)) {
                    SerialPort currSerialPort;
                    OutputStream outputStream;
                    InputStream inputStream;

                    portID = currPortId;
                    currSerialPort = Utils.initSerialPort(portID, this.getClass());
                    inputStream = Utils.openInputStreams(currSerialPort);
                    outputStream = Utils.openOutputStreams(currSerialPort);

                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    try {
                        outputStream.write(IDMessage.getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try {
                        while (inputStream.available() == 0) {
                            //waiting to get the sensor value
                        }

                        while (inputStream.available() > 0) {
                            String ID = Utils.getStringFromInputStream(inputStream);
                            if (ID.contains("sensor")) {
                                sensors.add(new Sensor(ID, currSerialPort, outputStream, inputStream));
                                System.out.println("Adding sensor : " + ID);
                            } else {
                                actuators.add(new Actuator(ID, currSerialPort, outputStream, inputStream));
                                System.out.println("Adding actuator : " + ID);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        return;
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Application application = new Application();

        application.init();

        int result = 0;

        while(true) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (ISensor sensor : application.sensors) {
                result = sensor.getValue();
            }
            for (IActuator actuator : application.actuators) {
                actuator.sendValue(result);
            }
        }
    }
}
