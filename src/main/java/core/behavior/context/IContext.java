package core.behavior.context;

import java.util.Optional;

public interface IContext {

    int getStartingX();
    int getEndingX();

    Optional<Point> getPointAt(int x);
    Optional<Point> getPointAtOrBefore(int x);
    Optional<Point> getPointAtOrAfter(int x);

    void addPoint(Point point);

}
