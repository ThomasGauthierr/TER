package core.behavior.context;

import core.Message;
import core.device.actuator.IActuator;
import core.device.sensor.ISensor;
import core.device.sensor.ISensorObserver;

import java.util.List;
import java.util.Vector;

/**
 * Created by Alexis Couvreur on 15/06/2018.
 */
public class ConcreteContext implements IContext, ISensorObserver {

    private String identifier;
    private List<IContextObserver> observers;
    private List<ISensor> observedSensors;
    private List<IActuator> actuators;

    private ISensor lastUpdated;
    private Message lastMessage;

    public ConcreteContext(String identifier) {
        this.identifier = identifier;
        this.observers = new Vector<>();
        this.observedSensors = new Vector<>();
        this.actuators = new Vector<>();
    }

    @Override
    public List<ISensor> getMonitoredEntities() {
        return observedSensors;
    }

    @Override
    public void notifyObservers() {
        // observers.forEach(); TODO: notify
        observers.forEach(o -> o.update(this, lastMessage));
    }

    //@Override
    public List<IContextObserver> getObservers() {
        return observers;
    }

    public List<IActuator> getActuators() {
        return actuators;
    }

    public void addSensor(ISensor sensor) {
        this.observedSensors.add(sensor);
        sensor.addObserver(this);
    }

    public void addActuator(IActuator actuator) {
        this.actuators.add(actuator);
    }

    @Override
    public void addObserver(IContextObserver observer) {
        this.observers.add(observer);
    }

    @Override
    public void removeObserver(IContextObserver observer) {
        this.observers.remove(observer);
    }

    @Override
    public String getIdentifier() {
        return identifier;
    }

    @Override
    public void update(ISensor source, Message newMessage) {
        // We have to notify the contracts that our context has changed
        // observers.forEach(o -> o.update(this, newMessage));
        this.lastUpdated = source;
        this.lastMessage = newMessage;

        System.out.println("[ConcreteContext] received -> " + newMessage.toString());
        notifyObservers();
    }
}
