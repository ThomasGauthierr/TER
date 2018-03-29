package devices;

import devices.interfaces.IDevice;
import gnu.io.SerialPort;
import utils.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class Device implements IDevice {
    protected String ID;
    protected SerialPort serialPort;
    private OutputStream outputStream;
    private InputStream inputStream;
    private boolean outputStreamOpened, inputStreamOpened;

    public Device(String ID, SerialPort serialPort) {
        this.ID = ID;
        this.serialPort = serialPort;
        outputStreamOpened = false;
        inputStreamOpened = false;
    }

    public String getID() {
        return ID;
    }

    @Override
    public SerialPort getSerialPort() {
        return serialPort;
    }

    /*
    *
    * OutputStream
    *
    */
    private void openOutputStream() {
        outputStream = Utils.openOutputStreams(serialPort);
        outputStreamOpened = true;
    }

    @Override
    public OutputStream getOutputStream() {
        if (!outputStreamOpened)
            openOutputStream();

        return outputStream;
    }

    /*
     *
     * InputStream
     *
     */
    private void openInputStream() {
        inputStream = Utils.openInputStreams(serialPort);
        inputStreamOpened = true;
    }

    @Override
    public InputStream getInputStream() {
        if (!inputStreamOpened)
            openInputStream();

        return inputStream;
    }

    @Override
    public void close() throws IOException {

        outputStream.close();
        inputStream.close();
        serialPort.close();

    }

    @Override
    public void writeString(String s) throws IOException {
        outputStream.write((s + "\n").getBytes());

    }

    @Override
    public void writeInt(int i) throws IOException {
        writeString(Integer.toString(i));
    }
}
