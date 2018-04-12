package core.behavior;

import core.Message;
import core.device.sensor.ISensor;

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

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof ISensor) {
            if (!contract.isRespected(((ISensor) o).getData())) {
                // TODO: trigger event here about actuators
                fireUnrespectedContractEvent();
            }
        }
    }

    private void fireUnrespectedContractEvent() {

        UnrespectedContractEvent evt = new UnrespectedContractEvent(this);

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
