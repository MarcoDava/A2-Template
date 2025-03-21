package ca.mcmaster.se2aa4.island.team012.Positioning;

public class EmergencyPosition extends Position {

    private Position emergencyPosition;

    public EmergencyPosition(int[] position) {
        super(position); // Call the parent class constructor with the required argument
        emergencyPosition = new Position(position); // Initialize emergencyPosition properly
    }

    public Position getEmergencyPosition() {
        return emergencyPosition;
    }

    public void setEmergencyPosition(Position position) {
        emergencyPosition = position;
    }
}
