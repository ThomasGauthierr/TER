package core.descision;

import java.util.List;

import core.behavior.contract.ActionType;
import core.device.actuator.IActuator;

public abstract class Action {

	protected List<IActuator> actuators;
	protected List<ActionType> actionTypes;
	
	
}
