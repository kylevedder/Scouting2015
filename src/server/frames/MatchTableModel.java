/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.frames;

import client.objects.matchdata.MatchData;
import java.io.File;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import org.json.JSONObject;
import utils.FileUtils;

/**
 *
 * @author kyle
 */
public class MatchTableModel extends AbstractTableModel
{

    private int NUM_COLS = 5;

    private ArrayList<File> filesToPopulateWith = null;
    private Object[][] objArray = null;

    public MatchTableModel(ArrayList<File> filesToPopulateWith)
    {
        this.filesToPopulateWith = filesToPopulateWith;

        objArray = new Object[this.filesToPopulateWith.size()][NUM_COLS];

        //populate each element of the array
        for (int i = 0; i < filesToPopulateWith.size(); i++)
        {
            File f = filesToPopulateWith.get(i);
            JSONObject json = new JSONObject(FileUtils.readFileContents(f));
            MatchData md = MatchData.deserialize(json.toString());
            objArray[i][0] = md.getMatchTeamNumber();
            objArray[i][1] = md.getMatchMatchNumber();
            objArray[i][2] = md.getMatchScouter();
            objArray[i][3] = md.getMatchFinalScore();
            objArray[i][4] = f.getName();
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
        "Team #", "Match #", "Scouter", "Match Score", "File Name"
    };

    @Override
    public String getColumnName(int index)
    {
        return columnNames[index];
    }

}
