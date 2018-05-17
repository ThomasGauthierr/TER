package core.descision;

import core.behavior.context.IViolatedContext;;

public interface IDescisionMaker {

	public Action solve(IViolatedContext ctx);
	
}
