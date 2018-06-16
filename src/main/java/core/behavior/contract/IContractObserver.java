package core.behavior.contract;

import java.util.List;

/**
 * Created by Alexis Couvreur on 16/06/2018.
 */
public interface IContractObserver {

    List<IContract> getObservedContracts();

    void update(IContract source, Object arg);

}
