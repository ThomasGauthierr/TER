package core.decision;

import core.behavior.context.IViolatedContext;

public interface IDecisionMaker {

    Action solve(IViolatedContext ctx);

}
