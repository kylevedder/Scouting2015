/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.objects;

import client.objects.matchdata.MatchData;
import client.objects.stacks.StackContainer;
import client.objects.stacks.StackTote;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Kyle
 */
public class MultiMatchData
{

    private ArrayList<MatchData> matchDataSet = null;
    private int teamNumber = 0;

    private int numDataPoints = 0;

    private double avgMatchScore = 0;

    private double avgTeleopToteStackHeight = 0;
    private double avgTeleopNumToteStacks = 0;

    private double avgTeleopContainerCappedStackHeight = 0;
    private double avgTeleopNumContainerCappedStacks = 0;

    private double avgAutoTotesScored = 0;
    private double avgAutoRobotTotesStacked = 0;
    private double avgAutoContainersScored = 0;
    private double avgAutoRobotInAutoZone = 0;

    public MultiMatchData(ArrayList<MatchData> matchDataSet, int teamNumber)
    {
        this.matchDataSet = new ArrayList<>();
        this.teamNumber = teamNumber;
        if (matchDataSet != null)
        {
            for (MatchData matchData : matchDataSet)
            {
                this.addMatchData(matchData);
                numDataPoints ++;
            }
        }
    }

    /**
     * Gets the number of matches for the match data.
     *
     * @return
     */
    public int getNumMatches()
    {
        return numDataPoints;
    }

    /**
     * Adds a match data object.
     *
     * @param matchData
     */
    public void addMatchData(MatchData matchData)
    {
        matchDataSet.add(matchData);
        //
        //score
        //
        avgMatchScore += matchData.getMatchFinalScore();

        //
        //totes
        //
        avgTeleopNumToteStacks += matchData.getTeleopToteStacks().length;

        double localAvgTeleopToteStackHeight = 0;
        for (StackTote toteStack : matchData.getTeleopToteStacks())
        {
            localAvgTeleopToteStackHeight += toteStack.getHeight();
        }
        avgTeleopToteStackHeight += (localAvgTeleopToteStackHeight / matchData.getTeleopToteStacks().length);

        //
        //container
        //
        avgTeleopNumContainerCappedStacks += matchData.getTeleopContainerStacks().length;

        double localAvgTeleopContainerCappedStackHeight = 0;
        for (StackContainer containerStack : matchData.getTeleopContainerStacks())
        {
            localAvgTeleopContainerCappedStackHeight += containerStack.getHeight();
        }
        avgTeleopContainerCappedStackHeight += localAvgTeleopContainerCappedStackHeight / matchData.getTeleopContainerStacks().length;

        //
        //Auto
        //
        avgAutoTotesScored += matchData.getAutoNumberTotes();
        avgAutoContainersScored += matchData.getAutoNumberContainers();
        avgAutoRobotInAutoZone += (matchData.isAutoInAutoZone() ? 100 : 0);
        avgAutoRobotTotesStacked += (matchData.isAutoTotesStacked() ? 100 : 0);

        numDataPoints++;
    }

    /**
     * Gets a match data set sorted by match number.
     *
     * @return
     */
    public ArrayList<MatchData> getSortedMatchDataSet()
    {
        Collections.sort(matchDataSet);
        return matchDataSet;
    }

    public double getAvgAutoContainersScored()
    {
        return avgAutoContainersScored / numDataPoints;
    }

    public double getAvgAutoRobotInAutoZone()
    {
        return avgAutoRobotInAutoZone / numDataPoints;
    }

    public double getAvgAutoRobotTotesStacked()
    {
        return avgAutoRobotTotesStacked / numDataPoints;
    }

    public double getAvgAutoTotesScored()
    {
        return avgAutoTotesScored / numDataPoints;
    }

    public double getAvgMatchScore()
    {
        return avgMatchScore / numDataPoints;
    }

    public double getAvgTeleopContainerCappedStackHeight()
    {
        return avgTeleopContainerCappedStackHeight / numDataPoints;
    }

    public double getAvgTeleopNumContainerCappedStacks()
    {
        return avgTeleopNumContainerCappedStacks / numDataPoints;
    }

    public double getAvgTeleopNumToteStacks()
    {
        return avgTeleopNumToteStacks / numDataPoints;
    }

    public double getAvgTeleopToteStackHeight()
    {
        return avgTeleopToteStackHeight / numDataPoints;
    }

    public int getTeamNumber()
    {
        return teamNumber;
    }

    public ArrayList<MatchData> getMatchDataSet()
    {
        return matchDataSet;
    }

}
