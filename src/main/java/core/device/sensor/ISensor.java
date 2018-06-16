package core.device.sensor;

import core.Message;
import core.behavior.context.MonitoredEntity;
import core.device.IDevice;
import core.device.TypedDevice;

/**
 * Created by Alexis Couvreur on 14/06/2018.
 */
public interface ISensor extends IDevice, TypedDevice, MonitoredEntity, IObservableSensor {
    Message getLastMessage();
}
