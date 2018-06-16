package core.behavior.context;

import core.behavior.contract.ConcreteContract;
import core.behavior.contract.IContract;
import core.behavior.contract.IContractObserver;
import core.behavior.contract.MetaContract;

import java.util.List;
import java.util.Vector;

/**
 * Created by Alexis Couvreur on 15/06/2018.
 */
public class MetaContext implements IContext, IContractObserver {

    private String identifier;
    private List<IContract> monitoredContracts;
    private List<IContextObserver> observers;
    private IContract lastUpdated;
    private Class<? extends IContract> lastUpdatedClass;

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
        observers.forEach(o -> o.update(this, lastUpdated));
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

    @Override
    public List<IContract> getObservedContracts() {
        return getMonitoredEntities();
    }

    @Override
    public void update(IContract source, Object arg) {

        lastUpdated = source;
        boolean newViolated = arg instanceof IViolatedContext;

        if (source instanceof MetaContract) {
            // Update as a metacontract
            lastUpdatedClass = MetaContract.class;
        } else if (source instanceof ConcreteContract) {
            // Update as a concrete contract
            lastUpdatedClass = ConcreteContract.class;
        }

        if (newViolated) {
            IViolatedContext violatedContext = (IViolatedContext) arg;
            System.out.println("[MetaContext] Context has changed : [IContract] gave me " + violatedContext);
        } else {
            System.out.println("[MetaContext] Context has changed : [IContract] gave me " + arg);
        }

        notifyObservers();
    }
}
