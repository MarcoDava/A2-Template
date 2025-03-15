// package ca.mcmaster.se2aa4.island.team012.DroneComponents;

// import ca.mcmaster.se2aa4.island.team012.States.Status;
// import ca.mcmaster.se2aa4.island.team012.States.State;
// import ca.mcmaster.se2aa4.island.team012.States.ApproachIslandState;
// import ca.mcmaster.se2aa4.island.team012.States.CreekFindingState;
// import ca.mcmaster.se2aa4.island.team012.States.SpiralSearchState;
// import ca.mcmaster.se2aa4.island.team012.States.LocatingIslandState;

// import java.io.StringReader;

// import org.apache.logging.log4j.LogManager;
// import org.apache.logging.log4j.Logger;

// import eu.ace_design.island.bot.IExplorerRaid;
// import org.json.JSONObject;
// import org.json.JSONTokener;

// import ca.mcmaster.se2aa4.island.team012.DroneObserver;
// import ca.mcmaster.se2aa4.island.team012.Positioning.Direction;
// import ca.mcmaster.se2aa4.island.team012.Positioning.Heading;
// import ca.mcmaster.se2aa4.island.team012.Positioning.Position;

// public class Drone implements IExplorerRaid{//reduce the amount of times that the dron changes heading, rarely use scan. 
// //use echo to guide the drone. 
//     private State currentState;//we might need to make a seperate class called decision maker to split up methods. 
//     private State approachIsland;
//     private State creekFinding;
//     private State spiralSearch;
//     private State locatingIsland;
//     private Drone drone;
//     private Position dronePosition;
//     private Heading heading;
//     private Battery batteryLevel;
//     private FlightSystem flightSystem=new FlightSystem(batteryLevel, heading);
//     private Photoscanner photoscanner=new Photoscanner();
//     private final Logger logger = LogManager.getLogger();
//     public Drone(){
//         currentStatus = Status.LOCATING_ISLAND_STATE;
//         approachIsland=new ApproachIslandState();
//         creekFinding=new CreekFindingState();
//         spiralSearch=new SpiralSearchState();
//         locatingIsland=new LocatingIslandState();
//         currentState = locatingIsland;
//     }
//     // public boolean gridSearch(){
//     //     for(int i=0;i<10;i++){
//     //         flightSystem.fly();
//     //         //photoscanner.scanBelow();
//     //     }
//     //     flightSystem.turnNorth();
//     //     flightSystem.fly();
//     //     photoscanner.scanBelow();
//     //     flightSystem.turnWest();
//     //     for(int i=0;i<10;i++){
//     //         flightSystem.fly();
//     //         //photoscanner.scanBelow();
//     //     }
//     //     flightSystem.turnSouth();
//     //     flightSystem.fly();
//     //     //photoscanner.scanBelow();
//     //     flightSystem.turnEast();
//     //     for(int i=0;i<10;i++){
//     //         flightSystem.fly();
//     //         //photoscanner.scanBelow();
//     //     }
//     //     flightSystem.stop();
//     //     return true;//need to change return value to a proper value. The reason why it returns is
//     // }
//     // public boolean locateIsland(){
//     //     return true;
//     // }
//     // public boolean approachIsland(int[] locationPosition){
//     //     return true;

//     // }


//     @Override
//     public void initialize(String s) {
//         DroneObserver observer = new DroneObserver();
//         batteryLevel.addObserver(observer);
//         heading.addObserver(observer);
//         logger.info("** Initializing the Exploration Command Center");
//         JSONObject info = new JSONObject(new JSONTokener(new StringReader(s)));
//         logger.info("** Initialization info:\n {}",info.toString(2));
//         info.getString("position")
//         dronePosition = new DronePosition();
//         String direction = info.getString("heading");
//         if (direction.equals("N")) {
//             heading=new Heading(Direction.N);
//         } else if (direction.equals("E")) {
//             heading=new Heading(Direction.E);
//         } else if (direction.equals("S")) {
//             heading=new Heading(Direction.S);
//         } else if (direction.equals("W")) {
//             heading=new Heading(Direction.W);
//         }
//         batteryLevel = new Battery(info.getInt("budget"));
//         logger.info("The drone is facing {}", direction);
//         logger.info("Battery level is {}", batteryLevel);
//     }

//     @Override
//     public String takeDecision() {
//         switch (currentStatus)
//             {
//                 case LOCATING_ISLAND_STATE:
//                     logger.info("STATE STATUS " + Status.LOCATING_ISLAND_STATE);
//                     //this.currentState = this.groundFinderState; 
//                     break;
//                 default:
//                     break;
//             }
//             this.currentState.handle(drone, decision, parameters);
//         }
//     }

//     @Override
//     public void acknowledgeResults(String s) {
//         JSONObject response = new JSONObject(new JSONTokener(new StringReader(s)));
//         logger.info("** Response received:\n"+response.toString(2));
//         Integer cost = response.getInt("cost");
//         logger.info("The cost of the action was {}", cost);
//         String status = response.getString("status");
//         logger.info("The status of the drone is {}", status);
//         JSONObject extraInfo = response.getJSONObject("extras");
//         logger.info("Additional information received: {}", extraInfo);
//     }

//     @Override
//     public String deliverFinalReport() {
//         return "no creek found";
//     }

//     public static void main(String[] args) {
//         Drone drone = new Drone();
//     }
    
// }