package main.java;

import java.io.*;

import gnu.io.*;

import java.util.Enumeration;
import java.util.concurrent.TimeUnit;


public class SerialPrototype {
    private static SerialPort actuatorSerialPort;
    private static SerialPort sensorSerialPort;

    private static OutputStream actuatorOutputStream;
    private static OutputStream sensorOutputStream;

    private static InputStream sensorInputStream;


    private static String sensorPorts[] = {
            "COM3"
    };

    private static String actuatorPorts[] = {
            "COM4"
    };


    private void init() {
        CommPortIdentifier actuatorPortId = null;
        CommPortIdentifier sensorPortId = null;
        Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

        while (portEnum.hasMoreElements()) {
            CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();

            //Initializing actuator components
            for (String portName : actuatorPorts) {
                if (currPortId.getName().equals(portName)) {
                    actuatorPortId = currPortId;

                    //Opening serial port
                    try {
                        actuatorSerialPort = (SerialPort) actuatorPortId.open(this.getClass().getName(), 2000);
                    } catch (PortInUseException e) {
                        e.printStackTrace();
                    }

                    //Opening output stream
                    try {
                        actuatorOutputStream = actuatorSerialPort.getOutputStream();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    //Setting parameters
                    try {
                        actuatorSerialPort.setSerialPortParams(
                                9600,
                                SerialPort.DATABITS_8,
                                SerialPort.STOPBITS_1,
                                SerialPort.PARITY_NONE);
                    } catch (UnsupportedCommOperationException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }

            //Initializing sensor components
            for (String portName : sensorPorts) {
                if (currPortId.getName().equals(portName)) {
                    sensorPortId = currPortId;

                    //Opening serial port
                    try {
                        sensorSerialPort = (SerialPort) sensorPortId.open(this.getClass().getName(), 2000);
                    } catch (PortInUseException e) {
                        e.printStackTrace();
                    }

                    //Opening output and input streams
                    try {
                        sensorOutputStream = sensorSerialPort.getOutputStream();
                        sensorInputStream = sensorSerialPort.getInputStream();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    //Setting parameters
                    try {
                        sensorSerialPort.setSerialPortParams(
                                9600,
                                SerialPort.DATABITS_8,
                                SerialPort.STOPBITS_1,
                                SerialPort.PARITY_NONE);
                    } catch (UnsupportedCommOperationException e) {
                        e.printStackTrace();
                    }

                    /*
                    try {
                        sensorSerialPort.addEventListener(this);
                    } catch (TooManyListenersException e) {
                        e.printStackTrace();
                    }
                    */

                    sensorSerialPort.notifyOnDataAvailable(true);
                    break;
                }
            }
        }
        if (actuatorPortId == null) {
            System.out.println("Actuators : Could not find any plugged device.");
        }

        if (sensorPortId == null) {
            System.out.println("Sensors : Could not find any plugged device.");
        }
    }


    private static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {

        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();
    }

    //Closing ports and streams
    private void close() {
        try {
            actuatorOutputStream.close();
            actuatorSerialPort.close();
            sensorSerialPort.removeEventListener();
            sensorOutputStream.close();
            sensorSerialPort.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (NullPointerException e){
        }
    }

    public static void main(String[] args) throws Exception {
        SerialPrototype test = new SerialPrototype();
        String delayTimeActuator = "500\n";
        String delayTimeSensor = "500\n";

        test.init();

        int i = 0;

        while (i <= 100) {

            int result = 0;

            while (sensorInputStream.available() == 0) {
                //waiting to get the sensor value
            }

            while (sensorInputStream.available() > 0) {
                result = Integer.parseInt(getStringFromInputStream(sensorInputStream));
                System.out.println("Result : " + result);
            }

            sensorOutputStream.write(delayTimeActuator.getBytes());
            System.out.print(i + " -> Message envoyé : " + delayTimeActuator + "\n");

            actuatorOutputStream.write((Integer.toString(result)+"\n").getBytes());

            i++;
        }

        test.close();
    }
}