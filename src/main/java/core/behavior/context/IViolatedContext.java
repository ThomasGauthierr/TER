package core.behavior.context;

import core.Message;
import core.behavior.contract.ActionType;
import core.device.actuator.IActuator;
import core.device.sensor.ISensor;

import java.util.List;

public interface IViolatedContext extends IContext {

    List<IActuator> getResponsibleList();
    List<ISensor> getWitnesses();

    ActionType getActionType();

    List<Message> getViolatingMessages();

}
