package core.device.sensor;

/**
 * Created by Alexis Couvreur on 15/06/2018.
 */
public interface IObservableSensor {
    void notifyObservers();

    void addObserver(ISensorObserver observer);

    void removeObserver(ISensorObserver observer);
}
