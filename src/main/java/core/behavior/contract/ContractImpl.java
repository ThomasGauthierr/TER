package core.behavior.contract;

import java.util.List;
import java.util.function.Predicate;

public class ContractImpl<T> implements IContract<T> {

    private Predicate<T> predicate;
    private ActionType actionType;
    private ContractStatus status;

    public ContractImpl() {
        this.predicate = t -> true;
        this.actionType = ActionType.OK;
        this.status = ContractStatus.OK;
    }

    public ContractImpl(Predicate<T> predicate, ActionType actionType){
        this.predicate = predicate;
        this.actionType = actionType;
        this.status = ContractStatus.OK;
    }

    @Override
    public Predicate<T> getPredicate() {
        return predicate;
    }

    @Override
    public ActionType isRespected(T data) {
        return predicate.test(data) ? ActionType.OK:actionType;
    }

    @Override
    public ActionType isRespected(List<T> data) {
            for(T e : data){
                if(! isRespected(e).equals(ActionType.OK))
                    return actionType;
            }
        return ActionType.OK;
    }

    @Override
    public ContractStatus getContractStatus() {
        return status;
    }

    @Override
    public void setContractStatus(ContractStatus status) {
        this.status = status;
    }
}
