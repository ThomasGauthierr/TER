package core.behavior;

import core.ValueTimestamp;

import java.util.List;
import java.util.function.Predicate;

public interface IContract<T> {

    List<Predicate<T>> getPredicates();

    void addPredicate(Predicate<T> predicate);

    boolean isRespected(List<T> data);

}
