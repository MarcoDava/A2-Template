package ca.mcmaster.se2aa4.island.team012.Positioning;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public class Heading {

    private final Logger logger = LogManager.getLogger();
    private Direction heading;

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
        logger.info("Got here 17");
        return this.heading == heading;
    }
}
