package core.device;

import core.ValueTimestamp;
import core.behavior.IContract;
import core.behavior.IRepair;

import java.util.List;

public interface IGroup {

    List<ISensor> getSensors();
    List<IActuator> getActuators();
    IContract<ValueTimestamp> getContract();
    void setRepair(IRepair<ValueTimestamp> repair);
    void repair();

}
