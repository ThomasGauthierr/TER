package core.descision;

import java.util.ArrayList;
import java.util.List;

import core.behavior.contract.ActionType;
import core.device.actuator.IActuator;

public class Action {

	protected List<IActuator> actuators;
	protected List<ActionType> actionTypes;
	
	
	public List<IActuator> getActuators() {
		return actuators;
	}
	public List<ActionType> getActionTypes() {
		return actionTypes;
	}
	
	public Action() {
		actionTypes = new ArrayList<>();
		actuators = new ArrayList<>();
	}
	
	
	
	
	
}
