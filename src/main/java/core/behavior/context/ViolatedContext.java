package core.behavior.context;

import core.Message;
import core.behavior.contract.ActionType;
import core.behavior.contract.IContract;
import core.device.sensor.ISensor;

import java.util.List;

public class ViolatedContext implements IViolatedContext {

    private IContract violatedContract;
    private IContext source;
    private ISensor witness;
    private Message message;

    public ViolatedContext(IContract violatedContract, IContext source, ISensor witness, Message message) {
        this.source = source;
        this.witness = witness;
        this.message = message;
        this.violatedContract = violatedContract;
    }

    @Override
    public IContract getViolatedContract() {
        return violatedContract;
    }

    @Override
    public ISensor getWitness() {
        return witness;
    }

    @Override
    public ActionType getActionType() {
        return null;
    }

    @Override
    public Message getViolatingMessage() {
        return message;
    }

    @Override
    public List<? extends MonitoredEntity> getMonitoredEntities() {
        return source.getMonitoredEntities();
    }

    @Override
    public void notifyObservers() {
        source.notifyObservers();
    }

    @Override
    public void addObserver(IContextObserver observer) {
        source.addObserver(observer);
    }

    @Override
    public void removeObserver(IContextObserver observer) {
        source.removeObserver(observer);
    }

    @Override
    public String getIdentifier() {
        return source.getIdentifier();
    }

}
