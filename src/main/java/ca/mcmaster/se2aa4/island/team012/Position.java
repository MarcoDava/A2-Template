package ca.mcmaster.se2aa4.island.team012;

public class Position { // Class for storing (row, col) coordinates

    private int[] position = new int[2];
    Position() {
        this.position[0] = 0;
        this.position[1] = 0;
    }

    public Position(int row, int col) {
        this.position[0] = row;
        this.position[1] = col;
    }

    public int[] getPosition() {
        return position;
    }

    public void setPosition(int row, int col) {
        this.position[0] = row;
        this.position[1] = col;
    }
}
