package core.behavior;

import core.Message;
import core.device.sensor.ISensor;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.function.Predicate;

/**
 * Created by Alexis Couvreur on 12/04/2018.
 */
public class Manager implements Observer {

    public Manager() {
        this.listeners = new ArrayList<>();
        this.contract = new ContractImpl<>();
        contract.addPredicate(new Predicate<Message>() {
            @Override
            public boolean test(Message message) {
                return message.getValue() < 5;
            }
        });
    }

    public Manager(IContract<Message> contract) {
        this.listeners = new ArrayList<>();
        this.contract = contract;
    }

    // Actuators
    private List<UnrespectedContractListener> listeners;
    private IContract<Message> contract;

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof ISensor) {
            if (!contract.isRespected(((ISensor) o).getData())) {
                // TODO: trigger event here about actuators
                fireUnrespectedContractEvent((ISensor) o);
            }
        }
    }

    private void fireUnrespectedContractEvent(ISensor sensor) {
        System.out.println("fireUnrespectedContractEvent");
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
