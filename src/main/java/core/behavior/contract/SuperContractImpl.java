package core.behavior.contract;

import core.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class SuperContractImpl<T> implements ISuperContract<T> {

    private List<IContract<T>> contracts;
    private Predicate<T> predicate;
    private ActionType actionType;

    public SuperContractImpl() {
        this.predicate = t -> true;
        this.actionType = ActionType.OK;
        contracts = new ArrayList<>();
    }

    public SuperContractImpl(Predicate<T> predicate, ActionType actionType){
        this.predicate = predicate;
        this.actionType = actionType;
        this.contracts = new ArrayList<>();
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
        return null;
    }

    @Override
    public void setContractStatus(ContractStatus status) {

    }

    @Override
    public List<IContract> getContracts() {
        return null;
    }

    @Override
    public void addContract(IContract<T> contract) {
        contracts.add(contract);
    }

    @Override
    public void removeContract(IContract<T> contract) {

    }
}
