package core.behavior.contract;

import java.util.ArrayList;
import java.util.List;

public class Tree<T> implements ITree<T> {

    private T parent;
    private List<T> nodes;

    public Tree() {
        parent = null;
        nodes = new ArrayList<>();
    }

    public Tree(T parent) {
        this.parent = parent;
        nodes = new ArrayList<>();
    }

    @Override
    public T getParent() {
        return parent;
    }

    @Override
    public List<T> getNodes() {
        return nodes;
    }

    @Override
    public void addNode(T node) {
        nodes.add(node);
    }

    @Override
    public void addNodes(List<T> nodes) {
        nodes.addAll(nodes);
    }

    @Override
    public boolean isRoot() {
        return parent == null;
    }

    @Override
    public boolean isLeaf() {
        return nodes.isEmpty();
    }
}
