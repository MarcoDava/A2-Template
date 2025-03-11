package ca.mcmaster.se2aa4.island.team012;

class Photoscanner{
    private JSONObject scanCommand = new JSONObject();
    private static final Logger logger = LogManager.getLogger();
    scanBelow(){//might need position as a parameter depending on how the scanBelow function works
        scanCommand.put("action", "scan");
        logger.info(scanCommand.toString());
    }

}