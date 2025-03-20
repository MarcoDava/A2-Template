package ca.mcmaster.se2aa4.island.team012.Positioning;

public class MapArea {

    private int[] mapArea;

    public MapArea(int[] mapArea) {
        this.mapArea = mapArea;
    }

    public int[] getMapArea() {
        return mapArea;
    }

    public int getRows(){
        return mapArea[0];
    }

    public int getCols(){
        return mapArea[1];
    }

    public void setMapArea(int row, int col) {
        mapArea = new int[]{row, col};
    }
}
