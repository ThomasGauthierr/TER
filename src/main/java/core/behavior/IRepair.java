package core.behavior;

import core.ValueTimestamp;
import core.device.IActuator;

import java.util.List;

public interface IRepair<T> {

    boolean repair(List<T> data, List<IActuator> actuators, IRepairStrategy strategy);

}
