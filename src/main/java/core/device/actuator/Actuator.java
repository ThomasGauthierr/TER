package core.device.actuator;

import core.behavior.contract.ActionType;
import core.device.DataType;
import core.device.Device;
import gnu.io.SerialPort;

public class Actuator extends Device implements IActuator {

    protected DataType dataType;
    protected ActionType actionType;
    protected State state;


    public Actuator(String ID, SerialPort serialPort, DataType dataType, ActionType actionType) {
        super(ID, serialPort);
        this.actionType = actionType;
        this.dataType = dataType;
        state = State.OFF;
    }

    @Override
    public void setState(State state) {
        writeString(("v" + state.name() + "\n"));
    }

    @Override
    public void activate() {
        this.state = State.HIGH;
        // TODO: simple thing here
    }

    @Override
    public void deactivate() {
        this.state = State.OFF;
        // TODO: simple thing here
    }

    @Override
    public boolean isActivated() {
        return state != State.OFF;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public DataType getDataType() {
        return dataType;
    }
    @Override
    public State getState() {
        return state;
    }

    @Override
    public String toString() {
        return "Actuator{" +
                "id=" + getID() +
                ", dataType=" + dataType +
                ", actionType=" + actionType +
                ", state=" + state +
                '}';
    }
}
