package core.device.actuator;

import core.behavior.Manager;
import core.behavior.contract.ActionType;
import core.behavior.contract.ContractEvent;
import core.device.DataType;
import core.device.Device;
import core.utils.Utils;
import gnu.io.SerialPort;

import javax.xml.transform.sax.SAXTransformerFactory;
import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

public class Actuator extends Device implements IActuator {
    private ActionType actionType;
    private State state;

    //This class is in charge of activating or deactivating the actuator
    //It requires a thread to verify the actuator state without blocking the program
    class Activator implements Runnable, IActivator {
        Actuator actuator;
        Mode mode;
        State state;

        //An activator with 2 args should activate the actuator
        public Activator(Actuator actuator, State state) {
            this.actuator = actuator;
            if (state == State.OFF) {
                mode = Mode.DEACTIVATE;
            } else {
                mode = mode.ACTIVATE;
                this.state = state;
            }
        }

        //An activator with 1 arg deactivate the actuator
        public Activator(Actuator actuator) {
            this.actuator = actuator;
            this.mode = Mode.DEACTIVATE;
        }

        @Override
        public void run() {
            //We save the actuator state so we can rollback it if there is an error
            State backUpState = actuator.state;

            if (mode == Mode.ACTIVATE) {

                //Changing the actuator state
                sendState(state);

                try {
                    actuator.state = state;
                    //We verify if the actuator changed of state correctly
                    verifyState();

                } catch (IOException e) {
                    e.printStackTrace();
                    actuator.state = backUpState;

                } catch (IncorrectStateException e) {
                    e.printStackTrace();
                    actuator.state = backUpState;
                }

            } else {

                //Changing the actuator state
                sendState(State.OFF);
                try {
                    actuator.state = State.OFF;
                    //We verify if the actuator changed of state correctly
                    verifyState();

                } catch (IOException e) {
                    e.printStackTrace();
                    actuator.state = backUpState;

                } catch (IncorrectStateException e) {
                    e.printStackTrace();
                    actuator.state = backUpState;
                }
            }
        }
    }

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
        Activator activator = new Activator(this, state);
        Thread t = new Thread(activator);
        t.start();
    }

    @Override
    public void deactivate() {
        Activator activator = new Activator(this);
        Thread t = new Thread(activator);
        t.start();
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

    @Override
    public void verifyState() throws IOException, IncorrectStateException {
        //Empty the input stream (just in case)
        Utils.getStringFromInputStream(this.getSerialPort().getInputStream());

        askState();

        String message = "";

        //We wait until we receive the state
        while (message.length() == 0) {
            message = Utils.getStringFromInputStream(this.getSerialPort().getInputStream());
        }

        if (!message.equals(state.name())) {
            throw new IncorrectStateException(state.name(), message);
        }
    }

    @Override
    //Asks the actuator to transmit its state
    public void askState() {
        try {
            outputStream.write(("t" + "\n").getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
