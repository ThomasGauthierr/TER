package core.behavior.contract;

import core.behavior.context.IContext;
import core.behavior.context.IViolatedContext;
import core.behavior.context.ViolatedContext;
import core.device.DataType;
import core.device.sensor.ISensor;

import java.util.function.Predicate;

public class ContractImpl implements IContract {

    private String name;
    private IContext context;
    private DataType dataType;
    private ArithmeticPredicate<IContext> predicate;

    public ContractImpl(String name, IContext context, DataType dataType, ArithmeticPredicate<IContext> predicate) {
        this.name = name;
        this.context = context;
        this.dataType = dataType;
        this.predicate = predicate;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public IContext getContext() {
        return context;
    }

    @Override
    public DataType getDataType() {
        return dataType;
    }

    @Override
    public Predicate<IContext> getPredicate() {
        return predicate.getPredicate();
    }

    @Override
    public void onViolatedContext(IViolatedContext violatedContext) {

    }

    @Override
    public void update(IContext context, ISensor source, int oldValue, int newValue) {

        // context should be the same
        if (!predicate.getPredicate().test(context)) {
            ActionType at = ActionType.fromArithmeticCondition(predicate.getArithmeticCondition());
            if (at.equals(ActionType.DECREASE)) {
                onViolatedContext(new ViolatedContext(
                        this,
                        context.getActuatorsThatDecrease(source),
                        context.getSensorsOf(source.getDataType()),
                        new int[]{newValue})
                );
            } else if (at.equals(ActionType.INCREASE)) {
                onViolatedContext(new ViolatedContext(
                        this,
                        context.getActuatorsThatIncrease(source),
                        context.getSensorsOf(source.getDataType()),
                        new int[]{newValue})
                );
            }
        } else {
            // All is working good nothing to do
        }
    }
}
