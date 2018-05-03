package core.behavior.contract;

import core.device.sensor.ISensor;

import java.util.List;
import java.util.Observer;
import java.util.function.Predicate;

public interface IContract extends ITree<IContract>, Observer {

    List<ContractObserver> getObservers();
    List<ContractListener> getListeners();

    void addObserver(ContractObserver contractObserver);
    void addListener(ContractListener contractListener);

    Status getStatus();

    enum Status {
        OK,
        VIOLATED,
        REPAIRING
    }

}
