package core.behavior.contract;

import java.util.Observable;
import java.util.function.Predicate;

public class ContractPredicate {
    private ActionType actionType;
    private Predicate<Observable> predicate;

    public ContractPredicate(Predicate<Observable> predicate, ActionType actionType) {
        this.actionType = actionType;
        this.predicate = predicate;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public Predicate<Observable> getPredicate() {
        return predicate;
    }
}
