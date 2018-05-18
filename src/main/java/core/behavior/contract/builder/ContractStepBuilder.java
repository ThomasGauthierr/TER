package core.behavior.contract.builder;

import com.sun.istack.internal.NotNull;
import core.behavior.context.IContext;
import core.behavior.contract.ContractImpl;
import core.behavior.contract.IContract;
import core.behavior.contract.predicate.SensorContractPredicate;
import core.device.DataType;

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
        private SensorContractPredicate predicate;
        private DataType dt;

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
            predicate = new SensorContractPredicate(m -> condition.express(m.getValue(), value), condition);
            return this;
        }

        @Override
        public PredicateContextStep on(@NotNull IContext context) {
            if (context == null) {
                throw new NullPointerException("The given context is null");
            }
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
