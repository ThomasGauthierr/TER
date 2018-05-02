package core;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import core.behavior.contract.ActionType;
import core.device.DataType;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Annuaire {
	
	private static Annuaire instance;
	public static Annuaire getInstance(){
		if(instance==null)
			instance = new Annuaire();
		return instance;
	}
	
	private HashMap<String, Information> annuaire;

	public class Information{
		private int dataType;
		private boolean isActuator;
		private int actionType;
		
		private Information(int dataType, int actionType){
			this.dataType=dataType;
			this.actionType=actionType;
			isActuator = actionType!=-1;
		}
		
		private Information(long dataType, long actionType) {
			this.dataType=Integer.parseInt(Long.toString(dataType));
			this.actionType=Integer.parseInt(Long.toString(actionType));
			isActuator = actionType!=-1;
		}

		public DataType getDataType() {
			return DataType.findFromId(dataType);
		}
		public boolean isActuator() {
			return isActuator;
		}
		public ActionType getActionType() {
			return ActionType.findFromId(actionType);
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
		JSONParser parser = new JSONParser();
		try {
			JSONObject tmp = (org.json.simple.JSONObject) parser.parse(new FileReader("Annuaire.json"));
			JSONArray a = (JSONArray) tmp.get("device");

			  for (Object o : a)
			  {
			    JSONObject information = (JSONObject) o;
			    annuaire.put((String) information.get("ID"), new Information((long) information.get("dataType")
			    		, (long) information.get("actionType")));
			    
			  }
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
	}
	
	
}
