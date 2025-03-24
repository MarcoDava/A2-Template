package ca.mcmaster.se2aa4.island.team012.Positioning;


public class EmergencyPosition extends Position {
    private Position sitePosition; // Add this field
    private String siteID;

    public EmergencyPosition(int row, int col) {
        super(row, col);
        this.sitePosition = new Position(row, col); // Initialize it
    }

    public int[] getEmergencyPosition() {
        return new int[]{sitePosition.getRow(), sitePosition.getCol()};
    }

    public void setEmergencyPosition(int row, int col, String siteID) {
        sitePosition = new Position(row, col);
        this.siteID = siteID;
    }
    
    public String getSiteID() {
        return siteID;
    }
}
