package core.behavior.contract;

import core.behavior.context.IContextObserver;
import core.behavior.context.MonitoredEntity;


public interface IContract extends IContextObserver, MonitoredEntity {

    String getName();

    Status getStatus();

    void addObserver(IContractObserver observer);

    void removeObserver(IContractObserver observer);

    void notifyObservers();

    enum Status {
        PASS,
        VIOLATED,
        FIXING
    }

}
