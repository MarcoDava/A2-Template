package ca.mcmaster.se2aa4.island.team012;

public class Position extends Subject{ // Class for storing (row, col) coordinates
    private int row;
    private int col;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int[] getPosition() {
        return new int[]{row, col};
    }

    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
        notifyObservers("position",getPosition());
    }
}
