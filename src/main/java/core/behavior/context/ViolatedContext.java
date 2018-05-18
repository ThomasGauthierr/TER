package core.behavior.context;

import core.Message;
import core.behavior.contract.ActionType;
import core.behavior.contract.IContract;
import core.device.DataType;
import core.device.actuator.IActuator;
import core.device.sensor.ISensor;

import java.util.List;
import java.util.OptionalDouble;

public class ViolatedContext implements IViolatedContext {

    private IContract source;
    private List<IActuator> responsibleList;
    private List<ISensor> witnesses;
    private List<Message> messages;

    public ViolatedContext(IContract source, List<IActuator> responsibleList, List<ISensor> witnesses, List<Message> messages) {
        this.source = source;
        this.responsibleList = responsibleList;
        this.witnesses = witnesses;
        this.messages = messages;
    }

    @Override
    public List<IActuator> getResponsibleList() {
        return responsibleList;
    }

    @Override
    public List<ISensor> getWitnesses() {
        return witnesses;
    }

    @Override
    public ActionType getActionType() {
        return null;
    }

    @Override
    public List<Message> getViolatingMessages() {
        return messages;
    }

    @Override
    public List<ISensor> getSensors() {
        return source.getContext().getSensors();
    }

    @Override
    public List<IActuator> getActuators() {
        return source.getContext().getActuators();
    }

    @Override
    public List<ISensor> getSensorsOf(DataType dt) {
        return source.getContext().getSensorsOf(dt);
    }

    @Override
    public List<IActuator> getActuatorsOf(DataType dt) {
        return source.getContext().getActuatorsOf(dt);
    }

    @Override
    public List<ISensor> getSensorsDecreasedBy(IActuator actuator) {
        return source.getContext().getSensorsDecreasedBy(actuator);
    }

    @Override
    public List<ISensor> getSensorsIncreasedBy(IActuator actuator) {
        return source.getContext().getSensorsIncreasedBy(actuator);
    }

    @Override
    public List<IActuator> getActuatorsThatDecrease(ISensor sensor) {
        return source.getContext().getActuatorsThatDecrease(sensor);
    }

    @Override
    public List<IActuator> getActuatorsThatIncrease(ISensor sensor) {
        return source.getContext().getActuatorsThatIncrease(sensor);
    }

    @Override
    public List<IContextListener> getListeners() {
        return source.getContext().getListeners();
    }

    @Override
    public void addListener(IContextListener listener) {
        source.getContext().addListener(listener);
    }

    @Override
    public OptionalDouble getValueOf(DataType dt) {
        return source.getContext().getValueOf(dt);
    }

    @Override
    public String getIdentifier() {
        return source.getContext().getIdentifier();
    }

    @Override
    public void update(ISensor source, List<Message> messageList) {
        // no idea what to do here
    }
}
