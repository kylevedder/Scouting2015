/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import objects.stacks.StackContainer;
import objects.stacks.StackTote;

/**
 *
 * @author Kyle
 */
public class MatchData
{
    //match meta data
    private int matchMatchNumber = -1;
    private int matchTeamNumber = -1;
    private String matchScouter = "";
    private int matchFinalScore = -1;
    
    //Activity data
    private RobotActivityType activityType = RobotActivityType.INACTIVE;
    private String activityComment = "";
    
    //Autonomous data
    private int autoNumberTotes = -1;
    private boolean autoTotesStacked = false;
    private int autoNumberContainers = -1;
    private boolean autoInAutoZone = false;
    
    //Coop data
    private CoOpType coopType = CoOpType.NONE;
    
    //Teleop data
    private StackTote[] teleopToteStacks = null;
    private StackContainer[] teleopContainerStacks = null;
    
    //Human Player data
    private HumanPlayerType hp = HumanPlayerType.NO_THROW;        

    public MatchData(int matchNum, int teamNum, String scouter, //scouter info
            int autoNumTotes, int autoNumContainers, boolean autoTotesStacked, boolean autoInAutoZone, //auto info
            StackTote[] toteArray, StackContainer[] containerArray, //stacks info
            RobotActivityType activityType, String activityComment, //robot activity info
            CoOpType coopType, //coop info
            HumanPlayerType hpType, //human player info
            int matchScore) //final match score 
    {
        
    }
    
    
}
