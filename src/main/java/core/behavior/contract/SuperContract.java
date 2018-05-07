package core.behavior.contract;

import java.util.List;
import java.util.Observable;
import java.util.function.Predicate;

public class SuperContract extends AbstractContract implements ITree<IContract> {

    private IContract parent;
    private List<IContract> nodes;

    public SuperContract(IContract parent, ContractPredicate predicate) {
        super("superpredicate", predicate);
        this.parent = parent;
    }
    public SuperContract(ContractPredicate predicate) {
        super("superpredicate", predicate);
    }

    @Override
    public void update(Observable o, Object args) {

    }

    @Override
    public IContract getParent() {
        return parent;
    }

    @Override
    public List<IContract> getNodes() {
        return nodes;
    }

    @Override
    public void addNode(IContract node) {
        nodes.add(node);
    }

    @Override
    public void addNodes(List<IContract> nodes) {
        this.nodes.addAll(nodes);
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
