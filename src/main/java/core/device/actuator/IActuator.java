package core.device.actuator;

import core.behavior.UnrespectedContractListener;
import core.device.IDevice;

public interface IActuator extends IDevice, UnrespectedContractListener {
    void sendValue(int value);
}
