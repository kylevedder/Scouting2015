/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import objects.stacks.StackBase;
import objects.stacks.StackContainer;
import objects.stacks.StackTote;
import org.json.JSONArray;
import org.json.JSONObject;


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
        
    /**
     * Converts the match data object to JSON String
     * @return 
     */    
    public String toJSON()
    {        
        HashMap<String, Object> map = new HashMap<>();        
        map.put(matchMatchNumberKey, matchMatchNumber);
        map.put(matchTeamNumberKey, matchTeamNumber);
        map.put(matchScouterKey, matchScouter);
        map.put(matchFinalScoreKey, matchFinalScore);
        
        map.put(activityTypeKey, activityType);
        map.put(activityCommentKey, activityComment);
        
        map.put(autoNumberTotesKey, autoNumberTotes);
        map.put(autoTotesStackedKey, autoTotesStacked);
        map.put(autoNumberContainersKey, autoNumberContainers);
        map.put(autoInAutoZoneKey, autoInAutoZone);
        
        map.put(coopTypeKey, coopType);
               
        //load the JSON object into an array
        ArrayList<JSONObject> containerArrayList = new ArrayList<>();              
        for(StackBase stack: teleopContainerStacks)
        {           
            containerArrayList.add(stack.getJSONObject());           
        }
        JSONArray containerArray = new JSONArray(containerArrayList.toArray());  
        
        //load the JSON object into an array
        ArrayList<JSONObject> toteArrayList = new ArrayList<>();
        for(StackBase stack: teleopToteStacks)
        {            
            toteArrayList.add(stack.getJSONObject());
        }
        JSONArray toteArray = new JSONArray(toteArrayList.toArray());
        
        map.put(teleopContainerStacksKey, containerArray);
        map.put(teleopToteStacksKey, toteArray);
        
        map.put(humanPlayerTypeKey, humanPlayerType);
        JSONObject json = new JSONObject(map);          
        return json.toString();
    }
    
    /**
     * Converts a JSON string into a MatchData object.
     * @param jsonStr
     * @return 
     */
    public static MatchData fromJSONtoMatchData(String jsonStr)
    {
//        JSONObject jsonObj = new JSONObject("{\"phonetype\":\"N95\",\"cat\":\"WP\"}");
//        JSONParser parser = new JSONParser();
//        try
//        {
////            JSONObject jsonObj = (JSONObject)parser.parse(jsonStr);
//            System.out.println(jsonObj.toJSONString());
//        }
//        catch (ParseException ex)
//        {
//            Logger.getLogger(MatchData.class.getName()).log(Level.SEVERE, null, ex);
//        }
        return null;
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