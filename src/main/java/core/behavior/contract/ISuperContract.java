package core.behavior.contract;

import java.util.List;

public interface ISuperContract<T> extends IContract<T> {
	
	public class Information{
		public int priority;

		public Information(int priority) {
			this.priority = priority;
		}
		
	}

	List<IContract<T>> getContracts();

    void addContract(IContract<T> contract);
    void removeContract(IContract<T> contract);

	Information getInfo(IContract<T> contract);
}
