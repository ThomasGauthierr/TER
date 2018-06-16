package core.behavior.context;

import core.Message;
import core.behavior.contract.ActionType;
import core.behavior.contract.IContract;
import core.device.sensor.ISensor;

public interface IViolatedContext extends IContext {

    IContract getViolatedContract();

    ISensor getWitness();

    ActionType getActionType();

    Message getViolatingMessage();

}
