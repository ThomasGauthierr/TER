package core.behavior.contract;

import java.util.List;

public interface ISuperContract<T> extends IContract<T> {

    List<IContract> getContracts();

    void addContract(IContract<T> contract);
    void removeContract(IContract<T> contract);
}
