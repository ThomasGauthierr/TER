package core.behavior.context;

import core.Message;
import core.device.sensor.ISensor;

import java.util.List;

public interface IContextListener {

    void onViolatedContext(IViolatedContext violatedContexts);

    void update(IContext context, ISensor source, List<Message> messageList);

}
