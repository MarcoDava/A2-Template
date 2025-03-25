package ca.mcmaster.se2aa4.island.team012.Positioning;


public class CreekPosition extends Position {
    private Position creekPosition; // Add this field
    private String creekID;

    public CreekPosition(int row, int col) {
        super(row, col);
        this.creekPosition = new Position(row, col); // Initialize it
    }

    public int[] getCreekPosition() {
        return new int[]{creekPosition.getRow(), creekPosition.getCol()};
    }

    public void setCreekPosition(int row, int col, String creekID) {
        creekPosition = new Position(row, col);
        this.creekID = creekID;
    }
    
    public String getCreekID() {
        return this.creekID;
    }
}
