package core;

import core.behavior.context.ConcreteContext;
import core.behavior.contract.ActionType;
import core.device.DataType;
import core.device.actuator.IActuator;
import core.device.sensor.ISensor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Annuaire {

	/* Singleton instance */
	private static Annuaire instance;
	private Map<String, Information> annuaire;

	/* Private constructor */
	private Annuaire() {
		this.annuaire = new HashMap<>();
	}

	/* Singleton consistency */
	public static Annuaire getInstance(){
		if(instance==null)
			instance = new Annuaire();
		return instance;
	}

	public Information getInformationAbout(String id) {
		return annuaire.get(id);
	}

	public void addSensor(ISensor sensor, ConcreteContext context) {
		if (!context.getMonitoredEntities().contains(sensor))
			return;

		annuaire.put(sensor.getIdentifier(), new Information(sensor.getIdentifier(), context.getIdentifier(), sensor.getDataType(), null));
	}

	public void addActuator(IActuator actuator, ConcreteContext context) {
		if (!context.getActuators().contains(actuator))
			return;

		annuaire.put(actuator.getIdentifier(), new Information(actuator.getIdentifier(), context.getIdentifier(), actuator.getDataType(), actuator.getActionType()));
	}

	public void populateFromFile(String fileName) {
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(new FileReader(fileName));
			JSONArray annuaireList = (JSONArray) ((JSONObject) obj).get("devices");
			System.out.println(annuaireList);
			annuaireList.forEach(v -> parseDeviceObject((JSONObject) v));
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
	}

	private void parseDeviceObject(JSONObject device) {
		//JSONObject deviceObject = (JSONObject) device.get("device");

		String id = (String) device.get("ID");

		String contextName = (String) device.get("contextName");

		String dataType = (String) device.get("dataType");

		String actionType = (String) device.get("actionType");

		annuaire.put(id, new Information(
				id,
				contextName,
				dataType,
				actionType.equals("-1") ? null : actionType
		));
	}

	public void saveToFile(String fineName) {
		// TODO
	}


	public class Information{

		private DataType dataType;
		private ActionType actionType;
		private String id;
		private String contextName;
		private int priority;

		private Information(String id, String contextName, DataType dataType, ActionType actionType) {
			this.id = id;
			this.contextName = contextName;
			this.dataType = dataType;
			this.actionType = actionType;
            this.priority = 0;
		}

		private Information(String id, String contextName, String dataType, String actionType) {
			this.id = id;
			this.contextName = contextName;
			this.dataType = DataType.findFromId(dataType);
			this.actionType = actionType == null ? null : ActionType.findFromId(actionType);
		    this.priority = 0;
		}

		private Information(String id, String contextName, Integer dataType, Integer actionType) {
			this.id = id;
			this.contextName = contextName;
			this.dataType = DataType.findFromId(dataType);
			this.actionType = actionType == null ? null : ActionType.findFromId(actionType);
            this.priority = 0;
		}

		public DataType getDataType() {
			return dataType;
		}

		public ActionType getActionType() {
			return actionType;
		}

		public String getId() {
			return id;
		}

		public String getContextName() {
			return contextName;
		}

		public boolean isActuator() {
			return actionType == null;
		}

		public int getPriority() {
		    return priority;
        }
	}
}
