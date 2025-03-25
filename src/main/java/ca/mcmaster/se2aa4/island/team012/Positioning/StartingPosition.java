package ca.mcmaster.se2aa4.island.team012.Positioning;

public class StartingPosition extends Position {

    public StartingPosition(int row, int col) {
        super(row, col);
    }

    public void setStartingPosition(int row, int col) {
        setRow(row);
        setCol(col);
    }
}