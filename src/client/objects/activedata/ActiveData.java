/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.objects.activedata;

import client.objects.matchdata.CoOpType;
import client.objects.matchdata.HumanPlayerType;

/**
 *
 * @author kyle
 */
public class ActiveData
{

    int matchTeamNumber = -1;
    String matchRobotScouter = null;

    RobotShape robotShape = null;
    RobotNumWheels robotNumWheels = null;
    RobotWheelType robotWheelType = null;
    String robotComments = null;

    int autoNumTotes = -1;
    int autoNumContainers = 1;
    boolean autoTotesStacked = false;
    boolean autoInAutoZone = false;

    boolean toteCanGetTotes = false;
    TotePickupOrientation totePickupOrientation = null;
    int toteMaxStackHeight = -1;
    FeedLocation toteFeedLocation = null;

    boolean containerCanGetContainers = false;
    boolean containerMustBeUpright = false;
    int containerMaxCappableStackHeight = -1;

    boolean litterCanPushLitter = false;
    boolean litterCanPickupLitter = false;
    
    CoOpType coopType = null;
    HumanPlayerType humanPlayerType = null;

    /**
     * Holds all data about a paticular team from active scouting.
     *
     * @param matchTeamNumber
     * @param matchScouter
     * @param robotShape
     * @param robotNumWheels
     * @param robotWheelType
     * @param robotComments
     * @param autoNumTotes
     * @param autoNumContainers
     * @param autoTotesStacked
     * @param autoInAutoZone
     * @param toteCanGetTotes
     * @param totePickupOrientation
     * @param toteMaxStackHeight
     * @param toteFeedLocation
     * @param containerCanGetContainers
     * @param containerMustBeUpright
     * @param containerMaxCappableStackHeight
     * @param litterCanPushLitter
     * @param litterCanPickupLitter
     * @param coopType
     * @param humanPlayerType
     */
    public ActiveData(int matchTeamNumber, String matchScouter, //scouter info
            RobotShape robotShape, RobotNumWheels robotNumWheels, RobotWheelType robotWheelType, String robotComments,//robot type
            int autoNumTotes, int autoNumContainers, boolean autoTotesStacked, boolean autoInAutoZone,//auto info
            boolean toteCanGetTotes, TotePickupOrientation totePickupOrientation, int toteMaxStackHeight, FeedLocation toteFeedLocation,//tote info 
            boolean containerCanGetContainers, boolean containerMustBeUpright, int containerMaxCappableStackHeight, //container info
            boolean litterCanPushLitter, boolean litterCanPickupLitter, //litter info
            CoOpType coopType, //coop info
            HumanPlayerType humanPlayerType //human player info                      
    )
    {
        this.matchTeamNumber = matchTeamNumber;
        this.matchRobotScouter = matchScouter;
        
        this.robotShape = robotShape;
        this.robotNumWheels = robotNumWheels;
        this.robotWheelType = robotWheelType;
        this.robotComments = robotComments;
        
        this.autoInAutoZone = autoInAutoZone;
        this.autoNumContainers = autoNumContainers;
        this.autoTotesStacked = autoTotesStacked;
        this.autoNumTotes = autoNumTotes;
        
        this.toteCanGetTotes = toteCanGetTotes;
        this.toteFeedLocation = toteFeedLocation;
        this.toteMaxStackHeight = toteMaxStackHeight;
        this.totePickupOrientation = totePickupOrientation;
        
        this.containerCanGetContainers = containerCanGetContainers;
        this.containerMaxCappableStackHeight = containerMaxCappableStackHeight;
        this.containerMustBeUpright = containerMustBeUpright;
        
        this.coopType = coopType;
        
        this.humanPlayerType = humanPlayerType;
    }    
}
