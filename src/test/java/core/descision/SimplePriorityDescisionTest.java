package core.descision;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

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

public class SimplePriorityDescisionTest {
	
	
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
		 SimplePriorityDescision sDescision = new SimplePriorityDescision();
		 Action a = sDescision.solve(ctx);
		 assertEquals(IActuator.State.OFF,a.getActionTypes().get(0));
	}

}
