package devices.interfaces;

import java.io.IOException;

public interface IActuator extends IDevice {
    void sendValue(int value) throws IOException;
}
