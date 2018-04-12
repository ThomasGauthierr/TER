package core.behavior;

import core.device.actuator.IActuator;

import java.util.List;

public interface IRepair<T> {

    boolean repair(List<T> data, List<IActuator> actuators, IRepairStrategy strategy);

}
