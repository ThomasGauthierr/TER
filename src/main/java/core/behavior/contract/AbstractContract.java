package core.behavior.contract;

import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

public abstract class AbstractContract extends Observable implements IContract  {

    private Vector<Observable> observables;
    private Vector<Observer> observers;
    private ContractPredicate predicate;
    private Status status;
    private String name;
    private int nice;
    public AbstractContract(String name, ContractPredicate predicate) {
        this.predicate = predicate;
        this.status = Status.OK;
        this.name = name;
        observables = new Vector<>();
        observers = new Vector<>();
        nice = 1;
    }

    public AbstractContract(String name, ContractPredicate predicate, int nice) {
        this.predicate = predicate;
        this.status = Status.OK;
        this.name = name;
        observables = new Vector<>();
        observers = new Vector<>();
        if (nice < 1) {
            this.nice = 1;
        }
        else {
            this.nice = nice;
        }
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

    public int getNice() {
        return nice;
    }

    protected void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public synchronized void addObserver(Observer o) {
        if (o == null)
            throw new NullPointerException();

        if (o instanceof AbstractContract) {
        }

        if (!observers.contains(o)) {
            observers.addElement(o);
        }
    }

    @Override
    public synchronized void deleteObserver(Observer o) {
        observers.removeElement(o);
    }

    @Override
    public void notifyObservers() {
        notifyObservers(null);
    }

    @Override
    public void notifyObservers(Object arg) {
        Object[] arrLocal;

        synchronized (this) {
            if (!hasChanged())
                return;
            arrLocal = observers.toArray();
            clearChanged();
        }

        for (int i = arrLocal.length-1; i>=0; i--)
            ((Observer)arrLocal[i]).update(this, arg);
    }

    @Override
    public synchronized void deleteObservers() {
        observers.removeAllElements();
    }

    @Override
    public synchronized int countObservers() {
        return observers.size();
    }
}
