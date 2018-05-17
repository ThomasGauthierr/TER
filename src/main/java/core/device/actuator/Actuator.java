package core.device.actuator;

import core.behavior.contract.ActionType;
import core.device.DataType;
import core.device.Device;
import gnu.io.SerialPort;

public class Actuator extends Device implements IActuator {

    private DataType dataType;
    private ActionType actionType;
    private State state;


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
        // TODO: simple thing here
    }

    @Override
    public void deactivate() {
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
}
