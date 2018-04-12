package core.device;

import core.Message;
import core.behavior.IContract;
import core.behavior.IRepair;

import java.util.List;

public interface IGroup {

    List<ISensor> getSensors();
    List<IActuator> getActuators();
    IContract<Message> getContract();
    void setRepair(IRepair<Message> repair);
    void repair();

}
