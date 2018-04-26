package core;

import java.util.HashMap;

import core.behavior.contract.ActionType;
import core.device.DataType;

public class Annuaire {
	
	private static Annuaire instance;
	public static Annuaire getInstance(){
		if(instance==null)
			instance = new Annuaire();
		return instance;
	}
	
	private HashMap<String, Information> annuaire;

	public class Information{
		private DataType dataType;
		private boolean isActuator;
		private ActionType actionType;
		
		private Information(DataType dataType, ActionType actionType){
			this.dataType=dataType;
			this.actionType=actionType;
			isActuator = actionType!=null;
		}
		
		public DataType getDataType() {
			return dataType;
		}
		public boolean isActuator() {
			return isActuator;
		}
		public ActionType getActionType() {
			return actionType;
		}
		
	}
	
	private Annuaire(){
		annuaire = new HashMap<>();
		updateAnnuaire();
	}
	
	public Information getInfo(String ID){
		return annuaire.get(ID);
	}

	public void updateAnnuaire() {
		annuaire.clear();
		// TODO Parse file in annuaire
	}
	
	
}
