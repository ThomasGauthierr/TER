package core.device.actuator;

import core.behavior.Manager;
import core.behavior.contract.ActionType;
import core.behavior.contract.ContractEvent;
import core.device.DataType;
import core.device.Device;
import gnu.io.SerialPort;

import javax.xml.transform.sax.SAXTransformerFactory;
import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Actuator extends Device implements IActuator {
    private ActionType actionType;
    private State state;

    public Actuator(String ID, SerialPort serialPort, OutputStream outputStream, InputStream inputStream, DataType dataType, ActionType actionType) {
        super(ID, serialPort, outputStream, inputStream, dataType);
        this.actionType = actionType;
        state = State.OFF;
    }

    @Override
    public void sendState(State state) {
        try {
            String stateName = state.name();
            //Sending the value to the actuator
            outputStream.write(("v" + stateName + "\n").getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void activate(State state) {
        if (state != State.OFF) {
            this.state = state;
            sendState(state);
        } else {
            deactivate();
        }
    }

    @Override
    public void deactivate() {
        state = State.OFF;
        sendState(State.OFF);
    }

    @Override
    public boolean isActivated() {
        return state != State.OFF;
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
    public void unrespectedContractEventReceived(ContractEvent evt) {
        System.out.println("[Actuator:"+ getID() + ":"+ getDataType().name()+ "] Received \""+ evt.getActionType().name() + "\" event from -> [Sensor:"+ evt.getSensor().getID() + ":"+ evt.getSensor().getDataType().name()+ "]");
        if(dataType.equals(evt.getSensor().getDataType())){
            if(getActionType().equals(evt.getActionType())){
                if(isActivated()) {
                    System.out.println("[Actuator:"+ getID() + "] has " + getActionType().name() + " action over " + getDataType().name() + " data so it should be turned off.");
                    //TODO: tell the manager that i am working against the contract
                    deactivate();
                    // ((Manager)evt.getSource()).repairing();
                } else {
                    // not my fault
                    System.out.println("Not my fault");
                }

            } else if ((getActionType().equals(ActionType.INCREASE) && evt.getActionType().equals(ActionType.DECREASE))
                    || (getActionType().equals(ActionType.DECREASE) && evt.getActionType().equals(ActionType.INCREASE))) {
                if(isActivated()) {
                    // not my fault
                    System.out.println("Not my fault");
                } else {
                    System.out.println("[Actuator:"+ getID() + "] has " + getActionType().name() + " action over " + getDataType().name() + " data so it should be turned on.");
                    //TODO: tell the manager that i am able to repair
                    activate(State.HIGH);
                    // ((Manager)evt.getSource()).repairing();
                }
            } else {
                System.out.println("[Actuator:"+ getID() + "] has " + getActionType().name() + " action over " + getDataType().name() + " and the action type is " + evt.getActionType().name() + " so i have no idea what to do.");
            }
        } else {
            System.out.println("DataType is not the same [" + getID() + "] can't handle this violation. (Needed " + evt.getSensor().getDataType().name() + " but is " + dataType.name());
        }
    }

    public ActionType getActionType() {
        return actionType;
    }

    @Override
    public void respectedContractEventReceived(ContractEvent evt) {
        System.out.println("[Actuator:"+ getID() + ":"+ getDataType().name()+ "] Received \""+ evt.getActionType().name() + "\" event from -> [Sensor:"+ evt.getSensor().getID() + ":"+ evt.getSensor().getDataType().name()+ "]");
        if(dataType.equals(evt.getSensor().getDataType())){
           deactivate();
        } else {
            System.out.println("DataType is not the same [" + getID() + "] can't handle this violation. (Needed " + evt.getSensor().getDataType().name() + " but is " + dataType.name());
        }
    }

    @Override
    public State getState() {
        return state;
    }
}
