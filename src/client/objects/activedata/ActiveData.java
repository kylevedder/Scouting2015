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

    /**
     * Holds all data about a paticular team from active scouting.
     * @param matchMatchNumber
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
     * @param canGetTotes
     * @param totePickupOrientation
     * @param toteMaxStackHeight
     * @param feedLocation
     * @param canGetContainers
     * @param containersMustBeUpright
     * @param maxCappableStackHeight
     * @param canPushLitter
     * @param canPickupLitter
     * @param coopType
     * @param humanPlayerType 
     */
    public ActiveData(int matchTeamNumber, String matchScouter, //scouter info
            RobotShape robotShape, RobotNumWheels robotNumWheels, RobotWheelType robotWheelType, String robotComments,//robot type
            int autoNumTotes, int autoNumContainers, boolean autoTotesStacked, boolean autoInAutoZone,//auto info
            boolean canGetTotes, TotePickupOrientation totePickupOrientation, int toteMaxStackHeight, FeedLocation feedLocation,//tote info 
            boolean canGetContainers, boolean containersMustBeUpright, int maxCappableStackHeight, //container info
            boolean canPushLitter, boolean canPickupLitter, //litter info
            CoOpType coopType, //coop info
            HumanPlayerType humanPlayerType //human player info                      
    )
    {
        
    }

}
