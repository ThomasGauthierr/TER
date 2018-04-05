package core.device;

import core.ValueTimestamp;

import java.util.List;

public interface ISensor extends IDevice {

    void collect();

    List<ValueTimestamp> getData();

}
