package core.behavior.contract;


import core.behavior.contract.builder.ArithmeticCondition;

public enum ActionType {
	INCREASE(0),
	DECREASE(1),
	NONE(2),
	OK(3);

	private final int id;

	ActionType(int id) {
		this.id = id;
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

    public static ActionType findFromId(String id) {
        switch (id) {
            case "increases":
                return INCREASE;
            case "decreases":
                return DECREASE;
            case "none":
                return NONE;
            case "ok":
                return OK;
        }
        return null;
    }

    public static ActionType fromArithmeticCondition(ArithmeticCondition ac) {
        switch (ac) {
            case LOWER_THAN:
            case LOWER_THAN_OR_EQUAL_TO:
                return INCREASE;
            case HIGHER_THAN:
            case HIGHER_THAN_OR_EQUAL_TO:
                return DECREASE;
        }
        return NONE;
    }

    public int getId() {
        return id;
    }
}
