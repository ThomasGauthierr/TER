package core.behavior.contract;

import core.Message;
import core.behavior.context.IContext;
import core.behavior.context.IViolatedContext;
import core.behavior.context.ViolatedContext;
import core.behavior.contract.predicate.SensorContractPredicate;
import core.descision.Action;
import core.descision.IDescisionMaker;
import core.descision.SimplePriorityDescision;
import core.device.DataType;
import core.device.sensor.ISensor;

import java.util.List;

public class ContractImpl implements IContract {

    private String name;
    private IContext context;
    private DataType dataType;
    private SensorContractPredicate predicate;

    public ContractImpl(String name, IContext context, DataType dataType, SensorContractPredicate predicate) {
        this.name = name;
        this.context = context;
        this.dataType = dataType;
        this.predicate = predicate;
        // Linking to the context
        context.addListener(this);
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
    public void onViolatedContext(IViolatedContext violatedContext) {
        System.out.println("[IContract](" + this.getName() + ") triggered onViolatedContext event");
        System.out.println("Responsibles are : ");
        violatedContext.getResponsibleList().forEach(System.out::println);
        IDescisionMaker descisionMaker = new SimplePriorityDescision();
        Action decisions = descisionMaker.solve(violatedContext);
        System.out.println(decisions);
    }

    @Override
    public void update(IContext context, ISensor source, List<Message> messageList) {

        System.out.println("[IContract](" + this.getName() + ") received update event from [IContext](" + context.getIdentifier() + ")");

        if (!source.getDataType().equals(this.dataType)) {
            System.out.println(" ---> Not the same datatype (source:" + source.getDataType().name() + ", contract:" + dataType.name() + ") not checking.");
            return;
        }

        // context should be the same
        if (!predicate.test(context, source, messageList)) {
            ActionType at = ActionType.fromArithmeticCondition(predicate.getArithmeticCondition());
            if (at.equals(ActionType.DECREASE)) {
                onViolatedContext(new ViolatedContext(
                        this,
                        context.getActuatorsThatDecrease(source),
                        context.getSensorsOf(source.getDataType()),
                        messageList)
                );
            } else if (at.equals(ActionType.INCREASE)) {
                onViolatedContext(new ViolatedContext(
                        this,
                        context.getActuatorsThatIncrease(source),
                        context.getSensorsOf(source.getDataType()),
                        messageList)
                );
            }
        } else {
            // All is working good nothing to do
        }
    }
}
