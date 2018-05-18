package core.behavior.contract.predicate;

import core.Message;
import core.behavior.context.IContext;
import core.behavior.contract.builder.ArithmeticCondition;
import core.device.sensor.ISensor;

import java.util.List;

public interface IContractPredicate {

    // TODO: this is bullshit
    boolean test(IContext context, ISensor sensor, List<Message> message);

    ArithmeticCondition getArithmeticCondition();

}
