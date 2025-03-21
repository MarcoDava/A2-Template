package ca.mcmaster.se2aa4.island.team012.Positioning;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Heading {
    private Direction heading;
    private static final Logger logger = LogManager.getLogger();

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
}
