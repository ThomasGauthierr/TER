package core.behavior.contract;

import core.Message;
import core.behavior.context.ConcreteContext;
import core.behavior.context.IContext;
import core.behavior.context.IViolatedContext;
import core.behavior.context.ViolatedContext;
import core.device.DataType;

import java.util.List;
import java.util.Vector;
import java.util.function.Predicate;

/**
 * Created by Alexis Couvreur on 15/06/2018.
 */
public class ConcreteContract implements IContract {

    private String identifier;
    private IContext observedContext;
    private DataType dataType;
    private Predicate<Message> predicate;
    private Status status;
    private List<IContractObserver> observers;
    private IViolatedContext violatedContext;

    public ConcreteContract(String identifier, IContext observedContext, DataType dataType, Predicate<Message> predicate) {
        this.identifier = identifier;
        this.observedContext = observedContext;
        this.predicate = predicate;
        this.status = Status.PASS;
        this.dataType = dataType;
        this.observers = new Vector<>();
    }

    @Override
    public IContext getObservedContext() {
        return observedContext;
    }

    @Override
    public void update(IContext source, Object arg) {
        // received context update from CONCRETE context
        // predicate.test(source)

        ConcreteContext concreteContext = (ConcreteContext) source;
        Message lastMessage = (Message) arg;

        System.out.println("[" + getName() + "] Context " + source.getIdentifier() + " has been updated");

        if (lastMessage.getDatatype().equals(dataType)) {
            if (predicate.test(lastMessage)) {
                System.out.println("-->Not violated");
                if (status != Status.PASS) {
                    this.status = Status.PASS;
                    notifyObservers();
                }

            } else if (status != Status.VIOLATED) {
                violatedContext = new ViolatedContext(this, concreteContext, lastMessage.getSource(), lastMessage);
                this.status = Status.VIOLATED;
                System.out.println("-->Violated !");
                notifyObservers();
            }
        }


    }

    @Override
    public String getName() {
        return identifier;
    }

    @Override
    public Status getStatus() {
        return status;
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
        observers.forEach(o -> o.update(this, violatedContext));
    }
}
