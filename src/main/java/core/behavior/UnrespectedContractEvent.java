package core.behavior;

import java.util.EventObject;

/**
 * Created by Alexis Couvreur on 12/04/2018.
 */
public class UnrespectedContractEvent extends EventObject {
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public UnrespectedContractEvent(Object source) {
        super(source);
    }
}
