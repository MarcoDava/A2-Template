package ca.mcmaster.se2aa4.island.team012;

interface FlightSystem{
    public boolean fly(){
        position=idk lol
    }
    public Heading turnLeft(Heading heading){
        if(heading==Heading.NORTH){
            return Heading.WEST;
        }
        else if(heading==Heading.WEST){
            return Heading.SOUTH;
        }
        else if(heading==Heading.SOUTH){
            return Heading.EAST;
        }
        else{
            return Heading.NORTH;
        }
    }
    public Heading turnRight(Heading heading){
        if(heading==Heading.NORTH){
            return Heading.EAST;
        }
        else if(heading==Heading.EAST){
            return Heading.SOUTH;
        }
        else if(heading==Heading.SOUTH){
            return Heading.WEST;
        }
        else{
            return Heading.NORTH;
        }
    }
}