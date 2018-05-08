package core.behavior.contract;

import java.util.Observable;

public class ContractImpl extends AbstractContract{

    public ContractImpl(String name, ContractPredicate predicate) {
        super(name, predicate);
    }

    @Override
    public void update(Observable o, Object args) {

        if (!getPredicate().getPredicate().test(o)) {
            if(getPredicate().getActionType().equals(ActionType.DECREASE)) {
                setStatus(Status.VIOLATED_DECREASING);
            } else if (getPredicate().getActionType().equals(ActionType.INCREASE)) {
                setStatus(Status.VIOLATED_INCREASING);
            }
            setChanged();
        }
        notifyObservers(o);
    }

}
