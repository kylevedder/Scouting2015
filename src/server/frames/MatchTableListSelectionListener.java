/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.frames;

import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import utils.FileUtils;

/**
 *
 * @author kyle
 */
class MatchTableListSelectionListener implements ListSelectionListener
{    

    private JTable activeTable = null;
    
    public MatchTableListSelectionListener(JTable activeTable)
    {
        this.activeTable = activeTable;
    }

    
    
    @Override
    public void valueChanged(ListSelectionEvent e)
    {
        String fileName = (String)activeTable.getValueAt(e.getFirstIndex(), 4);
//        FileUtils.readFileContents(null);
        System.out.println("click");
    }
    
}
