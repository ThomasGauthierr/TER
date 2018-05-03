package core.behavior;

import core.Message;
import core.behavior.contract.*;
import core.device.sensor.ISensor;

import java.util.*;
import java.util.function.Predicate;

/**
 * Created by Alexis Couvreur on 12/04/2018.
 */
public class Manager implements Observer, ISuperContract<Message> {
    // Actuators
    private List<ContractListener> listeners;
    private List<IContract<Message>> contracts;
	private Predicate<Message> predicate;
	private ActionType actionType;
	private HashMap<IContract<Message>, Information> extraInfo;

    public Manager(Predicate<Message> predicate) {
        this.listeners = new ArrayList<>();
        this.contracts = new ArrayList<>();
        extraInfo = new HashMap<>();
        this.predicate = predicate;
    }

    public Manager(List<IContract<Message>>  contracts, Predicate<Message> predicate) {
        this.listeners = new ArrayList<>();
        this.contracts = contracts;
        this.predicate = predicate;
        extraInfo = new HashMap<>();
        for(IContract<Message> contract : contracts) {
        	extraInfo.put(contract, new Information(0));
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        for(IContract<Message> contract : contracts) {
        	if (o instanceof ISensor) {
                List<Message> msg = ((ISensor) o).getData();
                if (msg.size() == 0)
                    return;


                ActionType actionType = contract.isRespected(msg.get(msg.size() - 1));
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
                            // TODO: check if there is someone who can fix this
                            contract.setContractStatus(IContract.ContractStatus.REAPAIRING);
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
    	for(IContract<Message> contract : contracts) {
    		contract.setContractStatus(IContract.ContractStatus.REAPAIRING);
    	}
    }

	@Override
	public Predicate<Message> getPredicate() {
		return predicate;
	}

	@Override
	public ActionType isRespected(Message data) {
		 return predicate.test(data) ? ActionType.OK:actionType;
	}

	@Override
	public ActionType isRespected(List<Message> data) {
		for(Message e : data){
            if(! isRespected(e).equals(ActionType.OK))
                return actionType;
        }
        return ActionType.OK;
	}

	@Override
	public void addObserver(ContractObserver observer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ContractStatus getContractStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setContractStatus(ContractStatus status) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<IContract<Message>> getContracts() {
		return contracts;
	}

	@Override
	public void addContract(IContract<Message> contract, Information info) {
		contracts.add(contract);
		extraInfo.put(contract, info);
		
	}

	@Override
	public void removeContract(IContract<Message> contract) {
		contracts.remove(contract);
		extraInfo.remove(contract);
		
	}

	@Override
	public Information getInfo(IContract<Message> contract) {
		return extraInfo.get(contract);
	}

}
