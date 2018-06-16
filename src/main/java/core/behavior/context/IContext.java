package core.behavior.context;

import java.util.List;

public interface IContext {

    List<? extends MonitoredEntity> getMonitoredEntities();

    void notifyObservers();

    void addObserver(IContextObserver observer);

    void removeObserver(IContextObserver observer);

    String getIdentifier();

}
