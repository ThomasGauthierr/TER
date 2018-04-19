package core.device;

public enum DataType {
    Temperature(0),
    Light(1);

    private final int id;

    DataType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
