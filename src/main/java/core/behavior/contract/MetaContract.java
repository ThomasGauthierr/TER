package core.behavior.contract;

import core.behavior.context.IContext;
import core.behavior.context.IContextObserver;
import core.behavior.context.MetaContext;

import java.util.function.Predicate;

/**
 * Created by Alexis Couvreur on 14/06/2018.
 */
public class MetaContract implements IContract, IContextObserver {

    private String name;
    private MetaContext contextObserved;
    private Predicate<IContext> metaPredicate;

    public MetaContract(String name, MetaContext contextObserved) {

        this.name = name;
        this.contextObserved = contextObserved;

    }

    public void update(IContext source, Object arg) {
        // Proviens uniquement de MetaContext

    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public IContext getObservedContext() {
        return contextObserved;
    }
}
