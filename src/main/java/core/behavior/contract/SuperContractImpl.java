package core.behavior.contract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;

public class SuperContractImpl<T> implements ISuperContract<T> {


    private List<IContract<T>> contracts;
    private Predicate<T> predicate;
    private ActionType actionType;
    private HashMap<IContract<T>, Information> extraInfo;
    private ContractObserver observer;

    public SuperContractImpl() {
        this.predicate = t -> true;
        this.actionType = ActionType.OK;
        contracts = new ArrayList<>();
    }

    public SuperContractImpl(Predicate<T> predicate, ActionType actionType, ContractObserver observer){
        this.predicate = predicate;
        this.actionType = actionType;
        this.contracts = new ArrayList<>();
        this.extraInfo = new HashMap<>();
        this.observer = observer;
        this.observer.setSuperContract(this);
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
    public Information getInfo(IContract<T> contract) {
        return extraInfo.get(contract);
    }

    @Override
    public List<IContract<T>> getContracts() {
        return contracts;
    }

    @Override
    public void addContract(IContract<T> contract, Information info) {
        contracts.add(contract);
        extraInfo.put(contract, info);
    }

    @Override
    public void removeContract(IContract<T> contract) {
    	contracts.remove(contract);
    	extraInfo.remove(contract);
    }

	@Override
	public void addObserver(ContractObserver observer) {		
	}
}
