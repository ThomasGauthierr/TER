package core.device.actuator;

import core.behavior.contract.ActionType;
import core.behavior.contract.IContract;
import core.device.IDevice;
import core.device.sensor.ISensor;

import java.io.IOException;
import java.util.Observer;

public interface IActuator extends IDevice, Observer {
    void sendState(State state);
    void activate(State state);
    void deactivate();
    boolean isActivated();

    void verifyState() throws IOException, IncorrectStateException;
    void askState();

    State getState();
    ActionType getActionType();

    void violatedContractEventReceived(IContract source, ISensor violator);
    void respectedContractEventReceived(IContract source, ISensor sensor);

    DecisionMaker getDecisionMaker();

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
