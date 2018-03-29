package devices.interfaces;

import java.io.IOException;

public interface ISensor extends IDevice {
    Integer getValue() throws IOException;
}
