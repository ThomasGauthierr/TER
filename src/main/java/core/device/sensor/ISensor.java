package core.device.sensor;

import core.Message;
import core.behavior.context.IContext;
import core.device.DataType;
import core.device.IDevice;

import java.util.List;

public interface ISensor extends IDevice {

    void collect();

    List<Message> getData();

    DataType getDataType();

    void addListener(IContext context);

    void notifyListeners(ISensor sensor, List<Message> messages);

}
