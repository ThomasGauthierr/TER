package core.behavior;

import core.Message;
import core.behavior.contract.*;
import core.device.sensor.ISensor;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Alexis Couvreur on 12/04/2018.
 */
public class Manager implements Observer {
    // Actuators
    private List<UnrespectedContractListener> listeners;
    private IContract<Message> contract;

    public Manager() {
        this.listeners = new ArrayList<>();
        this.contract = new ContractImpl<>();
    }

    public Manager(IContract<Message> contract) {
        this.listeners = new ArrayList<>();
        this.contract = contract;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof ISensor) {
            if (!contract.isRespected(((ISensor) o).getData())) {
                fireUnrespectedContractEvent((ISensor) o);
            }
        }
    }

    private void fireUnrespectedContractEvent(ISensor sensor) {
        System.out.println("Fired event 'UnrespectedContractEvent' to all listeners");

        // TODO: change parameter ActionType to real action of the event
        UnrespectedContractEvent evt = new UnrespectedContractEvent(this, sensor, ActionType.DECREASE);
        for (UnrespectedContractListener listener : listeners) {
            listener.unrespectedContractEventReceived(evt);
        }
    }

    public void addUnrespectedContractListener(UnrespectedContractListener l) {
        this.listeners.add(l);
    }

    public void removeUnrespectedContractListener(UnrespectedContractListener l) {
        this.listeners.remove(l);
    }

}
