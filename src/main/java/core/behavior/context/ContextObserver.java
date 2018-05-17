package core.behavior.context;

import core.device.sensor.ISensor;

import java.util.List;

public interface ContextObserver {

    void onViolatedContext(List<IViolatedContext> violatedContexts);
    void update(IContext context, ISensor source, int oldValue, int newValue);

}
