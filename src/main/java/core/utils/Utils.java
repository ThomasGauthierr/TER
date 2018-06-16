package core.utils;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

import java.io.*;

public final class Utils {

    public static String getStringFromInputStream(InputStream is) {

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

    public static SerialPort initSerialPort(CommPortIdentifier portId, Class cl) {
        SerialPort serialPort;
        try {
            serialPort = (SerialPort) portId.open(cl.getName(), 2000);
        } catch (PortInUseException e) {
            // e.printStackTrace();
            return null;
        }

        try {
            serialPort.setSerialPortParams(9600,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);
        } catch (UnsupportedCommOperationException e) {
            e.printStackTrace();
            return null;
        }
        return serialPort;
    }

    public static OutputStream openOutputStreams(SerialPort serialPort) {
        OutputStream outputStream;

        try {
            outputStream = serialPort.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return outputStream;
    }

    public static InputStream openInputStreams(SerialPort serialPort) {
        InputStream inputStream = null;

        try {
            inputStream = serialPort.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return inputStream;
    }
}
