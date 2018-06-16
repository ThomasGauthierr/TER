package core.decision;

import core.behavior.context.ConcreteContext;
import core.behavior.context.IContext;
import core.behavior.context.ViolatedContext;
import org.junit.Before;

public class SimplePriorityDecisionTest {
	
	
	ViolatedContext ctx;
	
	@Before
	public void setup(){
		IContext context = new ConcreteContext("room");
		// ISensor source = new FakeSensor("test", 10, DataType.TEMPERATURE, new FakeSerialPort());
		// IActuator radiator = new FakeActuator("testA", new FakeSerialPort(), DataType.TEMPERATURE, ActionType.INCREASE);
		// context.getMonitoredActuators().add(radiator);
		// context.getSensors().add(source);
		/*List<Message> messageList = new ArrayList<>();
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
                messageList);*/
	}
	/*
	@Test
	public void decisionTest() {
		SimplePriorityDecision sDescision = new SimplePriorityDecision();
		 Action a = sDescision.solve(ctx);
		 assertEquals(IActuator.State.OFF,a.getActionTypes().get(0));
	}
*/
}
