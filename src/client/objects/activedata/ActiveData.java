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

    public ActiveData(int matchMatchNumber, int matchTeamNumber, String matchScouter, //scouter info
            RobotShape robotShape, RobotNumWheels robotNumWheels, RobotWheelType robotWheelType, String robotComments,//robot type
            int autoNumTotes, int autoNumContainers, boolean autoTotesStacked, boolean autoInAutoZone,//auto info
            
            CoOpType coopType, //coop info
            HumanPlayerType humanPlayerType //human player info
            
            
            )
    {
    }

}
