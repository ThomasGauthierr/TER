package core.behavior.contract;

import core.behavior.context.ContextObserver;
import core.behavior.context.IContext;
import core.device.DataType;

import java.util.function.Predicate;

public interface IContract extends ContextObserver {

    String getName();

    IContext getContext();

    DataType getDataType();

    Predicate<IContext> getPredicate();

}
