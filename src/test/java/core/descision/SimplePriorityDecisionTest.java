package core.descision;

import core.FakeSerialPort;
import core.Message;
import core.behavior.context.ContextImpl;
import core.behavior.context.IContext;
import core.behavior.context.ViolatedContext;
import core.behavior.contract.ActionType;
import core.behavior.contract.IContract;
import core.behavior.contract.builder.ArithmeticCondition;
import core.behavior.contract.builder.ContractStepBuilder;
import core.device.DataType;
import core.device.actuator.FakeActuator;
import core.device.actuator.IActuator;
import core.device.sensor.FakeSensor;
import core.device.sensor.ISensor;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SimplePriorityDecisionTest {
	
	
	ViolatedContext ctx;
	
	@Before
	public void setup(){
        IContext context = new ContextImpl("room");
		ISensor source = new FakeSensor("test", 10, DataType.TEMPERATURE, new FakeSerialPort());
		IActuator radiator = new FakeActuator("testA", new FakeSerialPort(), DataType.TEMPERATURE, ActionType.INCREASE);
		context.getActuators().add(radiator);
		context.getSensors().add(source);
		List<Message> messageList = new ArrayList<>();
		IContract contract = ContractStepBuilder.newBuilder()
                .name("contract01")
                .on(context)
                .where(DataType.TEMPERATURE)
                .is(ArithmeticCondition.LOWER_THAN_OR_EQUAL_TO, 21)
                .build();
		ctx = new ViolatedContext(
                contract,
                context.getActuatorsThatIncrease(source),
                context.getSensorsOf(source.getDataType()),
                messageList);
	}
	
	@Test
	public void decisionTest() {
		SimplePriorityDecision sDescision = new SimplePriorityDecision();
		 Action a = sDescision.solve(ctx);
		 assertEquals(IActuator.State.OFF,a.getActionTypes().get(0));
	}

}
