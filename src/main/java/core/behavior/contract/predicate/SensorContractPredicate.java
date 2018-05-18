package core.behavior.contract.predicate;

import core.Message;
import core.behavior.context.IContext;
import core.behavior.contract.builder.ArithmeticCondition;
import core.device.sensor.ISensor;

import java.util.List;
import java.util.function.Predicate;

public class SensorContractPredicate implements IContractPredicate {

    private Predicate<Message> predicate;
    private ArithmeticCondition arithmeticCondition;

    public SensorContractPredicate(Predicate<Message> predicate, ArithmeticCondition arithmeticCondition) {
        this.arithmeticCondition = arithmeticCondition;
        this.predicate = predicate;
    }

    @Override
    public boolean test(IContext context, ISensor sensor, List<Message> messages) {
        if (!context.getSensors().contains(sensor)) {
            System.out.println("Does not contain the sensor");
            return true;
        }

        for (Message m : messages) {
            if (!predicate.test(m))
                return false;
        }

        return true;
    }

    @Override
    public ArithmeticCondition getArithmeticCondition() {
        return arithmeticCondition;
    }
}
