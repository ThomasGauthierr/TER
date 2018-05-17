package core.device;

import gnu.io.SerialPort;

public interface IDevice {

    String getID();
    SerialPort getSerialPort();
    void writeString(String s);
}
