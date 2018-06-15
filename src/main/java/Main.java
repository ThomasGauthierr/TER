import core.Application;
import core.behavior.contract.ActionType;
import core.device.DataType;
import core.device.SerialPortDevice;
import core.device.actuator.SerialPortActuator;
import core.device.sensor.SerialPortSensor;
import core.utils.Utils;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.TooManyListenersException;
import java.util.concurrent.TimeUnit;

public class Main {

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
    public static Logger logger = Logger.getLogger(Main.class);

    private static List<SerialPortDevice> getDevices() {

        List<SerialPortDevice> devices = new ArrayList<>();

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
                    currSerialPort = Utils.initSerialPort(portID, Main.class);
                    inputStream = Utils.openInputStreams(currSerialPort);
                    outputStream = Utils.openOutputStreams(currSerialPort);

                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    try {
                        //Removing garbages which can be left on the input stream
                        //by the arduino if it hasn't been stopped correctly
                        Utils.getStringFromInputStream(inputStream);
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

                            try {

                                //SerialPortSensor
                                if (ID.substring(0, 1).equals("0")) {
                                    DataType type = DataType.values()[Integer.parseInt(ID.substring(1, 2))];
                                    devices.add(new SerialPortSensor(ID, currSerialPort, 5, type));
                                    System.out.println("Adding sensor " + ID + " (port " + currPortId.getName() + ")");
                                    System.out.println("DataType : " + type);

                                    //SerialPortActuator
                                } else if (ID.substring(0, 1).equals("1")) {
                                    DataType dataType = DataType.values()[Integer.parseInt(ID.substring(1, 2))];
                                    ActionType actionType = ActionType.values()[Integer.parseInt(ID.substring(2, 3))];
                                    devices.add(new SerialPortActuator(ID, currSerialPort, dataType, actionType));
                                    System.out.println("Adding actuator " + ID + " (port " + currPortId.getName() + ")");
                                    System.out.println("DataType : " + dataType + " || ActionType : " + actionType);

                                //Error
                                } else {
                                    System.out.println("Found [" + ID + "] without any valid ID.");
                                }

                            //Thrown when ID is wrongly defined
                            } catch (ArrayIndexOutOfBoundsException e) {
                                System.out.println("SerialPortDevice [" + ID + "] ignored, illegal ID");
                            } catch (TooManyListenersException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return devices;
    }

    public static void main(String[] args) {

        Application app = new Application();

        for (SerialPortDevice device : getDevices()) {
            if (device instanceof SerialPortSensor)
                app.addSensor((SerialPortSensor) device);
            if (device instanceof SerialPortActuator)
                app.addActuator((SerialPortActuator) device);
        }
/*
        SerialPort fsp = new FakeSerialPort();
        FakeSensor fakeSensor = new FakeSensor("01WindowSen", 25, DataType.TEMPERATURE, fsp);
        FakeActuator chauffage = new FakeActuator("chauffage", fsp, DataType.TEMPERATURE, ActionType.INCREASE);
        chauffage.activate();
        FakeActuator clime = new FakeActuator("clime", fsp, DataType.TEMPERATURE, ActionType.DECREASE);
        app.addSensor(fakeSensor);
        app.addActuator(chauffage);
        app.addActuator(clime);
*/
        try {
            app.init();
        } catch (TooManyListenersException e) {
            e.printStackTrace();
        }
/*
        Scanner sc = new Scanner(System.in);

        System.out.println("Control the fake data:");
        String line;
        while(!(line = sc.nextLine()).equalsIgnoreCase("q")) {
            String[] params = line.split(" ");
            if (params[0].equalsIgnoreCase("w") && params.length == 3) {
                fakeSensor.setFakeMessageStrategyBehavior(Integer.parseInt(params[1]), Double.parseDouble(params[2]));
            }

            ((FakeSerialPort) fsp).triggerDataAvailable();
        }
*/
        //Closing the sensors
        for (SerialPortDevice d : getDevices())
            d.close();

        System.out.println("Ports closed");
    }

    private static DataType getDataTypeFromId(String ID) {
        return DataType.valueOf("1");
    }
}
