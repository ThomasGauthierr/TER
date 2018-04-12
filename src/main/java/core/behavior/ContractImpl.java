package core.behavior;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class ContractImpl<T> implements IContract<T> {

    private List<Predicate<T>> predicates;

    public ContractImpl(){
        predicates = new ArrayList<>();
    }

    @Override
    public List<Predicate<T>> getPredicates() {
        return predicates;
    }

    @Override
    public void addPredicate(Predicate<T> predicate) {
        predicates.add(predicate);
    }

    @Override
    public void removePredicate(Predicate<T> predicate) {
        predicates.remove(predicate);
    }

    @Override
    public boolean isRespected(List<T> data) {

        for(Predicate<T> p : predicates) {
            for(T e : data){
                if(!p.test(e)){
                    return false;
                }
            }
        }
        return true;
    }
}
