package core.behavior.contract;

import core.behavior.context.IContextObserver;
import core.behavior.context.MonitoredEntity;


public interface IContract extends IContextObserver, MonitoredEntity {

    String getName();

}
