package core.behavior.context;

public class WeatherStrategy implements IFakeValueStrategy {

    private final double delta;
    private final int averageDeg;

    public WeatherStrategy(int averageDeg, double delta) {
        this.averageDeg = averageDeg;
        this.delta = delta;
    }
    @Override
    public int getNextValue() {
        return (int) (averageDeg + (Math.random() * delta * 2) - delta);
    }
}
