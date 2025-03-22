// package ca.mcmaster.se2aa4.island.team012.DroneComponents;

// import org.apache.logging.log4j.LogManager;
// import org.apache.logging.log4j.Logger;
// import org.json.JSONObject;

// import ca.mcmaster.se2aa4.island.team012.Positioning.DronePosition;
// import ca.mcmaster.se2aa4.island.team012.Positioning.MapArea;
// import ca.mcmaster.se2aa4.island.team012.States.ApproachIslandState;
// import ca.mcmaster.se2aa4.island.team012.States.CreekFindingState;
// import ca.mcmaster.se2aa4.island.team012.States.FindLengthState;
// import ca.mcmaster.se2aa4.island.team012.States.LocatingIslandState;
// import ca.mcmaster.se2aa4.island.team012.States.SpiralSearchState;
// import ca.mcmaster.se2aa4.island.team012.States.State;
// import ca.mcmaster.se2aa4.island.team012.States.Status;

// public class AdvancedDroneBrain extends DroneBrain {

//     private State currentState;//we might need to make a seperate class called decision maker to split up methods. 
//     private State approachIsland;
//     private State creekFinding;
//     private State spiralSearch;
//     private State locatingIsland;
//     private State findAreaState;
//     private final Logger logger = LogManager.getLogger();
//     private Drone drone;
//     private MapArea mapArea;
//     private DroneRetrieval droneRetriever; 
//     private Battery battery;
//     private DronePosition dronePosition;

//     public AdvancedDroneBrain(Drone drone,Battery battery,DronePosition dronePosition) {
//         this.drone = drone;
//         this.battery=battery;
//         this.dronePosition=dronePosition;
//         findAreaState = new FindLengthState(mapArea,drone);
//         approachIsland = new ApproachIslandState(mapArea);
//         creekFinding = new CreekFindingState(mapArea);
//         spiralSearch = new SpiralSearchState(mapArea);
//         locatingIsland = new LocatingIslandState(mapArea);
//         currentState = locatingIsland;
//         droneRetriever= new DroneRetrieval(drone,mapArea,this.battery,this.dronePosition);
//     }
//     @Override
//     public String makeDecision(JSONObject decision) {
//         if (this.droneRetriever.dangerAssesment()!=DangerType.NEUTRAL) {
//             this.droneRetriever.handleDanger(decision, droneRetriever.dangerAssesment());
//         } else {
//             switch (drone.getStatus()) {
//                 case FIND_LENGTH_STATE:
//                     logger.info("STATE STATUS " + Status.FIND_LENGTH_STATE);
//                     this.currentState = this.findAreaState;
//                     break;
//                 case FIND_WIDTH_STATE:
//                     logger.info("STATE STATUS " + Status.FIND_WIDTH_STATE);
//                     this.currentState = this.findAreaState;
//                     break;
//                 case LOCATING_ISLAND_STATE:
//                     logger.info("STATE STATUS " + Status.LOCATING_ISLAND_STATE);
//                     this.currentState = this.findAreaState;
//                     break;
//                 case APPROACH_ISLAND_STATE:
//                     logger.info("STATE STATUS " + Status.APPROACH_ISLAND_STATE);
//                     this.currentState = this.findAreaState;
//                     break;
//                 case CREEK_FINDING_STATE:
//                     logger.info("STATE STATUS " + Status.CREEK_FINDING_STATE);
//                     this.currentState = this.findAreaState;
//                     break;
//                 case SPIRAL_SEARCH_STATE:
//                     logger.info("STATE STATUS " + Status.SPIRAL_SEARCH_STATE);
//                     this.currentState = this.findAreaState;
//                     break;
//                 default:
//                     break;
//             }
//             this.currentState.handle(drone, decision);
//         }
//         return decision.toString();
//     }
// }
