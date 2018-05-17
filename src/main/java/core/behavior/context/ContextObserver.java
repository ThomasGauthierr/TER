package core.behavior.context;

import core.device.sensor.ISensor;

public interface ContextObserver {

    void onViolatedContext(IViolatedContext violatedContexts);
    void update(IContext context, ISensor source, int oldValue, int newValue);

}
