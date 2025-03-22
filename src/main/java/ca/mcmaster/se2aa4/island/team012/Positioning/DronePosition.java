package ca.mcmaster.se2aa4.island.team012.Positioning;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DronePosition extends Position {

    private Position dronePosition;
    private static final Logger logger = LogManager.getLogger();

    public DronePosition(int row,int col) { // Call the parent class constructor with the required argument
        super(row,col);
        logger.info("Drone Position: "+row+" "+col);
        dronePosition = new Position(row,col);
        logger.info(dronePosition.getRow());
    }

    public Position getDronePosition() {
        return dronePosition;
    }

    public void updateDronePosition(int rowsMoved, int colsMoved) {
        int finalRow= getPosition()[0]+rowsMoved;
        int finalCol= getPosition()[1]+colsMoved;
        dronePosition.setRow(finalRow);
        dronePosition.setCol(finalCol);
    }
}
