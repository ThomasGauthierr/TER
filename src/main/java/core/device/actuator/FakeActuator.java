package core.device.actuator;

import core.behavior.contract.ActionType;
import core.device.DataType;
import gnu.io.SerialPort;

public class FakeActuator extends Actuator {

    public FakeActuator(String ID, SerialPort serialPort, DataType dataType, ActionType actionType) {
        super(ID, serialPort, dataType, actionType);
    }

    @Override
    public void writeString(String s) {
    }
}
