package ca.mcmaster.se2aa4.island.team012.Positioning;

/**
 * Represents the position of the drone on the map.
 * Extends the Position class to include drone-specific functionality.
 */
public class DronePosition extends Position {

    /**
     * Constructor to initialize the drone's position.
     * 
     * @param row The row index of the drone's position.
     * @param col The column index of the drone's position.
     */
    public DronePosition(int row, int col) {
        super(row, col);
    }

    /**
     * Updates the drone's position by moving it a specified number of rows and columns.
     * 
     * @param rowsMoved The number of rows to move.
     * @param colsMoved The number of columns to move.
     */
    public void updateDronePosition(int rowsMoved, int colsMoved) {
        // Calculate the new row and column positions.
        int newRow = getRow() + rowsMoved;
        int newCol = getCol() + colsMoved;

        // Update the row position if it is valid.
        if (getRow() != -1) {
            setRow(newRow);
        }

        // Update the column position if it is valid.
        if (getCol() != -1) {
            setCol(newCol);
        }
    }
}