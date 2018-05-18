package core.behavior.context;

import core.Message;
import core.device.sensor.ISensor;

import java.util.List;

public interface ISensorListener {

    void update(ISensor source, List<Message> messageList);

}
