package core.behavior;

import java.util.List;
import java.util.function.Predicate;

public interface IContract<T> {

    List<Predicate<T>> getPredicates();

    void addPredicate(Predicate<T> predicate);

    void removePredicate(Predicate<T> predicate);

    boolean isRespected(List<T> data);

}
