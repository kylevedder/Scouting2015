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
        this.matchDataSet = matchDataSet;
        this.teamNumber = teamNumber;

        for (MatchData matchData : matchDataSet)
        {
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
            avgTeleopContainerCappedStackHeight += localAvgTeleopContainerCappedStackHeight;

            //
            //Auto
            //
            avgAutoTotesScored += matchData.getAutoNumberTotes();
            avgAutoContainersScored += matchData.getAutoNumberContainers();
            avgAutoRobotInAutoZone += (matchData.isAutoInAutoZone() ? 100 : 0);
            avgAutoRobotTotesStacked += (matchData.isAutoTotesStacked() ? 100 : 0);

            numDataPoints++;
        }
        //
        //score
        //
        avgMatchScore /= numDataPoints;

        //
        //totes
        //
        avgTeleopNumToteStacks /= numDataPoints;
        avgTeleopToteStackHeight /= numDataPoints;

        //
        //container
        //
        avgTeleopNumContainerCappedStacks /= numDataPoints;
        avgTeleopContainerCappedStackHeight /= numDataPoints;

        //
        //Auto
        //
        avgAutoTotesScored /= numDataPoints;
        avgAutoContainersScored /= numDataPoints;
        avgAutoRobotInAutoZone /= numDataPoints;
        avgAutoRobotTotesStacked /= numDataPoints;
    }

    public double getAvgAutoContainersScored()
    {
        return avgAutoContainersScored;
    }

    public double getAvgAutoRobotInAutoZone()
    {
        return avgAutoRobotInAutoZone;
    }

    public double getAvgAutoRobotTotesStacked()
    {
        return avgAutoRobotTotesStacked;
    }

    public double getAvgAutoTotesScored()
    {
        return avgAutoTotesScored;
    }

    public double getAvgMatchScore()
    {
        return avgMatchScore;
    }

    public double getAvgTeleopContainerCappedStackHeight()
    {
        return avgTeleopContainerCappedStackHeight;
    }

    public double getAvgTeleopNumContainerCappedStacks()
    {
        return avgTeleopNumContainerCappedStacks;
    }

    public double getAvgTeleopNumToteStacks()
    {
        return avgTeleopNumToteStacks;
    }

    public double getAvgTeleopToteStackHeight()
    {
        return avgTeleopToteStackHeight;
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
