package devices.interfaces;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface IDevice {
    String getID();
    SerialPort getSerialPort();
    OutputStream getOutputStream();
    InputStream getInputStream();
    void close() throws IOException;

    void writeString(String s) throws IOException;
    void writeInt(int i) throws IOException;
}
