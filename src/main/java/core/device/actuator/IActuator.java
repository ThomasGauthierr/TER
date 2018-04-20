package core.device.actuator;

import core.behavior.contract.UnrespectedContractListener;
import core.device.IDevice;

public interface IActuator extends IDevice, UnrespectedContractListener {
    void sendValue(int value);
    void activate();
    void deactivate();
    boolean isActivated();
}
