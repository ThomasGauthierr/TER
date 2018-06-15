package core.behavior.contract;

import core.behavior.context.IContext;
import core.device.DataType;

public class ContractImpl implements IContract {

    private String name;
    private IContext context;
    private DataType dataType;


    @Override
    public IContext getObservedContext() {
        return null;
    }

    @Override
    public void update(IContext source, Object arg) {

    }

    @Override
    public String getName() {
        return null;
    }
}
