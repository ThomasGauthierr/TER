package core.behavior;

import core.device.sensor.ISensor;

import java.util.EventObject;

/**
 * Created by Alexis Couvreur on 12/04/2018.
 */
public class UnrespectedContractEvent extends EventObject {
    private ISensor sensor;
    private ActionType actionType;



    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public UnrespectedContractEvent(Object source) {
        super(source);
    }

    public UnrespectedContractEvent(Object source, ISensor sensor, ActionType actionType) {
        super(source);
        this.sensor = sensor;
        this.actionType = actionType;
    }

    public ISensor getSensor() {
        return sensor;
    }

    public ActionType getActionType() {
        return actionType;
    }
}
