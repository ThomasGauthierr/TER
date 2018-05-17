package core.descision;

import core.behavior.context.IContext;;

public interface IDescisionMaker {

	public Action solve(IContext ctx);
	
}
