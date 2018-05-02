package core.behavior.context;

public class ContextDigital extends AbsctractContext {

    public ContextDigital(int start, int end) {
        super(start, end);
    }

    @Override
    public void addPoint(Point point) {
        if(getPointAt(point.getX()).isPresent()) {
            // TODO: somme ou moyenne ? ou rien ?
            System.out.println("There is already a point defined there, what should i do ?");
            return;
        }

        points.add(point);
    }
}
