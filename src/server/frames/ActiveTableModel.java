/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.frames;

import client.objects.activedata.ActiveData;
import java.io.File;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import org.json.JSONObject;
import utils.FileUtils;

/**
 *
 * @author kyle
 */
public class ActiveTableModel extends AbstractTableModel
{

    private int NUM_COLS = 3;

    private ArrayList<File> filesToPopulateWith = null;
    private Object[][] objArray = null;

    public ActiveTableModel(ArrayList<File> filesToPopulateWith)
    {
        this.filesToPopulateWith = filesToPopulateWith;

        objArray = new Object[this.filesToPopulateWith.size()][NUM_COLS];

        //populate each element of the array
        for (int i = 0; i < filesToPopulateWith.size(); i++)
        {
            File f = filesToPopulateWith.get(i);
            JSONObject json = new JSONObject(FileUtils.readFileContents(f));
            ActiveData ad = ActiveData.deserialize(json.toString());
            objArray[i][0] = ad.getMatchTeamNumber();
            objArray[i][1] = ad.getMatchRobotScouter();
            objArray[i][2] = f.getName();            
        }
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
        return objArray[rowIndex][columnIndex];
    }
    
    String[] columnNames =
    {
        "Team #", "Scouter", "File Name"
    };

    @Override
    public String getColumnName(int index)
    {
        return columnNames[index];
    }
}
