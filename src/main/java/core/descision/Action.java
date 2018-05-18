package core.descision;

import core.behavior.contract.ActionType;
import core.device.actuator.IActuator;

import java.util.ArrayList;
import java.util.List;

public class Action {

	protected List<IActuator> actuators;
	protected List<IActuator.State> states;
	
	
	public List<IActuator> getActuators() {
		return actuators;
	}
	public List<IActuator.State> getActionTypes() {
		return states;
	}
	
	public Action() {
		states = new ArrayList<>();
		actuators = new ArrayList<>();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < actuators.size(); i++) {
			sb.append("[IActuator](").append(actuators.get(i)).append(") should ").append(states.get(i).name()).append("\n");
		}
		return "Actions to do : \n" + sb.toString();
	}
}
