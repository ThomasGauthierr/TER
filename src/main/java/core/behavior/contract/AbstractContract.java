package core.behavior.contract;

import core.device.actuator.IActuator;
import core.device.sensor.ISensor;

import java.util.List;
import java.util.Observable;
import java.util.function.Predicate;

public abstract class AbstractContract implements IContract {

    private IContract parent;
    private List<IContract> nodes;
    private List<ContractObserver> observers; // actuators
    private List<ContractListener> listeners; // sensors / contracts
    private Predicate<?> predicate;

    public AbstractContract(IContract parent, Predicate<?> predicate) {
        this.parent = parent;
        this.predicate = predicate;
    }

    @Override public List<ContractObserver> getObservers() { return observers; }
    @Override public List<ContractListener> getListeners() { return listeners; }
    @Override public IContract getParent() { return parent; }
    @Override public List<IContract> getNodes() { return nodes; }

    @Override public boolean isRoot() { return this.parent == null; }
    @Override public boolean isLeaf() { return this.nodes.isEmpty(); }

    @Override
    public void addObserver(ContractObserver contractObserver) {
        this.observers.add(contractObserver);
    }

    @Override
    public void addListener(ContractListener contractListener) {
        this.listeners.add(contractListener);
    }

    @Override
    public void update(Observable o, Object args){
        if(o instanceof ISensor) {

        }
    }

    @Override
    public void onViolatedContract(ContractEvent evt) {
        if(canIHandleThis()) {
            if(!isRoot()){
                askIfSuperContractAreNotBroken();
            } else {
                fixThis();
            }
        } else if(!isRoot()) {
            parent.onViolatedContract(evt);
        } else {
            doNothing();
        }
    }

    protected abstract void fixThis();

    protected abstract void askIfSuperContractAreNotBroken();

    @Override
    public void onRespectedContract(ContractEvent evt) {
        listeners.forEach(l -> l.onRespectedContract(evt));
    }

    private boolean canFixThis(ContractEvent evt) {
        observers.forEach(o -> {
            evt.getActionType()
            ((IActuator)o).getDataType().
        });
        if(evt.getSensor().getDataType())
        return false;
    }
    private void handle(){}
    private void doNothing(){}

}
