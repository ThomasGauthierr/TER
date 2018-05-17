package core.behavior.contract.builder;

import core.behavior.context.IContext;
import core.behavior.contract.ArithmeticPredicate;
import core.behavior.contract.ContractImpl;
import core.behavior.contract.IContract;
import core.device.DataType;

import java.util.OptionalDouble;

public class ContractStepBuilder {

    private ContractStepBuilder() {
    }

    public static IdStep newBuilder() {
        return new ContractSteps();
    }

    public interface IdStep {
        SubjectStep name(String id);
    }

    public interface SubjectStep {
        PredicateContextStep on(IContext context);
        // PredicateContractStep on(IContract contract);
        // IMPL. Contract of contract later
    }

    public interface PredicateContextStep {
        PredicateConditionStep where(DataType dt);
    }

    public interface PredicateContractStep {
        PredicateContractConditionStep where(IContract contract);
    }

    public interface PredicateContractConditionStep {

    }

    public interface PredicateConditionStep {
        BuildStep is(ArithmeticCondition condition, double value);
    }

    public interface BuildStep {
        IContract build();
    }

    private static class ContractSteps implements IdStep, SubjectStep, PredicateContextStep, PredicateConditionStep, BuildStep {

        private String id;
        private IContext context;
        private ArithmeticPredicate<IContext> predicate;
        private DataType dt;
        private ArithmeticCondition condition;

        @Override
        public IContract build() {
            return new ContractImpl(id, context, dt, predicate);
        }

        @Override
        public SubjectStep name(String id) {
            this.id = id;
            return this;
        }

        @Override
        public BuildStep is(ArithmeticCondition condition, double value) {
            predicate = new ArithmeticPredicate<>(p -> {
                OptionalDouble val = context.getValueOf(dt);
                return val.isPresent() && condition.express(val.getAsDouble(), value);
            }, condition);
            return this;
        }

        @Override
        public PredicateContextStep on(IContext context) {
            this.context = context;
            return this;
        }

        @Override
        public PredicateConditionStep where(DataType dt) {
            this.dt = dt;
            return this;
        }
    }

}
