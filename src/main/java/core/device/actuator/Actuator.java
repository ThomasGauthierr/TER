package core.device.actuator;

import core.behavior.contract.ActionType;
import core.behavior.contract.IContract;
import core.device.DataType;
import core.device.Device;
import core.device.sensor.ISensor;
import core.device.sensor.Sensor;
import core.utils.Utils;
import gnu.io.SerialPort;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Observable;

public class Actuator extends Device implements IActuator {
    private ActionType actionType;
    private State state;
    ResponseType responseType;

    @Override
    public void update(Observable observable, Object o) {
        IContract source = (IContract) observable;
        Sensor sensor = (Sensor) o;

        if(source.getStatus().isViolated()) {
            violatedContractEventReceived(source, sensor);
        } else {
            respectedContractEventReceived(source, sensor);
        }
    }

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
                mode = Mode.ACTIVATE;
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
                if (responseType == ResponseType.NUMERIC) {
                    state = State.HIGH;
                }

                //Changing the actuator state
                sendState(state);

                try {
                    actuator.state = state;
                    //We verify if the actuator changed of state correctly
                    verifyState();

                } catch (IOException | IncorrectStateException e) {
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

                } catch (IOException | IncorrectStateException e) {
                    e.printStackTrace();
                    actuator.state = backUpState;

                }
            }
        }
    }

    public Actuator(String ID, SerialPort serialPort, OutputStream outputStream, InputStream inputStream, DataType dataType, ActionType actionType, ResponseType responseType) {
        super(ID, serialPort, outputStream, inputStream, dataType);
        this.actionType = actionType;
        this.responseType = responseType;
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

    public void violatedContractEventReceived(IContract source, ISensor violator) {
        System.out.println("[Actuator:"+ getID() + ":"+ getDataType().name()+ "] Received \""+ source.getStatus().name() + "\" event from -> [Sensor:"+ violator.getID() + ":"+ violator.getDataType().name()+ "]");
        if(dataType.equals(violator.getDataType())){
            if((getActionType().equals(ActionType.INCREASE) && source.getStatus().equals(IContract.Status.VIOLATED_INCREASING)) || (getActionType().equals(ActionType.DECREASE) && source.getStatus().equals(IContract.Status.VIOLATED_DECREASING))){
                if(isActivated()) {
                    System.out.println("[Actuator:"+ getID() + "] has " + getActionType().name() + " action over " + getDataType().name() + " data so it should be turned off.");
                    //TODO: tell the manager that i am working against the contract
                    deactivate();
                    // ((Manager)evt.getSource()).repairing();
                } else {
                    // not my fault
                    System.out.println("Not my fault");
                }

            } else if ((getActionType().equals(ActionType.INCREASE) && source.getStatus().equals(IContract.Status.VIOLATED_DECREASING))
                    || (getActionType().equals(ActionType.DECREASE) && source.getStatus().equals(IContract.Status.VIOLATED_INCREASING))) {
                if(isActivated()) {
                    System.out.println("[Actuator:"+ getID() + "] has " + getActionType().name() + " action over " + getDataType().name() + " data so there is nothing i can do about it.");
                } else {
                    System.out.println("[Actuator:"+ getID() + "] has " + getActionType().name() + " action over " + getDataType().name() + " data so it should be turned on.");
                    //TODO: tell the manager that i am able to repair
                    activate(State.HIGH);
                    // ((Manager)evt.getSource()).repairing();
                }
            } else {
                System.out.println("[Actuator:"+ getID() + "] has " + getActionType().name() + " action over " + getDataType().name() + " and the action type is " + source.getStatus().name() + " so i have no idea what to do.");
            }
        } else {
            System.out.println("DataType is not the same [" + getID() + "] can't handle this violation. (Needed " + violator.getDataType().name() + " but is " + dataType.name());
        }
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void respectedContractEventReceived(IContract source, ISensor sensor) {
        System.out.println("[Actuator:"+ getID() + ":"+ getDataType().name()+ "] Received \""+ source.getStatus().name() + "\" event from -> [Sensor:"+ sensor.getID() + ":"+ sensor.getDataType().name()+ "]");
        if(dataType.equals(sensor.getDataType())){
           deactivate();
        } else {
            System.out.println("DataType is not the same [" + getID() + "] i should be off. (Needed " + sensor.getDataType().name() + " but is " + dataType.name());
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
