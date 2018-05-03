package core.behavior.contract;

public interface ContractObserver {
	void notifySuperContract(ContractEvent evt);
	void setSuperContract(ISuperContract<?> s);
}
