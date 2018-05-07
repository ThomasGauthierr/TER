package core.behavior.contract;

import core.Message;
import core.device.sensor.ISensor;
import core.device.sensor.Sensor;

import java.util.Observable;
import java.util.function.Predicate;

public class BasicContract extends AbstractContract{

    public BasicContract(Predicate<Observable> predicate) {
        super(predicate);
    }

    @Override
    public void update(Observable o, Object args) {
        if(o instanceof ISensor) {
            Sensor sensor = (Sensor) o;
            if(getPredicate().test(sensor)) {
                // Everything is ok
                notifyObservers();
            } else {
                setStatus(Status.VIOLATED);
                setChanged();
                notifyObservers(this);
            }

        } else if(o instanceof IContract) {

        } else {
            System.out.println("contract update dunno what i received");
        }
    }

}
