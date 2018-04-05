package core.device;

import core.ValueTimestamp;
import core.behavior.ContractImpl;
import core.behavior.IContract;
import core.behavior.IRepair;
import core.behavior.RepairImpl;

import java.util.ArrayList;
import java.util.List;

public class GroupImpl implements IGroup {

    private List<ISensor> sensors;
    private List<IActuator> actuators;
    private IContract<ValueTimestamp> contract;
    private IRepair<ValueTimestamp> repair;

    public GroupImpl(){
        sensors = new ArrayList<>();
        actuators = new ArrayList<>();
        contract = new ContractImpl<>();
        repair = new RepairImpl();
    }

    @Override
    public List<ISensor> getSensors() {
        return sensors;
    }

    @Override
    public List<IActuator> getActuators() {
        return actuators;
    }

    @Override
    public IContract<ValueTimestamp> getContract() {
        return contract;
    }

    @Override
    public void setRepair(IRepair<ValueTimestamp> repair) {

    }

    @Override
    public void repair() {
        // MAGIE
        repair.repair(null, actuators, null);
    }
}
