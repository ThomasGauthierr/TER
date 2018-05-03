package core.device.actuator;

import core.behavior.contract.ContractListener;
import core.device.IDevice;

import java.io.IOException;

public interface IActuator extends IDevice, ContractListener {
    void sendState(State state);
    void activate(State state);
    void deactivate();
    boolean isActivated();

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
