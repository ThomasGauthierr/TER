package core.behavior.contract;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alexis Couvreur on 13/06/2018.
 */
public class ContractManager {

    private Map<String, IContract> contracts;

    public ContractManager() {
        this.contracts = new HashMap<>();
    }

    public IContract getContract(String name) {
        return contracts.get(name);
    }

    public void addContract(IContract contract) {
        // if(contracts.containsKey(contract.getName()) {
        // TODO: vérifier intégrité du contrat
        contracts.put(contract.getName(), contract);
    }

    public void removeContract(IContract contract) {
        contracts.remove(contract.getName());
    }
}
