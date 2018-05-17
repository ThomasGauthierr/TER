package core.device.actuator;

import core.behavior.contract.ActionType;
import core.device.DataType;
import core.device.IDevice;

public interface IActuator extends IDevice {

    State getState();

    void setState(State state);

    void activate();
    void deactivate();

    boolean isActivated();

    DataType getDataType();
    ActionType getActionType();

    enum State {
        HIGH,
        MEDHIGH,
        MEDLOW,
        LOW,
        OFF
    }

}
