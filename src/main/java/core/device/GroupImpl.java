package core.device;

import core.Message;
import core.behavior.ContractImpl;
import core.behavior.IContract;
import core.behavior.IRepair;
import core.behavior.RepairImpl;

import java.util.ArrayList;
import java.util.List;

public class GroupImpl implements IGroup {

    private List<ISensor> sensors;
    private List<IActuator> actuators;
    private IContract<Message> contract;
    private IRepair<Message> repair;

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
    public IContract<Message> getContract() {
        return contract;
    }

    @Override
    public void setRepair(IRepair<Message> repair) {

    }

    @Override
    public void repair() {
        // MAGIE
        repair.repair(null, actuators, null);
    }
}
