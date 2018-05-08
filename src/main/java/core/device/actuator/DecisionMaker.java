package core.device.actuator;

import core.behavior.contract.ActionType;
import core.behavior.contract.IContract;
import core.device.sensor.Sensor;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class DecisionMaker implements Observer {

    List<IActuator> actuators;
    private int actualNice;
    private IContract.Status actualStatus;

    public DecisionMaker() {
        actuators = new ArrayList<>();
    }

    public DecisionMaker(List<IActuator> actuators) {
        this.actuators = actuators;
    }

    public void addActuator(IActuator actuator) {
        actuators.add(actuator);
    }

    public void removeActuator(IActuator actuator) {
        actuators.remove(actuator);
    }

    public void clearActuators() {
        actuators.clear();
    }

    @Override
    public void update(Observable observable, Object o) {
        IContract source = (IContract) observable;
        Sensor sensor = (Sensor) o;

        if(source.getStatus().isViolated() && source.getNice() > actualNice) {
            for (IActuator actuator : actuators) {
                System.out.println("[Actuator:"+ actuator.getID() + ":"+ actuator.getDataType().name()+ "] Received \""+ source.getStatus().name() + "\" event from -> [Sensor:"+ sensor.getID() + ":"+ sensor.getDataType().name()+ "]");
                if(actuator.getDataType().equals(sensor.getDataType())){
                    if((actuator.getActionType().equals(ActionType.INCREASE) && source.getStatus().equals(IContract.Status.VIOLATED_INCREASING)) || (actuator.getActionType().equals(ActionType.DECREASE) && source.getStatus().equals(IContract.Status.VIOLATED_DECREASING))){
                        if(actuator.isActivated()) {
                            System.out.println("[Actuator:"+ actuator.getID() + "] has " + actuator.getActionType().name() + " action over " + actuator.getDataType().name() + " data so it should be turned off.");
                            actuator.deactivate();
                            // ((Manager)evt.getSource()).repairing();
                        } else {
                            // not my fault
                            System.out.println("Not my fault");
                        }

                    } else if ((actuator.getActionType().equals(ActionType.INCREASE) && source.getStatus().equals(IContract.Status.VIOLATED_DECREASING))
                            || (actuator.getActionType().equals(ActionType.DECREASE) && source.getStatus().equals(IContract.Status.VIOLATED_INCREASING))) {
                        if(actuator.isActivated()) {
                            System.out.println("[Actuator:"+ actuator.getID() + "] has " + actuator.getActionType().name() + " action over " + actuator.getDataType().name() + " data so there is nothing i can do about it.");
                        } else {
                            System.out.println("[Actuator:"+ actuator.getID() + "] has " + actuator.getActionType().name() + " action over " + actuator.getDataType().name() + " data so it should be turned on.");
                            //TODO: tell the manager that i am able to repair
                            actuator.activate(IActuator.State.HIGH);
                            // ((Manager)evt.getSource()).repairing();
                        }
                    } else {
                        System.out.println("[Actuator:"+ actuator.getID() + "] has " + actuator.getActionType().name() + " action over " + actuator.getDataType().name() + " and the action type is " + source.getStatus().name() + " so i have no idea what to do.");
                    }
                } else {
                    System.out.println("DataType is not the same [" + actuator.getID() + "] can't handle this violation. (Needed " + sensor.getDataType().name() + " but is " + actuator.getDataType().name());
                }
            }
        } else {
            actualNice = -1;
            actuators.forEach(a -> a.deactivate());
        }
    }
}
