package core.behavior.contract;

public enum ActionType {
	INCREASE(0),
	DECREASE(1),
	NONE(2),
	OK(3);

	private final int id;

	ActionType(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
	
	public static ActionType findFromId(int id){
		switch (id){
		case 0:
			return INCREASE;
		case 1:
			return DECREASE;
		case 3:
			return OK;
		default:
			return NONE;
		}
	}
}
