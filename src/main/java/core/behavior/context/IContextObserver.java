package core.behavior.context;

/**
 * Created by Alexis Couvreur on 14/06/2018.
 */
public interface IContextObserver {

    IContext getObservedContext();

    void update(IContext source, Object arg);

}
