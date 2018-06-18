package core.behavior.contract;

import core.behavior.context.IContext;
import core.behavior.context.IContextObserver;
import core.behavior.context.MetaContext;

import java.util.List;
import java.util.Vector;
import java.util.function.Predicate;

/**
 * Created by Alexis Couvreur on 14/06/2018.
 */
public class MetaContract implements IContract, IContextObserver {

    private String name;
    private MetaContext contextObserved;
    private Predicate<IContext> metaPredicate;
    private List<IContractObserver> observers;

    public MetaContract(String name, MetaContext contextObserved) {

        this.name = name;
        this.contextObserved = contextObserved;
        this.observers = new Vector<>();
    }

    public void update(IContext source, Object arg) {
        // Provient uniquement de MetaContext
        System.out.println("[MetaContract] A monitored context has been updated : " + source.getIdentifier());

        notifyObservers();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Status getStatus() {
        return null;
    }

    @Override
    public void addObserver(IContractObserver observer) {
        this.observers.add(observer);
    }

    @Override
    public void removeObserver(IContractObserver observer) {
        this.observers.remove(observer);
    }

    @Override
    public void notifyObservers() {

    }

    @Override
    public IContext getObservedContext() {
        return contextObserved;
    }
}
