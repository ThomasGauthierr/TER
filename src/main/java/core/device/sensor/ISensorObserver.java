package core.device.sensor;

import core.Message;

/**
 * Created by Alexis Couvreur on 15/06/2018.
 * Notify an entity when a new message is retrieved
 */
public interface ISensorObserver {

    void update(ISensor source, Message newMessage);

}
