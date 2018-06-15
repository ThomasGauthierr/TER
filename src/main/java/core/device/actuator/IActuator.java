package core.device.actuator;

import core.behavior.contract.ActionType;
import core.device.IDevice;
import core.device.TypedDevice;

/**
 * Created by Alexis Couvreur on 13/06/2018.
 */
public interface IActuator extends IDevice, TypedDevice {

    ActionType getActionType();
    State getState();

    boolean isActivated();
    void activate();
    void deactivate();

    enum State {
        ON,
        OFF
    }

}
