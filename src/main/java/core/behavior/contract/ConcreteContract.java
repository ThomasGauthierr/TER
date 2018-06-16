package core.behavior.contract;

import core.Message;
import core.behavior.context.ConcreteContext;
import core.behavior.context.IContext;
import core.behavior.context.IViolatedContext;
import core.behavior.context.ViolatedContext;
import core.device.DataType;

import java.util.function.Predicate;

/**
 * Created by Alexis Couvreur on 15/06/2018.
 */
public class ConcreteContract implements IContract {

    private String identifier;
    private IContext observedContext;
    private DataType dataType;
    private Predicate<Message> predicate;

    public ConcreteContract(String identifier, IContext observedContext, DataType dataType, Predicate<Message> predicate) {
        this.identifier = identifier;
        this.observedContext = observedContext;
        this.predicate = predicate;
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

        System.out.println("received a context update from " + source.getIdentifier());

        if (predicate.test(lastMessage)) {
            System.out.println("Not violated");
        } else {
            IViolatedContext violatedContext = new ViolatedContext(this, concreteContext, lastMessage.getSource(), lastMessage);
            System.out.println("Violated !");
        }

    }

    @Override
    public String getName() {
        return identifier;
    }
}
