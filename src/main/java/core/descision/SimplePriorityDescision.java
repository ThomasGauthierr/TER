package core.descision;

import java.util.List;

import core.behavior.context.IViolatedContext;
import core.device.DataType;
import core.device.actuator.IActuator;
import core.device.sensor.ISensor;

public class SimplePriorityDescision implements IDescisionMaker {

	public SimplePriorityDescision() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Action solve(IViolatedContext ctx) {
		List<ISensor> witness = ctx.getWitnesses();
		List<IActuator> responsible = ctx.getActuators();
		Action action = new Action();
		DataType dataType = witness.get(0).getDataType();
		
		return action;
	}

}
