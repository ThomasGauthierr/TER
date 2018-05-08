package core.behavior.contract;

import java.util.Observable;
import java.util.Observer;
import java.util.function.Predicate;

public interface IContract extends Observer {

    String getName();

    void addObservable(Observable observable);
    void deleteObservable(Observable observable);
    void deleteObservables();
    int countObservables();

    ContractPredicate getPredicate();

    Status getStatus();

    int getNice();

    enum Status {
        OK,
        VIOLATED_INCREASING,
        VIOLATED_DECREASING,
        REPAIRING;

        public boolean isViolated() {
            return this.equals(Status.VIOLATED_DECREASING) || this.equals(Status.VIOLATED_INCREASING);
        }
    }

}
