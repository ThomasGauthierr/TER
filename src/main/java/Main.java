import core.Application;
import core.behavior.context.WeatherStrategy;
import core.behavior.contract.ActionType;
import core.device.DataType;
import core.device.IDevice;
import core.device.actuator.Actuator;
import core.device.actuator.IActuator;
import core.device.sensor.FakeSensor;
import core.device.sensor.ISensor;
import core.device.sensor.Sensor;
import core.utils.Utils;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;
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

    private static List<IDevice> getDevices() {

        List<IDevice> devices = new ArrayList<>();

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

                                //Sensor
                                if (ID.substring(0, 1).equals("0")) {
                                    DataType type = DataType.values()[Integer.parseInt(ID.substring(1, 2))];
                                    devices.add(new Sensor(ID, currSerialPort, outputStream, inputStream, 5, type));
                                    System.out.println("Adding sensor " + ID + " (port " + currPortId.getName() + ")");
                                    System.out.println("DataType : " + type);

                                //Actuator
                                } else if (ID.substring(0, 1).equals("1")) {
                                    DataType dataType = DataType.values()[Integer.parseInt(ID.substring(1, 2))];
                                    ActionType actionType = ActionType.values()[Integer.parseInt(ID.substring(2, 3))];
                                    devices.add(new Actuator(ID, currSerialPort, outputStream, inputStream, dataType, actionType, IActuator.ResponseType.NUMERIC));
                                    System.out.println("Adding actuator " + ID + " (port " + currPortId.getName() + ")");
                                    System.out.println("DataType : " + dataType + " || ActionType : " + actionType);

                                //Error
                                } else {
                                    System.out.println("Found [" + ID + "] without any valid ID.");
                                }

                            //Thrown when ID is wrongly defined
                            } catch (ArrayIndexOutOfBoundsException e) {
                                System.out.println("Device [" + ID + "] ignored, illegal ID");
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

        for(IDevice device : getDevices()){
            if(device instanceof ISensor)
                app.addSensor((Sensor) device);
            if(device instanceof IActuator)
                app.addActuator((IActuator) device);
        }

        SerialPort fsp = new FakeSerialPort();
        FakeSensor fakeSensor = new FakeSensor("01WindowSen", 25, DataType.TEMPERATURE, fsp);
        app.addSensor(fakeSensor);

        try {
            app.init();
        } catch (TooManyListenersException e) {
            e.printStackTrace();
        }

        Scanner sc = new Scanner(System.in);

        System.out.println("Control the fake data:");
        String line;
        while(!(line = sc.nextLine()).equalsIgnoreCase("q")) {
            ((FakeSerialPort) fsp).triggerDataAvailable();
            String[] params = line.split(" ");
            if(params[0].equalsIgnoreCase("w") && params.length == 3) {
                fakeSensor.setFakeMessageStrategyBehavior(new WeatherStrategy(Integer.parseInt(params[1]), Double.parseDouble(params[2])));
            }

        }

        //Closing the sensors
        for (ISensor sensor : app.getSensors()) {
            sensor.close();
        }

        //Closing the actuators
        for (IActuator actuator : app.getActuators()) {
            actuator.close();
        }

        System.out.println("Ports closed");
    }

    private static DataType getDataTypeFromId(String ID) {
        return DataType.valueOf("1");
    }
}
