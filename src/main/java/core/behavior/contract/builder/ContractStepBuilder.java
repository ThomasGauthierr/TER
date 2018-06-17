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
        BuildStep asMetaContract();

        PredicateContextStep asConcreteContract();
    }

    public interface SubjectStep {
        TypeStep on(IContext context);
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

    private static class ContractSteps implements IdStep, SubjectStep, PredicateContextStep, PredicateConditionStep, BuildStep, TypeStep {

        private String id;
        private IContext context;
        private Predicate<Message> predicate;
        private DataType dt;
        private boolean concrete;
        private ArithmeticCondition condition;

        @Override
        public IContract build() {
            IContract c;
            if (concrete) {
                c = new ConcreteContract(id, context, dt, predicate, condition);
            } else {
                c = new MetaContract(id, (MetaContext) context);
            }
            context.addObserver(c);
            return c;
        }

        @Override
        public SubjectStep name(String id) {
            this.id = id;
            return this;
        }

        @Override
        public BuildStep is(ArithmeticCondition condition, double value) {
            this.condition = condition;
            predicate = o -> condition.express(o.getValue(), value);
            return this;
        }

        @Override
        public TypeStep on(@NotNull IContext context) {
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

        @Override
        public BuildStep asMetaContract() {
            this.concrete = false;
            return this;
        }

        @Override
        public PredicateContextStep asConcreteContract() {
            this.concrete = true;
            return this;
        }
    }

}
