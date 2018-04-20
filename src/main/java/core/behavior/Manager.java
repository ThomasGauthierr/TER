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
    private List<ContractListener> listeners;
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
            ActionType actionType = contract.isRespected(((ISensor) o).getData().get(0));
            System.out.println("Checking actionType of [Sensor:" + ((ISensor)o).getID() + "] -> " + actionType.name());
            if (! actionType.equals(ActionType.OK)) {
                switch (contract.getContractStatus()) {
                    case OK:
                        contract.setContractStatus(IContract.ContractStatus.VIOLATED);
                        fireUnrespectedContractEvent((ISensor) o, actionType);
                        break;
                    case VIOLATED:
                        // contract is violated start repairing
                        System.out.println("Contract is violated [Sensor:" + ((ISensor)o).getID() + " ...");
                        break;
                    case REAPAIRING:
                        // contract is repairing
                        System.out.println("Now repairing the contract ...");
                        // We assume this is repaired by magic voodo tricks so yeah
                        contract.setContractStatus(IContract.ContractStatus.OK);
                        break;
                }
            } else {
                fireRespectedContractEvent((ISensor) o, actionType);
            }
        }
    }

    private void fireRespectedContractEvent(ISensor sensor, ActionType actionType) {
        System.out.println("Fired event 'RespectedContractEvent' to all listeners");

        // TODO: change parameter ActionType to real action of the event
        ContractEvent evt = new ContractEvent(this, sensor, actionType);
        for (RespectedContractListener listener : listeners) {
            listener.respectedContractEventReceived(evt);
        }
    }

    private void fireUnrespectedContractEvent(ISensor sensor, ActionType actionType) {
        System.out.println("Fired event 'UnrespectedContractEvent' to all listeners");

        // TODO: change parameter ActionType to real action of the event
        ContractEvent evt = new ContractEvent(this, sensor, actionType);
        for (UnrespectedContractListener listener : listeners) {
            listener.unrespectedContractEventReceived(evt);
        }
    }

    public void addContractListener(ContractListener l) {
        this.listeners.add(l);
    }

    public void removeContractListener(ContractListener l) {
        this.listeners.remove(l);
    }

    public void repairing() {
        contract.setContractStatus(IContract.ContractStatus.REAPAIRING);
    }

}
