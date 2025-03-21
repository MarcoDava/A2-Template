package ca.mcmaster.se2aa4.island.team012.Positioning;

public class DronePosition extends Position {

    private Position dronePosition;

    public DronePosition(Position position) { // Call the parent class constructor with the required argument
        super(position.getPosition()); // Explicitly call the parent class constructor
        dronePosition = position; // Initialize dronePosition properly
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
