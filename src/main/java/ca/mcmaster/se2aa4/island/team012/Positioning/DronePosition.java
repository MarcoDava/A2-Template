package ca.mcmaster.se2aa4.island.team012.Positioning;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DronePosition extends Position {
    private static final Logger logger = LogManager.getLogger();

    public DronePosition(int row, int col) {
        super(row, col);
    }

    public int[] getDronePosition() {
        return new int[]{getRow(), getCol()};
    }

    public void updateDronePosition(int rowsMoved, int colsMoved) {
        int newRow = getRow() + rowsMoved;
        int newCol = getCol() + colsMoved;
        setRow(newRow);
        setCol(newCol);
        logger.info("Drone position updated to: " + newRow + " " + newCol);
    }

    public boolean comparePosition(int row, int col) {
        return getRow() == row && getCol() == col;
    }
}