package core;

import static org.junit.Assert.*;

import org.junit.Test;

import core.behavior.contract.ActionType;
import core.device.DataType;

public class AnnuaireTest {

	@Test
	public void test() {
		Annuaire a= Annuaire.getInstance();
		assertEquals(ActionType.INCREASE.getId(), a.getInfo("TEST2").getActionType().getId());
		assertEquals(DataType.TEMPERATURE.getId(), a.getInfo("TEST2").getDataType().getId());
		assertTrue(a.getInfo("TEST2").isActuator());
		assertEquals(DataType.LIGHT.getId(), a.getInfo("TEST1").getDataType().getId());
		assertFalse(a.getInfo("TEST1").isActuator());
	}

}
