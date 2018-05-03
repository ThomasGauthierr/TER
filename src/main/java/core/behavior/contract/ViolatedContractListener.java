package core.behavior.contract;

/**
 * Created by Alexis Couvreur on 12/04/2018.
 */
public interface ViolatedContractListener {
    void onViolatedContract(ContractEvent evt);
}
