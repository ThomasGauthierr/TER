import com.google.common.collect.EvictingQueue;
import core.strategy.IOscillatorStrategy;
import core.strategy.OscillatorStrategyImpl;
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
import java.lang.reflect.Array;
import java.util.*;
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
                                sensors.add(new Sensor(ID, currSerialPort, outputStream, inputStream));
                                System.out.println("Adding sensor " + ID + " (port " + currPortId.getName() + ")");
                            } else {
                                actuators.add(new Actuator(ID, currSerialPort, outputStream, inputStream));
                                System.out.println("Adding actuator " + ID + " (port " + currPortId.getName() + ")");
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

        // CircularFIFOQueue
        Queue<Integer> q = EvictingQueue.create(2);
        q.add(1);
        q.add(2);
        q.add(3);
        System.out.println(q.peek());


        Application application = new Application();

        //Initiating ports and devices
        application.init();

        boolean shouldBeRunning = true;

        int initialWaitingTime = 250;
        int waitingTime = initialWaitingTime;
        IOscillatorStrategy oscillatorStrategy = new OscillatorStrategyImpl();

        // CircularFIFOQueue
        Queue<IOscillatorStrategy.ValueTimeStamp> queue = EvictingQueue.create(15);

        //Getting the program working for about 1 minute
        while(shouldBeRunning) {

            IOscillatorStrategy.ValueTimeStamp vt = null;

            try {
                TimeUnit.MILLISECONDS.sleep(waitingTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //Getting the value from the sensor
            for (ISensor sensor : sensors) {
                Integer[] values = sensor.getValues();
                vt = new IOscillatorStrategy.ValueTimeStamp(values[0], values[1]);
                queue.add(vt);
                System.out.print("value : " + values[0] + " || timestamp : " + values[1] + " || ");
            }


            //Sending the value to the actuator
            for (IActuator actuator : actuators) {
                actuator.sendValue(vt.getValue());
            }

            if (oscillatorStrategy.isOscillating(new ArrayList<>(queue))) {
                waitingTime = initialWaitingTime * 10;
                System.out.println("Oscillating");
            } else {
                waitingTime = initialWaitingTime;
                System.out.println("Not anymore");
            }

        }

        //Closing the sensors
        for (ISensor sensor : sensors) {
            sensor.close();
        }

        //Closing the actuators
        for (IActuator actuator : actuators) {
            actuator.close();
        }

        System.out.println("Ports closed");
    }
}
