package core.descision;

import core.Annuaire;
import core.behavior.context.IViolatedContext;
import core.behavior.contract.ActionType;
import core.device.actuator.IActuator;
import core.device.sensor.ISensor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SimplePriorityDescision implements IDescisionMaker {

	public SimplePriorityDescision() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Action solve(IViolatedContext ctx) {
		List<ISensor> witness = ctx.getWitnesses();
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
		action.getActuators().addAll(needToOff);
		action.getActuators().addAll(needToUse);
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
		}*/
		List<IActuator> responsible = ctx.getResponsibleList();
		responsible.sort(new Comparator<IActuator>() {

			@Override
			public int compare(IActuator o1, IActuator o2) {
				Annuaire annuaire = Annuaire.getInstance();

				return annuaire.getInformationAbout(o2.getID()).getPriority() - annuaire.getInformationAbout(o1.getID()).getPriority();
			}
		});
		action.actuators.addAll(responsible);
		if(aT==ActionType.INCREASE) {
			for(int i = 0;i<responsible.size();i++) {
				action.getActionTypes().add(ActionType.INCREASE);
			}
		}else if(aT==ActionType.DECREASE) {
			for(int i = 0;i<responsible.size();i++) {
				action.getActionTypes().add(ActionType.DECREASE);
			}
		}
		
		
		return action;
	}

}
