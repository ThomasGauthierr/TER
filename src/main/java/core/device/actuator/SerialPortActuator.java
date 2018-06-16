package core.device.actuator;

import core.behavior.contract.ActionType;
import core.device.DataType;
import core.device.SerialPortDevice;
import gnu.io.SerialPort;

import java.io.IOException;
import java.util.TooManyListenersException;

public class SerialPortActuator extends SerialPortDevice implements IActuator {

    protected DataType dataType;
    protected ActionType actionType;
    protected State state;


    public SerialPortActuator(String ID, SerialPort serialPort, DataType dataType, ActionType actionType) throws TooManyListenersException {
        super(ID, serialPort);
        this.actionType = actionType;
        this.dataType = dataType;
        this.state = State.OFF; // TODO: check
    }

    public ActionType getActionType() {
        return actionType;
    }

    @Override
    public State getState() {
        return state;
    }

    @Override
    public boolean isActivated() {
        return state == State.ON;
    }

    @Override
    public void activate() {
        if (isActivated())
            return;

        try {
            this.state = State.ON;
            write("activate");
        } catch (IOException e) {
            this.state = State.OFF;
            e.printStackTrace();
        }

        helper.insertActuatorMeasurement(this);
    }

    @Override
    public void deactivate() {

        if (!isActivated())
            return;

        try {
            this.state = State.OFF;
            write("deactivate");
        } catch (IOException e) {
            e.printStackTrace();
            this.state = State.ON;
        }

        helper.insertActuatorMeasurement(this);
    }

    public DataType getDataType() {
        return dataType;
    }

    @Override
    protected void onDataAvailable(String data) {
        // Maybe if the actuator haas to tell us something, it will be defined there
    }
}
