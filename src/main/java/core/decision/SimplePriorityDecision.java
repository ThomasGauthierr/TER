package core.decision;

import core.behavior.context.IViolatedContext;
import core.behavior.contract.ActionType;

public class SimplePriorityDecision implements IDecisionMaker {

	public SimplePriorityDecision() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Action solve(IViolatedContext ctx) {
		// List<ISensor> witness = ctx.getWitnesses();
		Action action = new Action();
		ActionType aT = ctx.getActionType();
		/*List<IActuator> needToUse = new ArrayList<>();
		List<IActuator> needToOff = new ArrayList<>();
		if(aT==ActionType.INCREASE) {
			needToUse=ctx.getActuatorsThatIncrease(witness.get(0));
			needToOff=ctx.getActuatorsThatDecrease(witness.get(0));
			needToOff=needToOff.stream().filter(a -> (a.getState()!=IActuator.State.OFF)).collect(Collectors.toList());
			
		}else if(aT==ActionType.DECREASE) {
			needToUse=ctx.getActuatorsThatDecrease(witness.get(0));
			needToOff=ctx.getActuatorsThatIncrease(witness.get(0));
			needToOff=needToOff.stream().filter(a -> (a.getState()!=IActuator.State.OFF)).collect(Collectors.toList());
		}
		if(needToUse != null) {
			needToUse=needToUse.stream().filter(a -> (a.getState()!=IActuator.State.HIGH)).collect(Collectors.toList());
		
			needToUse.sort(new Comparator<IActuator>() {

				@Override
				public int compare(IActuator o1, IActuator o2) {
					Annuaire annuaire = Annuaire.getInstance();

					return annuaire.getInformationAbout(o2.getID()).getPriority() - annuaire.getInformationAbout(o1.getID()).getPriority();
				}
			});
		}
		if(needToOff != null) {
			needToOff.sort(new Comparator<IActuator>() {

				@Override
				public int compare(IActuator o1, IActuator o2) {
					Annuaire annuaire = Annuaire.getInstance();

	                return annuaire.getInformationAbout(o2.getID()).getPriority() - annuaire.getInformationAbout(o1.getID()).getPriority();
				}
			});
		}
		action.getMonitoredActuators().addAll(needToOff);
		action.getMonitoredActuators().addAll(needToUse);
		if(aT==ActionType.INCREASE) {
			for(int i = 0;i<needToOff.size();i++) {
				action.getActionTypes().add(ActionType.DECREASE);
			}
			for(int i = 0;i<needToUse.size();i++) {
				action.getActionTypes().add(ActionType.INCREASE);
			}
		}else if(aT==ActionType.DECREASE) {
			for(int i = 0;i<needToOff.size();i++) {
				action.getActionTypes().add(ActionType.INCREASE);
			}
			for(int i = 0;i<needToUse.size();i++) {
				action.getActionTypes().add(ActionType.DECREASE);
			}
		}*//*
		List<IActuator> responsible = ctx.getResponsibleList();
		responsible.sort(new Comparator<IActuator>() {

			@Override
			public int compare(IActuator o1, IActuator o2) {
				Annuaire annuaire = Annuaire.getInstance();

				return annuaire.getInformationAbout(o2.getIdentifier()).getPriority() - annuaire.getInformationAbout(o1.getIdentifier()).getPriority();
			}
		});
		action.actuators.addAll(responsible);
		for(IActuator a : action.actuators){
			if(a.getActionType()==aT) {
				action.states.add(IActuator.State.HIGH);
			}else {
				action.states.add(IActuator.State.OFF);
			}
		}*/
		
		
		return action;
	}

}
