package core.behavior;

import core.ValueTimestamp;
import core.device.IActuator;

import java.util.List;

public class RepairImpl implements IRepair<ValueTimestamp> {

    @Override
    public boolean repair(List<ValueTimestamp> data, List<IActuator> actuators, IRepairStrategy strategy) {
        // La magie
        return false;
    }
}
