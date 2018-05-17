package core.behavior.context;

import core.device.actuator.Actuator;
import core.device.actuator.IActuator;
import core.device.sensor.ISensor;

import java.util.List;
import java.util.Optional;

public interface IContext {

    List<ISensor> getSensors();
    List<IActuator> getActuators();

    String getIdentifier();


}
