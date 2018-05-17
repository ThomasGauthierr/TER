package core.behavior.context;

public interface ContextObserver {

    void onViolatedContext(IViolatedContext violatedContext);
    void update(IContext context);

}
