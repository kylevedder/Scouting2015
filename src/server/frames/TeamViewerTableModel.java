/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.frames;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import javax.swing.table.AbstractTableModel;
import server.objects.MultiMatchData;

/**
 *
 * @author Kyle
 */
public class TeamViewerTableModel extends AbstractTableModel
{

    private int NUM_COLS = 9;

    private HashMap<Integer, MultiMatchData> hashMap = null;
    private Object[][] objArray = null;

    public TeamViewerTableModel(HashMap<Integer, MultiMatchData> hashMap)
    {
        this.hashMap = hashMap;
        
        Set<Entry<Integer, MultiMatchData>> set = hashMap.entrySet();
        Iterator<Entry<Integer, MultiMatchData>> itr = set.iterator();
        objArray = new Object[set.size()][NUM_COLS];
        int index = 0;
        while (itr.hasNext())
        {            
            Entry<Integer, MultiMatchData> entity = itr.next();
            int teamNum = entity.getKey();
            MultiMatchData multiMatchData = entity.getValue();            
            objArray[index][0] = teamNum;   
            objArray[index][1] = multiMatchData.getAvgMatchScore();
            objArray[index][2] = multiMatchData.getAvgTeleopToteStackHeight();
            objArray[index][3] = multiMatchData.getAvgTeleopNumToteStacks();
            objArray[index][4] = multiMatchData.getAvgTeleopContainerCappedStackHeight();
            objArray[index][5] = multiMatchData.getAvgTeleopNumContainerCappedStacks();
            objArray[index][6] = multiMatchData.getAvgAutoContainersScored();
            objArray[index][7] = multiMatchData.getAvgAutoTotesScored();
            objArray[index][8] = multiMatchData.getAvgAutoRobotInAutoZone();
            index++;
        }       
        System.out.println("Team Viewer Model Setup.");
    }

    @Override
    public int getRowCount()
    {
        return objArray.length;
    }

    @Override
    public int getColumnCount()
    {
        return NUM_COLS;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        if (objArray.length > rowIndex && objArray[0].length > columnIndex)
        {
            return objArray[rowIndex][columnIndex];
        }
        else
        {
            return null;
        }
    }

    String[] columnNames =
    {
        "Team #", "Match Score", "Tote Stack Height", "# Tote Stacks", "Cont Stack Height", "# Cont Stacks", "Auto Totes Scored", "Auto Conts Scored", "% in Auto Zone"
    };

    @Override
    public String getColumnName(int index)
    {
        return columnNames[index];
    }

}
