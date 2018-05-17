package core.device.actuator;

import core.behavior.contract.ActionType;
import core.device.IDevice;

import java.io.IOException;
import java.util.Observer;

public interface IActuator extends IDevice, Observer {
    void sendState(State state);
    void activate(State state);
    void deactivate();
    boolean isActivated();

    ActionType getActionType();

    void verifyState() throws IOException, IncorrectStateException;
    void askState();

    State getState();

    enum State {
        HIGH,
        MEDHIGH,
        MEDLOW,
        LOW,
        OFF
    }

    enum ResponseType {
        NUMERIC,
        ANALOGIC
    }

}
