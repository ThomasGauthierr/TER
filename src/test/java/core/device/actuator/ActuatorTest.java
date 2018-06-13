package core.device.actuator;

import static org.junit.Assert.*;

import org.junit.Test;

import core.FakeSerialPort;
import core.behavior.contract.ActionType;
import core.device.DataType;
import core.device.actuator.IActuator.State;

public class ActuatorTest {

	@Test
	public void test() {
		FakeSerialPort serialPort = new FakeSerialPort();
		IActuator actuator = new FakeActuator("test", serialPort, DataType.LIGHT, ActionType.INCREASE);
		actuator.deactivate();
		assertFalse(actuator.isActivated());
		actuator.activate();
		assertTrue(actuator.isActivated());
		actuator.setState(State.HIGH);
		assertEquals(State.HIGH, actuator.getState());
	}

}
