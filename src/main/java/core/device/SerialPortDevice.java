package core.device;

import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.TooManyListenersException;

public abstract class SerialPortDevice implements IDevice {

    private String identifier;
    private SerialPort serialPort;

    public SerialPortDevice(String identifier, SerialPort serialPort) throws TooManyListenersException {
        this.identifier = identifier;
        this.serialPort = serialPort;

        this.serialPort.addEventListener(sp -> {
            if (sp.getEventType() == SerialPortEvent.DATA_AVAILABLE)
                try {
                    onDataAvailable(read());
                } catch (IOException e) {
                    e.printStackTrace();
                }
        });
    }

    public void setReadDataWhenAvailable(boolean readDataWhenAvailable) {
        this.serialPort.notifyOnDataAvailable(readDataWhenAvailable);
    }

    protected abstract void onDataAvailable(String data);

    public String read() throws IOException {
        StringBuilder data = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
        String line;
        while ((line = br.readLine()) != null) {
            data.append(line);
        }
        return data.toString();
    }

    public void write(String message) throws IOException {
        serialPort.getOutputStream().write((message + "\n").getBytes());
    }

    public String getIdentifier() {
        return identifier;
    }

    public void close() {
        this.serialPort.close();
    }

}
