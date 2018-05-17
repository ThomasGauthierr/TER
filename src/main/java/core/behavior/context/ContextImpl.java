package core.behavior.context;

import core.device.DataType;
import core.device.actuator.IActuator;
import core.device.sensor.ISensor;

import java.util.List;
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
        return sensors.stream().filter(s -> s.getDataType().equals(dt)).collect(Collectors.toList());
    }

    @Override
    public List<IActuator> getActuatorsOf(DataType dt) {
        return actuators.stream().filter(a -> a.getDataType().equals(dt)).collect(Collectors.toList());
    }

    @Override
    public String getIdentifier() {
        return identifier;
    }
}
