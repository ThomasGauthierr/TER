package core.behavior.context;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public abstract class AbsctractContext implements IContext {
    protected int startingX;
    protected int endingX;
    protected List<Point> points;

    public AbsctractContext(int start, int end) {
        startingX = start;
        endingX = end;
        points = new ArrayList<>();
    }

    @Override
    public int getStartingX() {
        return startingX;
    }

    @Override
    public int getEndingX() {
        return endingX;
    }

    @Override
    public Optional<Point> getPointAt(int x) {
        if(x < startingX || x > endingX) {
            return Optional.empty();
        }
        return points.stream().filter(p ->p.getX() == x).findFirst();
    }

    @Override
    public Optional<Point> getPointAtOrBefore(int x) {
        if(x < startingX || x > endingX) {
            return Optional.empty();
        }

        return points.stream().filter(p -> p.getX() <= x).max((Comparator.comparingInt(Point::getX)));
    }

    @Override
    public Optional<Point> getPointAtOrAfter(int x) {
        if(x < startingX || x > endingX) {
            return Optional.empty();
        }

        return points.stream().filter(p -> p.getX() <= x).min((Comparator.comparingInt(Point::getX)));
    }
}
