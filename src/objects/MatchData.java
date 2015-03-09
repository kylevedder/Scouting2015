/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import objects.stacks.StackContainer;
import objects.stacks.StackTote;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Kyle
 */
public class MatchData
{
    //match meta data
    private int matchMatchNumber = -1;
    public static String matchMatchNumberKey = "matchMatchNumberKey";
    private int matchTeamNumber = -1;
    public static String matchTeamNumberKey = "matchTeamNumberKey";
    private String matchScouter = "";
    public static String matchScouterKey = "matchScouterKey";
    private int matchFinalScore = -1;
    public static String matchFinalScoreKey = "matchFinalScoreKey";
    
    //Activity data
    private RobotActivityType activityType = RobotActivityType.INACTIVE;
    public static String activityTypeKey = "activityTypeKey";
    private String activityComment = "";
    public static String activityCommentKey = "activityCommentKey";
    
    //Autonomous data
    private int autoNumberTotes = -1;
    public static String autoNumberTotesKey = "autoNumberTotesKey";
    private boolean autoTotesStacked = false;
    public static String autoTotesStackedKey = "autoTotesStackedKey";
    private int autoNumberContainers = -1;
    public static String autoNumberContainersKey = "autoNumberContainersKey";
    private boolean autoInAutoZone = false;
    public static String autoInAutoZoneKey = "autoInAutoZoneKey";
    
    //Coop data
    private CoOpType coopType = CoOpType.NONE;
    public static String coopTypeKey = "coopTypeKey";
    
    //Teleop data
    private StackTote[] teleopToteStacks = null;
    public static String teleopToteStacksKey = "teleopToteStacksKey";
    private StackContainer[] teleopContainerStacks = null;
    public static String teleopContainerStacksKey = "teleopContainerStacksKey";
    
    //Human Player data
    private HumanPlayerType humanPlayerType = HumanPlayerType.NO_THROW;        
    public static String humanPlayerTypeKey = "humanPlayerTypeKey";

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
        System.out.println(toJSON());
    }
    
    public String toJSON()
    {                                        
        JSONObject json = new JSONObject();
        json.put(matchMatchNumberKey, matchMatchNumber);
        json.put(matchTeamNumberKey, matchTeamNumber);
        json.put(matchScouterKey, matchScouter);
        json.put(matchFinalScoreKey, matchFinalScore);
        
        json.put(activityTypeKey, activityType);
        json.put(activityCommentKey, activityComment);
        
        json.put(autoNumberTotesKey, autoNumberTotes);
        json.put(autoTotesStackedKey, autoTotesStacked);
        json.put(autoNumberContainersKey, autoNumberContainers);
        json.put(autoInAutoZoneKey, autoInAutoZone);
        
        json.put(coopTypeKey, coopType);
                
        json.put(teleopContainerStacksKey, teleopContainerStacks);        
        json.put(teleopToteStacksKey, teleopToteStacks);
        
        json.put(humanPlayerTypeKey, humanPlayerType);
        return json.toJSONString();
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