package core.behavior.contract;

import core.behavior.context.IContext;
import core.behavior.context.IContextListener;
import core.device.DataType;

public interface IContract extends IContextListener {

    String getName();

    IContext getContext();

    DataType getDataType();

}
