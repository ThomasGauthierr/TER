package core.behavior.contract.builder;

import com.sun.istack.internal.NotNull;
import core.Message;
import core.behavior.context.IContext;
import core.behavior.context.MetaContext;
import core.behavior.contract.ConcreteContract;
import core.behavior.contract.IContract;
import core.behavior.contract.MetaContract;
import core.device.DataType;

import java.util.function.Predicate;

public class ContractStepBuilder {

    private ContractStepBuilder() {
    }

    public static IdStep newBuilder() {
        return new ContractSteps();
    }

    public interface IdStep {
        SubjectStep name(String id);
    }

    public interface TypeStep {
        MetaStep asMetaContract();

        ConcreteStep asConcreteContract();
    }

    public interface MetaStep {

    }

    public interface ConcreteStep {

    }

    public interface SubjectStep {
        PredicateContextStep on(IContext context);
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
        private Predicate<Message> predicate;
        private DataType dt;
        private boolean concrete;

        @Override
        public IContract build() {

            if (concrete)
                return new ConcreteContract(id, context, dt, predicate);
            return new MetaContract(id, (MetaContext) context);
        }

        @Override
        public SubjectStep name(String id) {
            this.id = id;
            return this;
        }

        @Override
        public BuildStep is(ArithmeticCondition condition, double value) {
            predicate = o -> condition.express(o.getValue(), value);
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
