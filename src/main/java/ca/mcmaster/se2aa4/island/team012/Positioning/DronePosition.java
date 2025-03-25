package ca.mcmaster.se2aa4.island.team012.Positioning;


public class DronePosition extends Position {

    public DronePosition(int row, int col) {
        super(row, col);
    }

    public void updateDronePosition(int rowsMoved, int colsMoved) {
        int newRow = getRow() + rowsMoved;
        int newCol = getCol() + colsMoved;
        if(getRow()!=-1){
            setRow(newRow);
        }
        if(getCol()!=-1){
            setCol(newCol);
        }
    }
}