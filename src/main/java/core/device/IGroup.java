package core.device;

import core.Message;
import core.behavior.contract.IContract;
import core.behavior.IRepair;
import core.device.actuator.IActuator;
import core.device.sensor.ISensor;

import java.util.List;

public interface IGroup {

    List<ISensor> getSensors();
    List<IActuator> getActuators();
    IContract<Message> getContract();
    void setRepair(IRepair<Message> repair);
    void repair();

}
