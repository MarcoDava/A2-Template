package ca.mcmaster.se2aa4.island.team012.Positioning;

public class EmergencyPosition extends Position {

    private Position emergencyPosition;

    public EmergencyPosition(int row, int col) {
        super(row, col);
        emergencyPosition = new Position(row, col);
    }

    public Position getEmergencyPosition() {
        return emergencyPosition;
    }

    public void setEmergencyPosition(int row, int col) {
        emergencyPosition = new Position(row, col);
    }
}
