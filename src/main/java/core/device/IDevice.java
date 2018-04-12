package core.device;

import gnu.io.SerialPort;

import java.io.InputStream;
import java.io.OutputStream;

public interface IDevice {

    String getID();
    SerialPort getSerialPort();
    OutputStream getOutputStream();
    InputStream getInputStream();
    void close();

    void writeString(String s);
    void writeInt(int i);
}
