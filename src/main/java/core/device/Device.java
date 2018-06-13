package core.device;

import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.TooManyListenersException;

public abstract class Device {

    private String identifier;
    private SerialPort serialPort;
    private DeviceReader deviceReader;
    private DeviceWriter deviceWriter;

    public Device(String identifier, SerialPort serialPort) throws TooManyListenersException {
        this.identifier = identifier;
        this.serialPort = serialPort;

        deviceReader = new DeviceReader();

        serialPort.notifyOnDataAvailable(true);
        serialPort.addEventListener(sp -> {
            if (sp.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
                try {
                    onDataAvailable(deviceReader.read());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void onDataAvailable(String data) {
        String[] values = data.split(" ");

        if (values.length != 3)
            return;

        onDataRead(values[0], Double.valueOf(values[1]), Long.valueOf(values[2]));
    }

    protected abstract void onDataRead(String source, double value, long timestamp);

    protected abstract void onDataWritten(String destination, double value, long timestamp);

    public void write(String message) throws IOException {
        deviceWriter.write(message);
    }

    public String getIdentifier() {
        return identifier;
    }

    private class DeviceWriter {
        void write(String message) throws IOException {
            serialPort.getOutputStream().write((message + "\n").getBytes());
        }
    }

    private class DeviceReader {
        public String read() throws IOException {
            StringBuilder data = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                data.append(line);
            }
            return data.toString();
        }
    }
}
