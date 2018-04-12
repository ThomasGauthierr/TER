package core.device;

import core.Message;

import java.util.List;

public interface ISensor extends IDevice {

    void collect();

    List<Message> getData();

}
