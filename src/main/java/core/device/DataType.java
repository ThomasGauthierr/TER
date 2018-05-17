package core.device;

public enum DataType {
    TEMPERATURE(0),
    LIGHT(1);

    private final int id;

    DataType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
    
	public static DataType findFromId(int id){
		switch (id){
		case 0:
			return TEMPERATURE;
		case 1:
			return LIGHT;
		}
		return null;
	}

    public static DataType findFromId(String id) {
        return findFromId(Integer.parseInt(id));
    }
}
