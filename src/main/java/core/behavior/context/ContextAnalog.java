package core.behavior.context;

import java.util.Optional;

public class ContextAnalog extends AbsctractContext {

    public ContextAnalog(int start, int end) {
        super(start, end);
    }

    @Override
    public Optional<Point> getPointAt(int x) {
        if(x < startingX || x > endingX) {
            return Optional.empty();
        }

        Optional<Point> p = points.stream().filter(point ->point.getX() == x).findFirst();
        if(p.isPresent()) return p;

        Optional<Point> p1 = getPointAtOrBefore(x);
        Optional<Point> p2 = getPointAtOrAfter(x);

        if(p1.isPresent() && p2.isPresent()) {
            return Optional.of(new Point(p2.get().getX() - p1.get().getX(), p2.get().getY() - p1.get().getY()));
        }
        return Optional.empty();
    }

    @Override
    public void addPoint(Point point) {
        Optional<Point> existingPoint = super.getPointAt(point.getX());
        if(existingPoint.isPresent()) {
            existingPoint.get().setY((existingPoint.get().getX() + point.getX()) / 2);
        } else {
            points.add(point);
        }
    }
}
