package ca.mcmaster.se2aa4.island.team012;

public class DroneObserver implements Observer {
    private Battery battery;
    private Position position;
    private Heading heading;

    @Override
    public void update(String property, Object value) {
        System.out.println("Property " + property + " changed to " + value);
    }

}
