package core.behavior;

import core.Message;
import core.device.actuator.IActuator;

import java.util.List;

public class RepairImpl implements IRepair<Message> {

    @Override
    public boolean repair(List<Message> data, List<IActuator> actuators, IRepairStrategy strategy) {
        // La magie
        return false;
    }
}
