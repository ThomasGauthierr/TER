package core.behavior.contract;

import core.Message;
import core.behavior.context.ConcreteContext;
import core.behavior.context.IContext;
import core.behavior.context.IViolatedContext;
import core.behavior.context.ViolatedContext;
import core.behavior.contract.builder.ArithmeticCondition;
import core.decision.Action;
import core.decision.SimplePriorityDecision;
import core.device.DataType;
import core.device.actuator.IActuator;

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
    private ArithmeticCondition condition;
    private Status status;
    private List<IContractObserver> observers;
    private IViolatedContext violatedContext;
    private SimplePriorityDecision decisionMaker;

    public ConcreteContract(String identifier, IContext observedContext, DataType dataType, Predicate<Message> predicate, ArithmeticCondition condition) {
        this.identifier = identifier;
        this.observedContext = observedContext;
        this.predicate = predicate;
        this.status = Status.PASS;
        this.dataType = dataType;
        this.observers = new Vector<>();
        this.condition = condition;
        decisionMaker = new SimplePriorityDecision();
    }
    
    public Predicate<Message> getPredicate(){
		return predicate;
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
                Action action = decisionMaker.solve(violatedContext);

                for (int i = 0; i < action.getActuators().size(); i++) {
                    if (action.getActionTypes().get(i) == IActuator.State.ON) {
                        action.getActuators().get(i).activate();
                        //System.out.println("activating actuator " + action.getActuators().get(i).getIdentifier());
                    } else {
                        action.getActuators().get(i).deactivate();
                        //System.out.println("deactivating actuator " + action.getActuators().get(i).getIdentifier());
                    }
                }

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

    public ArithmeticCondition getArithmeticCondition() {
        return condition;
    }
}
