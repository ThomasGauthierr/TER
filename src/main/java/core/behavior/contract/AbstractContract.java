package core.behavior.contract;

import java.util.List;
import java.util.Observable;
import java.util.Vector;

public abstract class AbstractContract extends Observable implements IContract  {

    private Vector<Observable> observables;
    private ContractPredicate predicate;
    private Status status;
    private String name;

    public AbstractContract(String name, ContractPredicate predicate) {
        this.predicate = predicate;
        this.status = Status.OK;
        this.name = name;
        observables = new Vector<>();
    }

    @Override
    public void addObservable(Observable observable) {
        observable.addObserver(this);
        observables.add(observable);
    }

    @Override
    public void deleteObservable(Observable observable) {
        observable.deleteObserver(this);
        observables.remove(observable);
    }

    @Override
    public void deleteObservables() {
        observables.forEach(o -> o.deleteObserver(this));
        observables.clear();
    }

    @Override
    public int countObservables() { return observables.size(); }

    @Override
    public abstract void update(Observable o, Object args);

    @Override
    public ContractPredicate getPredicate() {
        return predicate;
    }

    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public String getName() {
        return name;
    }

    protected void setStatus(Status status) {
        this.status = status;
    }
}
