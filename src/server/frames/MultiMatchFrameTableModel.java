/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.frames;

import client.objects.matchdata.MatchData;
import client.objects.stacks.StackTote;
import javax.swing.table.AbstractTableModel;
import server.objects.MultiMatchData;

/**
 *
 * @author kyle
 */
public class MultiMatchFrameTableModel extends AbstractTableModel
{

    private final int NUM_PARAMS = 12;

    private Object[][] objArray = null;
    private MultiMatchData multiMatchData = null;

    public MultiMatchFrameTableModel(MultiMatchData multiMatchData)
    {
        this.multiMatchData = multiMatchData;
        System.out.println(multiMatchData.getNumMatches() + " vs " + multiMatchData.getSortedMatchDataSet().size());
        objArray = new Object[multiMatchData.getSortedMatchDataSet().size() + 1][NUM_PARAMS];

        objArray[0][0] = "Match Score";
        objArray[0][1] = "Activity Type";
        objArray[0][2] = "# Auto Totes";
        objArray[0][3] = "Auto Totes Stckd";
        objArray[0][4] = "# Auto Cont";
        objArray[0][5] = "In Auto Zone";
        objArray[0][6] = "# Tote Stacks";
        objArray[0][7] = "Avg Tote Height";
        objArray[0][8] = "# Cont Stacks";
        objArray[0][9] = "Avg Cont Height";
        objArray[0][10] = "CoOP Type";
        objArray[0][11] = "HP";

        for (int i = 0; i < multiMatchData.getSortedMatchDataSet().size(); i++)
        {
            MatchData matchData = multiMatchData.getSortedMatchDataSet().get(i);
            objArray[i + 1][0] = matchData.getMatchFinalScore();
            objArray[i + 1][1] = matchData.getActivityType().toString();
            objArray[i + 1][2] = matchData.getAutoNumberTotes();
            objArray[i + 1][3] = matchData.isAutoTotesStacked();
            objArray[i + 1][4] = matchData.getAutoNumberContainers();
            objArray[i + 1][5] = matchData.isAutoInAutoZone();
            objArray[i + 1][6] = matchData.getTeleopToteStacks().length;
            objArray[i + 1][7] = matchData.getTeleopAvgToteStackHeight();
            objArray[i + 1][8] = matchData.getTeleopContainerStacks().length;
            objArray[i + 1][9] = matchData.getTeleopAvgContainerStackHeight();
            objArray[i + 1][10] = matchData.getCoopType().toString();
            objArray[i + 1][11] = matchData.getHumanPlayerType().toString();
        }
    }

    @Override
    public int getRowCount()
    {
        return objArray[0].length;
    }

    @Override
    public int getColumnCount()
    {
        return objArray.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        if (rowIndex < objArray[0].length && columnIndex < objArray.length)
        {
            return objArray[columnIndex][rowIndex];
        }
        return null;
    }

    @Override
    public String getColumnName(int index)
    {
        System.out.println("index " + index);
        if (multiMatchData.getSortedMatchDataSet().size() < index)
        {
            return "TT";
        }
        else if (index > 0)
        {
            System.out.println("AFD: " + multiMatchData.getSortedMatchDataSet().get(index - 1).getMatchMatchNumber());
            return "M#: " + String.valueOf(multiMatchData.getSortedMatchDataSet().get(index - 1).getMatchMatchNumber());
        }
        else
        {
            return "T#: " + multiMatchData.getTeamNumber();
        }
    }

}
