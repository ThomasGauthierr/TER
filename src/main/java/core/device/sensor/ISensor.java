package core.device.sensor;

import core.Message;
import core.device.DataType;
import core.device.IDevice;

import java.util.List;

public interface ISensor extends IDevice {

    void collect();

    List<Message> getData();

    DataType getDataType();

}
