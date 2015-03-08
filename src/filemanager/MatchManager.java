/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filemanager;

import objects.MatchData;

/**
 *
 * @author Kyle
 */
public class MatchManager
{

    private static MatchManager matchManager = null;

    public static MatchManager getInstance()
    {
        if (matchManager == null)
        {
            matchManager = new MatchManager();
        }
        return matchManager;
    }

    private MatchManager()
    {
    }
    
    public void addMatch(MatchData match)
    {
        
    }
}
