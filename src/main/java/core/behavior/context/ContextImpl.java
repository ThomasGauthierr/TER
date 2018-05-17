package core.behavior.context;

import core.behavior.contract.ActionType;
import core.device.DataType;
import core.device.actuator.IActuator;
import core.device.sensor.ISensor;

import java.util.Collections;
import java.util.List;
import java.util.OptionalDouble;
import java.util.Vector;
import java.util.stream.Collectors;

public class ContextImpl implements IContext {

    private String identifier;
    private List<ISensor> sensors;
    private List<IActuator> actuators;

    public ContextImpl(String identifier) {
        this.identifier = identifier;
        this.sensors = new Vector<>();
        this.actuators = new Vector<>();
    }

    @Override
    public List<ISensor> getSensors() {
        return sensors;
    }

    @Override
    public List<IActuator> getActuators() {
        return actuators;
    }

    @Override
    public List<ISensor> getSensorsOf(DataType dt) {
        return sensors
                .stream().filter(s -> s.getDataType().equals(dt))
                .collect(Collectors.toList());
    }

    @Override
    public List<IActuator> getActuatorsOf(DataType dt) {
        return actuators
                .stream().filter(a -> a.getDataType().equals(dt))
                .collect(Collectors.toList());
    }

    @Override
    public List<ISensor> getSensorsDecreasedBy(IActuator actuator) {
        if (actuator.isActivated() && actuator.getActionType().equals(ActionType.DECREASE)) {
            return getSensorsOf(actuator.getDataType());
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public List<ISensor> getSensorsIncreasedBy(IActuator actuator) {
        if (actuator.isActivated() && actuator.getActionType().equals(ActionType.INCREASE)) {
            return getSensorsOf(actuator.getDataType());
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public List<IActuator> getActuatorsThatDecrease(ISensor sensor) {
        return getActuatorsOf(sensor.getDataType())
                .stream().filter(a -> a.isActivated() && a.getActionType().equals(ActionType.DECREASE))
                .collect(Collectors.toList());
    }

    @Override
    public List<IActuator> getActuatorsThatIncrease(ISensor sensor) {
        return getActuatorsOf(sensor.getDataType())
                .stream().filter(a -> a.isActivated() && a.getActionType().equals(ActionType.INCREASE))
                .collect(Collectors.toList());
    }

    @Override
    public OptionalDouble getValueOf(DataType dt) {
        // TODO: remove the get 0 ...
        return getSensorsOf(dt).stream().mapToInt(v -> v.getData().get(0).getValue()).average();
    }

    @Override
    public String getIdentifier() {
        return identifier;
    }
}
