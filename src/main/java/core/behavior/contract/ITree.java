package core.behavior.contract;

import java.util.List;

public interface ITree<T> {

    T getParent();
    List<T> getNodes();
    void addNode(T node);
    void addNodes(List<T> nodes);

    boolean isRoot();
    boolean isLeaf();

}
