package ca.mcmaster.se2aa4.island.team012.Positioning;

public class Heading {
    private Direction heading;
    private Direction lastScanDirection;

    public Heading(Direction heading) {
        this.heading = heading;
    }

    public Direction getHeading() {
        return heading;
    }

    public void updateHeading(Object value) {
        this.heading = (Direction) value;
    }

    public boolean compareHeading(Direction heading) {
        return this.heading == heading;
    }

    public void setScanDirection(Direction direction){
        lastScanDirection = direction;
    }

    public Direction getLastScanDirection(){
        return lastScanDirection;
    }
}
