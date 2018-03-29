import devices.Actuator;
import devices.Sensor;
import devices.interfaces.IActuator;
import devices.interfaces.ISensor;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import utils.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.concurrent.TimeUnit;

public class Application {
    private static ArrayList<ISensor> sensors = new ArrayList<>();
    private static ArrayList<IActuator> actuators = new ArrayList<>();

    private static final String IDMessage = "i\n";

    private static final String portNames[] = {
            "/dev/tty.usbserial-A9007UX1", // Mac OS X
            "/dev/ttyACM0", // Raspberry Pi
            "/dev/ttyUSB0", // Linux
            // Windows ports
            "COM1",
            "COM2",
            "COM3",
            "COM4",
            "COM5",
            "COM6"
    };

    private static void init() {

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
                    currSerialPort = Utils.initSerialPort(portID, Application.class);
                    inputStream = Utils.openInputStreams(currSerialPort);
                    outputStream = Utils.openOutputStreams(currSerialPort);

                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    try {
                        //Asking the device to get its ID
                        outputStream.write(IDMessage.getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try {
                        int i = 0;
                        while (inputStream.available() == 0 && i < 10) {
                            //Waiting the device to reply every 100ms.
                            //We consider the port not to be a device if it hasn't reply after 1 second
                            try {
                                TimeUnit.MILLISECONDS.sleep(100);
                                i++;
                                if (i == 10) {
                                    System.out.println("No device found on port " + currPortId.getName());
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                        //If the device has answered, we check if he's an actuator or a sensor
                        while (inputStream.available() > 0) {
                            String ID = Utils.getStringFromInputStream(inputStream);
                            if (ID.contains("sensor")) {
                                sensors.add(new Sensor(ID, currSerialPort));
                                System.out.println("Adding sensor " + ID + " (port " + currPortId.getName() + ")");
                            } else {
                                actuators.add(new Actuator(ID, currSerialPort));
                                System.out.println("Adding actuator " + ID + " (port " + currPortId.getName() + ")");
                            }
                            inputStream.close();
                            outputStream.close();

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

        //Initiating ports and devices
        init();

        int value = 0;
        int i = 0;

        //Getting the program working for about 1 minute
        while (i < 240) {
            try {
                TimeUnit.MILLISECONDS.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //Getting the value from the sensor
            for (ISensor sensor : sensors) {
                try {
                    value = sensor.getValue();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            //Sending the value to the actuator
            for (IActuator actuator : actuators) {
                try {
                    actuator.sendValue(value);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            i++;
        }

        try {
            //Closing the sensors
            for (ISensor sensor : sensors) {
                sensor.close();
            }

            //Closing the actuators
            for (IActuator actuator : actuators) {
                actuator.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Ports closed");
    }
}
