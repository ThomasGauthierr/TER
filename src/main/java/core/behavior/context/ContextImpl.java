package core.behavior.context;

import core.device.actuator.IActuator;
import core.device.sensor.ISensor;

import java.util.List;
import java.util.Vector;

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
    public String getIdentifier() {
        return identifier;
    }
}
