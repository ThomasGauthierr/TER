package core.behavior.contract;

public interface ContractObserver {
	public void notifySuperContract(ContractEvent evt);
	public void setSuperContract(ISuperContract<?> s);
}
