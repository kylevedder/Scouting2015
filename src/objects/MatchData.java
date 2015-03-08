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
    private HumanPlayerType humanPlayerType = HumanPlayerType.NO_THROW;        

    /**
     * Object for storing all match data.
     * @param matchMatchNumber
     * @param matchTeamNumber
     * @param matchScouter
     * @param autoNumTotes
     * @param autoNumContainers
     * @param autoTotesStacked
     * @param autoInAutoZone
     * @param teleopToteStacks
     * @param teleopContainerStacks
     * @param activityType
     * @param activityComment
     * @param coopType
     * @param humanPlayerType
     * @param matchFinalScore 
     */
    public MatchData(int matchMatchNumber, int matchTeamNumber, String matchScouter, //scouter info
            int autoNumTotes, int autoNumContainers, boolean autoTotesStacked, boolean autoInAutoZone, //auto info
            StackTote[] teleopToteStacks, StackContainer[] teleopContainerStacks, //stacks info
            RobotActivityType activityType, String activityComment, //robot activity info
            CoOpType coopType, //coop info
            HumanPlayerType humanPlayerType, //human player info
            int matchFinalScore) //final match score 
    {
        this.matchMatchNumber = matchMatchNumber;
        this.matchTeamNumber = matchTeamNumber;
        this.matchScouter = matchScouter;
        this.matchFinalScore = matchFinalScore;
        
        this.activityType = activityType;
        this.activityComment = activityComment;
        
        this.autoNumberTotes = autoNumTotes;
        this.autoNumberContainers = autoNumContainers;
        this.autoTotesStacked = autoTotesStacked;
        this.autoInAutoZone = autoInAutoZone;
        
        this.coopType = coopType;
        
        this.teleopToteStacks = teleopToteStacks;
        this.teleopContainerStacks = teleopContainerStacks;
        
        this.humanPlayerType = humanPlayerType;
    }

    public String getActivityComment()
    {
        return activityComment;
    }

    public RobotActivityType getActivityType()
    {
        return activityType;
    }

    public int getAutoNumberContainers()
    {
        return autoNumberContainers;
    }

    public int getAutoNumberTotes()
    {
        return autoNumberTotes;
    }

    public CoOpType getCoopType()
    {
        return coopType;
    }

    public HumanPlayerType getHumanPlayerType()
    {
        return humanPlayerType;
    }

    public int getMatchFinalScore()
    {
        return matchFinalScore;
    }

    public int getMatchMatchNumber()
    {
        return matchMatchNumber;
    }

    public String getMatchScouter()
    {
        return matchScouter;
    }

    public int getMatchTeamNumber()
    {
        return matchTeamNumber;
    }

    public StackContainer[] getTeleopContainerStacks()
    {
        return teleopContainerStacks;
    }

    public StackTote[] getTeleopToteStacks()
    {
        return teleopToteStacks;
    }

    public boolean isAutoInAutoZone()
    {
        return autoInAutoZone;
    }

    public boolean isAutoTotesStacked()
    {
        return autoTotesStacked;
    }            
}