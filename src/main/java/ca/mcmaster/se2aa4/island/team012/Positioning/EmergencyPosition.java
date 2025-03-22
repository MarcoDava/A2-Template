package ca.mcmaster.se2aa4.island.team012.Positioning;

public class EmergencyPosition extends Position {

    private Position emergencyPosition;

    public EmergencyPosition() {
    }

    public Position getEmergencyPosition() {
        return emergencyPosition;
    }

    public void setEmergencyPosition(Position position) {
        emergencyPosition = position;
    }
}
