package core.device.actuator;

import core.behavior.contract.ContractListener;
import core.behavior.contract.UnrespectedContractListener;
import core.device.IDevice;

public interface IActuator extends IDevice, ContractListener {
    void sendState(State state);
    void activate(State state);
    void deactivate();
    boolean isActivated();

    enum State{
        HIGH,
        MEDHIGH,
        MEDLOW,
        LOW,
        OFF
    }

    State getState();
}
