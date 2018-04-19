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
}
