package core.device.actuator;

import core.behavior.contract.ActionType;
import core.behavior.contract.UnrespectedContractEvent;
import core.device.DataType;
import core.device.Device;
import gnu.io.SerialPort;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Actuator extends Device implements IActuator {
    private ActionType actionType;

    public Actuator(String ID, SerialPort serialPort, OutputStream outputStream, InputStream inputStream, DataType dataType, ActionType actionType) {
        super(ID, serialPort, outputStream, inputStream, dataType);
        this.actionType = actionType;
    }

    @Override
    public void sendValue(int value) {
        try {
            //Sending the value to the actuator
            outputStream.write(("v" + Integer.toString(value) + "\n").getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        try {
            outputStream.write("c\n".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.close();
    }

    @Override
    public void unrespectedContractEventReceived(UnrespectedContractEvent evt) {
        System.out.println("["+ getID() + ":"+ getDataType().name()+ "] Received \""+ evt.getActionType().name() + "\" event from -> ["+ evt.getSensor().getID() + ":"+ evt.getSensor().getDataType().name()+ "]");
        if(dataType.equals(evt.getSensor().getDataType())){
            if(getActionType().equals(evt.getActionType())){
                System.out.println("["+ getID() + "] has " + getActionType().name() + " action over " + getDataType().name() + " data so it should be turned off.");
                //TODO: tell the manager that i am able to repair
            } else if ((getActionType().equals(ActionType.INCREASE) && evt.getActionType().equals(ActionType.DECREASE))
                    || (getActionType().equals(ActionType.DECREASE) && evt.getActionType().equals(ActionType.INCREASE))) {
                System.out.println("["+ getID() + "] has " + getActionType().name() + " action over " + getDataType().name() + " data so it should be turned on.");
                //TODO: tell the manager that i am able to repair
            } else {
                System.out.println("["+ getID() + "] has " + getActionType().name() + " action over " + getDataType().name() + " and the action type is " + evt.getActionType().name() + " so i have no idea what to do.");
            }
        } else {
            System.out.println("DataType is not the same [" + getID() + "] can't handle this violation. (Needed " + evt.getSensor().getDataType().name() + " but is " + dataType.name());
        }
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }
}
