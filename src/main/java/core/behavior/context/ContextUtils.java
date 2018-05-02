package core.behavior.context;

import java.util.ArrayList;
import java.util.List;

public class ContextUtils {

    private ContextUtils() {}

    public List<Point> getAscVariation(int fromX, int toX, int fromY, int toY, int n) {
        List<Point> points = new ArrayList<>();

        for(int i = 0; i < n; i++) {
            int x = 0;
            int y = 0;
            points.add(new Point(x, y));
        }

        return points;
    }
}
