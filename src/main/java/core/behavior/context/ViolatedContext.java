package core.behavior.context;

import core.behavior.contract.IContract;
import core.device.actuator.IActuator;
import core.device.sensor.ISensor;

import java.util.List;

public class ViolatedContext implements IViolatedContext {

    private IContract source;
    private List<IActuator> responsibleList;
    private List<ISensor> witnesses;
    private int[] values;

    public ViolatedContext(IContract source, List<IActuator> responsibleList, List<ISensor> witnesses, int[] values) {
        this.source = source;
        this.responsibleList = responsibleList;
        this.witnesses = witnesses;
        this.values = values;
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
    public int[] values() {
        return values;
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
    public String getIdentifier() {
        return source.getContext().getIdentifier();
    }
}
