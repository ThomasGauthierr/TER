package core.behavior.contract;

import java.util.List;
import java.util.function.Predicate;

public interface IContract<T> {

    Predicate<T> getPredicate();

    ActionType isRespected(T data);
    ActionType isRespected(List<T> data);

    ContractStatus getContractStatus();
    void setContractStatus(ContractStatus status);

    enum ContractStatus {
        OK,
        VIOLATED,
        REAPAIRING
    }

}
