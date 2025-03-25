package ca.mcmaster.se2aa4.island.team012.DroneComponents;

import java.io.StringReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import ca.mcmaster.se2aa4.island.team012.Positioning.CreekPosition;
import ca.mcmaster.se2aa4.island.team012.Positioning.Direction;
import ca.mcmaster.se2aa4.island.team012.Positioning.DronePosition;
import ca.mcmaster.se2aa4.island.team012.Positioning.StartingPosition;
import ca.mcmaster.se2aa4.island.team012.Positioning.EmergencyPosition;
import ca.mcmaster.se2aa4.island.team012.Positioning.Heading;
import ca.mcmaster.se2aa4.island.team012.Positioning.MapArea;
import ca.mcmaster.se2aa4.island.team012.States.Status;

/**
 * Handles the acknowledgment and processing of results received from the server.
 * Updates the drone's state and map information based on the results.
 */
public class ResultsAcknowledger {
    // Fields for various components and states of the drone.
    private Heading heading;
    private Battery battery;
    private MapArea mapArea;
    private Control controller;
    private DroneBrain droneBrain;
    private StartingPosition startingPosition;
    private CreekPosition creekPosition;
    private EmergencyPosition emergencyPosition;
    private DronePosition dronePosition;
    private boolean groundFound;
    private boolean creekFound;
    private boolean siteFound;
    private boolean XFound;
    private boolean eastClear;
    private boolean westClear;
    private boolean northClear;
    private boolean southClear;
    private int RowDisplacement;
    private int ColDisplacement;
    private int range;
    private int southDistance;
    private int eastDistance;
    private int northDistance;
    private int westDistance;


    /**
     * Constructor to initialize the ResultsAcknowledger with necessary components.
     * 
     * @param battery The battery of the drone.
     * @param heading The heading direction of the drone.
     * @param mapArea The map area being explored.
     * @param dronePosition The current position of the drone.
     * @param startingPosition The starting position of the drone.
     * @param creekPosition The position of the creek.
     * @param emergencyPosition The position of the emergency site.
     * @param droneBrain The brain of the drone.
     * @param controller The controller for managing drone commands.
     */
    public ResultsAcknowledger(Battery battery, Heading heading, MapArea mapArea, DronePosition dronePosition, StartingPosition startingPosition, CreekPosition creekPosition, EmergencyPosition emergencyPosition, DroneBrain droneBrain, Control controller) {
        // Initialize fields with provided parameters.
        this.battery = battery;
        this.heading = heading;
        this.mapArea = mapArea;
        this.dronePosition = dronePosition;
        this.startingPosition = startingPosition;
        this.creekPosition = creekPosition;
        this.emergencyPosition = emergencyPosition;
        this.droneBrain = droneBrain;
        this.controller = controller;

        // Initialize default values for distances and flags.
        southDistance = -1;
        eastDistance = -1;
        northDistance = -1;
        westDistance = -1;

        RowDisplacement = 0;
        ColDisplacement = 0;

        groundFound = false;
        creekFound = false;
        siteFound = false;
        XFound = false;

        northClear = false;
        southClear = false;
        westClear = false;
        eastClear = false;

    }

    /**
     * Extracts the battery usage from the server response and updates the battery.
     * 
     * @param response The response from the server.
     */
    private void extractBattery(JSONObject response) {
        battery.useBattery(response.getInt("cost"));
    }

    /**
     * Extracts ground information from the server response.
     * 
     * @param extraInfo The JSONObject containing ground information.
     * @return True if ground is found, false otherwise.
     */
    private boolean extractGround(JSONObject extraInfo) {
        String Ground = extraInfo.getString("found");
        return Ground.equals("GROUND");
    }

    /**
     * Extracts the range from the server response.
     * 
     * @param extraInfo The JSONObject containing range information.
     * @return The range value.
     */
    private int extractRange(JSONObject extraInfo) {
        return extraInfo.getInt("range");
    }

    /**
     * Extracts site information from the server response.
     * 
     * @param extraInfo The JSONObject containing site information.
     * @return True if sites are found, false otherwise.
     */
    private boolean extractSites(JSONObject extraInfo) {
        JSONArray emergencySite = extraInfo.getJSONArray("sites");
        if (emergencySite.length() == 0) {
            return false;
        }
        emergencyPosition.setEmergencyPosition(dronePosition.getRow(), dronePosition.getCol(), emergencySite.getString(0));
        return true;
    }

    /**
     * Extracts creek information from the server response.
     * 
     * @param extraInfo The JSONObject containing creek information.
     * @return True if creeks are found, false otherwise.
     */
    private boolean extractCreeks(JSONObject extraInfo) {
        JSONArray creek = extraInfo.getJSONArray("creeks");
        
        if (creek.length() == 0) {
            return false;
        }
        creekPosition.setCreekPosition(dronePosition.getRow(), dronePosition.getCol(), creek.getString(0));
        return true;
    }

    /**
     * Updates the drone's values based on the server response.
     * 
     * @param s The response from the server as a string.
     */
    public void updateValues(String s) {
        // Convert the response from the server to a JSON object.
        JSONObject response = new JSONObject(new JSONTokener(new StringReader(s)));
        JSONObject extraInfo = response.getJSONObject("extras");

        // Extract and update battery information.
        extractBattery(response);

        // Handle actions based on the last command.
        if (controller.compareAction(Command.ECHO)) {
            range = extractRange(extraInfo);
            groundFound = extractGround(extraInfo);
        } else if (controller.compareAction(Command.SCAN)) {
            if (extractCreeks(extraInfo)) {
                creekFound = true;
            }
            if (extractSites(extraInfo)) {
                siteFound = true;
            }
        }

        // Handle the current state of the drone.
        switch (droneBrain.getStatus()) {
            case LENGTH_ALIGN_STATE:
                lengthStateHandler();
                break;
            case FIND_LENGTH_STATE:
                findLengthStateHandler();
                break;
            case LEFT_TURN_STATE:
                leftTurnStateHandler();
                break;
            case RIGHT_TURN_STATE:
                rightTurnStateHandler();
                break;
            case WIDTH_ALIGN_STATE:
                widthStateHandler();
                break;
            case FIND_WIDTH_STATE:
                findWidthStateHandler();
                break;
            case APPROACH_ISLAND_STATE:
                approachIslandStateHandler();
                break;
            case SPIRAL_FROM_MIDDLE_STATE:
                spiralFromMiddleStateHandler();
                break;
            case SPIRAL_FROM_SITE_STATE:
                spiralFromSiteHandler();
                break;
            default:
                break;
        }
    }

    /**
     * Handles the length alignment state.
     * Ensures that both sides of the drone (left and right) are clear of any ground.
     * If no ground is found on two sides, it indicates the spot to start searching for dimensions.
     * If ground is found on either side, the drone keeps scanning until a spot with no ground is found.
     */
    private void lengthStateHandler() {
        if (heading.getLastScanDirection() == Direction.E && !groundFound) {
            eastClear = true;
        } else if (heading.getLastScanDirection() == Direction.N && !groundFound) {
            northClear = true;
        } else if (heading.getLastScanDirection() == Direction.W && !groundFound) {
            westClear = true;
        } else if (heading.getLastScanDirection() == Direction.S && !groundFound) {
            southClear = true;
        }
        if (controller.compareAction(Command.MOVE)) {
            if (heading.compareHeading(Direction.E)) {
                ColDisplacement++;
            } else if (heading.compareHeading(Direction.W)) {
                ColDisplacement--;
            } else if (heading.compareHeading(Direction.N)) {
                RowDisplacement--;
            } else {
                RowDisplacement++;
            }
        }
        if (northClear && southClear) {
            droneBrain.setStatus(Status.FIND_LENGTH_STATE);
        } else if (westClear && eastClear) {
            droneBrain.setStatus(Status.FIND_LENGTH_STATE);
        }
    }

    /**
     * Handles the find length state.
     */
    private void findLengthStateHandler() {
        if (heading.getLastScanDirection() == Direction.N) {
            northDistance = range;
        } else if (heading.getLastScanDirection() == Direction.E) {
            eastDistance = range;
        } else if (heading.getLastScanDirection() == Direction.S) {
            southDistance = range;
        } else if (heading.getLastScanDirection() == Direction.W) {
            westDistance = range;
        }
        if (northDistance != -1 && southDistance != -1) {
            if (northDistance > southDistance && heading.getHeading() == Direction.E) {
                droneBrain.setStatus(Status.LEFT_TURN_STATE);
            } else if (northDistance < southDistance && heading.getHeading() == Direction.E) {
                droneBrain.setStatus(Status.RIGHT_TURN_STATE);
            } else if (northDistance > southDistance && heading.getHeading() == Direction.W) {
                droneBrain.setStatus(Status.RIGHT_TURN_STATE);
            } else if (northDistance < southDistance && heading.getHeading() == Direction.W) {
                droneBrain.setStatus(Status.LEFT_TURN_STATE);
            }
            mapArea.setMapY(northDistance + southDistance + 1);
            dronePosition.setRow(northDistance + 1);
            startingPosition.setRow(northDistance - RowDisplacement + 1);
        } else if (eastDistance != -1 && westDistance != -1) {
            if (eastDistance > westDistance && heading.getHeading() == Direction.N) {
                droneBrain.setStatus(Status.RIGHT_TURN_STATE);
            } else if (eastDistance < westDistance && heading.getHeading() == Direction.N) {
                droneBrain.setStatus(Status.LEFT_TURN_STATE);
            } else if (eastDistance > westDistance && heading.getHeading() == Direction.S) {
                droneBrain.setStatus(Status.LEFT_TURN_STATE);
            } else if (eastDistance < westDistance && heading.getHeading() == Direction.S) {
                droneBrain.setStatus(Status.RIGHT_TURN_STATE);
            }
            mapArea.setMapX(eastDistance + westDistance + 1);
            dronePosition.setCol(westDistance + 1);
            startingPosition.setCol(westDistance - ColDisplacement + 1);
            XFound = true;
        } else {
            droneBrain.setStatus(Status.FIND_LENGTH_STATE);
        }
    }

    /**
     * Handles the left turn state.
     */
    private void leftTurnStateHandler() {
        droneBrain.setStatus(Status.WIDTH_ALIGN_STATE);
    }

    /**
     * Handles the right turn state.
     */
    private void rightTurnStateHandler() {
        droneBrain.setStatus(Status.WIDTH_ALIGN_STATE);
    }

    /**
     * Handles the width alignment state.
     * Ensures that both sides of the drone (left and right) are clear of any ground.
     * If no ground is found on two sides, it indicates the spot to start searching for dimensions.
     * If ground is found on either side, the drone keeps scanning until a spot with no ground is found.
     */
    private void widthStateHandler() {
        if (heading.getLastScanDirection() == Direction.E && !groundFound) {
            eastClear = true;
        } else if (heading.getLastScanDirection() == Direction.N && !groundFound) {
            northClear = true;
        } else if (heading.getLastScanDirection() == Direction.W && !groundFound) {
            westClear = true;
        } else if (heading.getLastScanDirection() == Direction.S && !groundFound) {
            southClear = true;
        }
        if (controller.compareAction(Command.MOVE)) {
            if (heading.compareHeading(Direction.E)) {
                ColDisplacement++;
            } else if (heading.compareHeading(Direction.W)) {
                ColDisplacement--;
            } else if (heading.compareHeading(Direction.N)) {
                RowDisplacement--;
            } else {
                RowDisplacement++;
            }
        }
        if (westClear && eastClear) {
            droneBrain.setStatus(Status.FIND_WIDTH_STATE);
        }
    }

    /**
     * Handles the find width state.
     */
    private void findWidthStateHandler() {
        if (heading.getLastScanDirection() == Direction.N) {
            northDistance = range;
        } else if (heading.getLastScanDirection() == Direction.E) {
            eastDistance = range;
        } else if (heading.getLastScanDirection() == Direction.S) {
            southDistance = range;
        } else if (heading.getLastScanDirection() == Direction.W) {
            westDistance = range;
        }
        if (eastDistance != -1 && westDistance != -1 && northDistance != -1 && southDistance != -1) {
            if (XFound) {
                mapArea.setMapY(northDistance + southDistance + 1);
                dronePosition.setRow(northDistance + 1);
                startingPosition.setRow(northDistance - RowDisplacement + 1);
            } else {
                mapArea.setMapX(eastDistance + westDistance + 1);
                dronePosition.setCol(westDistance + 1);
                startingPosition.setRow(westDistance - ColDisplacement + 1);
            }
            droneBrain.setStatus(Status.APPROACH_ISLAND_STATE);
        } else {
            droneBrain.setStatus(Status.FIND_WIDTH_STATE);
        }
    }

    /**
     * Handles the approach island state.
     */
    private void approachIslandStateHandler() {
        if (mapArea.getRows() / 2 == dronePosition.getRow() && mapArea.getCols() / 2 == dronePosition.getCol()) {
            droneBrain.setStatus(Status.SPIRAL_FROM_MIDDLE_STATE);
        } else {
            droneBrain.setStatus(Status.APPROACH_ISLAND_STATE);
        }
    }

    /**
     * Handles the spiral search state from the middle of the map.
     */
    private void spiralFromMiddleStateHandler() {
        if (siteFound) {
            creekFound = false;
            droneBrain.setStatus(Status.SPIRAL_FROM_SITE_STATE);
        } else {
            droneBrain.setStatus(Status.SPIRAL_FROM_MIDDLE_STATE);
        }
    }

    /**
     * Handles the spiral search state from the site.
     */
    private void spiralFromSiteHandler() {
        if (creekFound) {
            droneBrain.setStatus(Status.END_SEARCH_STATE);
        } else {
            droneBrain.setStatus(Status.SPIRAL_FROM_SITE_STATE);
        }
    }
}