package ca.mcmaster.se2aa4.island.team012;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ca.mcmaster.se2aa4.island.team012.DroneComponents.Battery;
import ca.mcmaster.se2aa4.island.team012.Positioning.Direction;
import ca.mcmaster.se2aa4.island.team012.Positioning.Position;

public class DroneObserver implements Observer {
    private Battery batteryLevel;
    private Direction currentHeading;
    private Position position;
    private final Logger logger = LogManager.getLogger();
    @Override
    public void update(String property, Object value) {
        if (property.equals("battery")) {
            this.batteryLevel = (Battery) value;
        }
        else if(property.equals( "heading")){
            this.currentHeading = (Direction) value;
        }
        else if(property.equals("position")){
            this.position = (Position) value;
        }
        else{
            logger.info("Unknown property: " + property);
        }
        logger.info("Property %s updated to %s%n", property, value);
    }
}