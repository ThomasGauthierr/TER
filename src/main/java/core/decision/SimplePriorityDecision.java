package core.decision;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import core.Annuaire;
import core.Message;
import core.behavior.context.ConcreteContext;
import core.behavior.context.IViolatedContext;
import core.behavior.context.ViolatedContext;
import core.behavior.contract.ActionType;
import core.behavior.contract.ConcreteContract;
import core.behavior.contract.IContract;
import core.device.DataType;
import core.device.actuator.IActuator;
import core.device.sensor.ISensor;

public class SimplePriorityDecision implements IDecisionMaker {

	public SimplePriorityDecision() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Action solve(IViolatedContext vx) {
		ViolatedContext ctx = (ViolatedContext) vx;
		ISensor witness = ctx.getWitness();
		ConcreteContext source = (ConcreteContext) ctx.getSource();
		Action action = new Action();
		DataType dT = witness.getDataType();
		ConcreteContract violatedContract = null;
		if(ctx.getViolatedContract() instanceof ConcreteContract){
			violatedContract = (ConcreteContract) ctx.getViolatedContract();
		}
			
		
		
		List<IActuator> responsible = new ArrayList<>();
		ctx.getMonitoredEntities();
		boolean toohigh = violatedContract.getPredicate().negate().test(new Message("test", 1000, 0, null, dT));
		for(IActuator a : source.getActuators()){
			if(a.getDataType()==dT){
				responsible.add(a);
			}
		}
		responsible.sort(new Comparator<IActuator>() {

			@Override
			public int compare(IActuator o1, IActuator o2) {
				Annuaire annuaire = Annuaire.getInstance();

				return annuaire.getInformationAbout(o2.getIdentifier()).getPriority() - annuaire.getInformationAbout(o1.getIdentifier()).getPriority();
			}
		});
		action.actuators.addAll(responsible);
		for(IActuator a : action.actuators){
			if(a.getActionType().equals(ActionType.INCREASE) && toohigh) {
				action.states.add(IActuator.State.OFF);
			}else if(a.getActionType().equals(ActionType.DECREASE) && !toohigh){
				action.states.add(IActuator.State.OFF);
			}else{
				action.states.add(IActuator.State.ON);
			}
		}
		
		
		return action;
	}

}
