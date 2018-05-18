package core.behavior.context;

import core.device.DataType;
import core.device.actuator.IActuator;
import core.device.sensor.ISensor;

import java.util.List;
import java.util.OptionalDouble;

public interface IContext extends ISensorListener {

    List<ISensor> getSensors();
    List<IActuator> getActuators();

    List<ISensor> getSensorsOf(DataType dt);
    List<IActuator> getActuatorsOf(DataType dt);

    List<ISensor> getSensorsDecreasedBy(IActuator actuator);

    List<ISensor> getSensorsIncreasedBy(IActuator actuator);

    List<IActuator> getActuatorsThatDecrease(ISensor sensor);

    List<IActuator> getActuatorsThatIncrease(ISensor sensor);

    List<IContextListener> getListeners();

    void addListener(IContextListener listener);

    OptionalDouble getValueOf(DataType dt);

    String getIdentifier();


}
