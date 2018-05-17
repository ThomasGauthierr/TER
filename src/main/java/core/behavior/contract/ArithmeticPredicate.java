package core.behavior.contract;

import core.behavior.contract.builder.ArithmeticCondition;

import java.util.function.Predicate;

public class ArithmeticPredicate<T> {

    private Predicate<T> predicate;
    private ArithmeticCondition arithmeticCondition;

    public ArithmeticPredicate(Predicate<T> predicate, ArithmeticCondition ac) {
        this.predicate = predicate;
        this.arithmeticCondition = ac;
    }

    public Predicate<T> getPredicate() {
        return predicate;
    }

    public ArithmeticCondition getArithmeticCondition() {
        return arithmeticCondition;
    }
}
