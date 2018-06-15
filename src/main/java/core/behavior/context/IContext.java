package core.behavior.context;

import java.util.List;

public interface IContext {

    List<? extends MonitoredEntity> getMonitoredEntities();
    /*List<IActuator> getMonitoredActuators();
    List<IContract> getMonitoredContracts();

    List<ISensor> getSensorsOf(DataType dt);
    List<IActuator> getActuatorsOf(DataType dt);*/

    /*
    List<ISensor> getSensorsDecreasedBy(IActuator actuator);

    List<ISensor> getSensorsIncreasedBy(IActuator actuator);

    List<IActuator> getActuatorsThatDecrease(ISensor sensor);

    List<IActuator> getActuatorsThatIncrease(ISensor sensor);
*/

    void notifyObservers();

    void addObserver(IContextObserver observer);

    void removeObserver(IContextObserver observer);

    String getIdentifier();

}
