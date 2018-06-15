package core.behavior.context;

import core.behavior.contract.IContract;

import java.util.List;
import java.util.Vector;

/**
 * Created by Alexis Couvreur on 15/06/2018.
 */
public class MetaContext implements IContext {

    private String identifier;
    private List<IContract> monitoredContracts;
    private List<IContextObserver> observers;

    public MetaContext(String identifier) {
        this.identifier = identifier;
        this.monitoredContracts = new Vector<>();
        this.observers = new Vector<>();
    }


    @Override
    public List<IContract> getMonitoredEntities() {
        return monitoredContracts;
    }

    @Override
    public void notifyObservers() {
        // TODO: notify here
    }

    @Override
    public void addObserver(IContextObserver observer) {
        this.observers.add(observer);
    }

    @Override
    public void removeObserver(IContextObserver observer) {
        this.observers.remove(observer);
    }

    @Override
    public String getIdentifier() {
        return identifier;
    }
}
