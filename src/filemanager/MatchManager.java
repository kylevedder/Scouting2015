/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filemanager;

import java.io.File;
import objects.MatchData;

/**
 *
 * @author Kyle
 */
public class MatchManager
{
    //singleton object
    private static MatchManager matchManager = null;
    
    private static final String LOCAL_FOLDER_PATH = "./saves/local";
    private static final String SERVER_FOLDER_PATH = "./saves/server";

    /**
     * Gets the singleton instance of this class.
     * @return 
     */
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
        File localFolder = new File(LOCAL_FOLDER_PATH);
        File serverFolder = new File(SERVER_FOLDER_PATH);
        
        if(!localFolder.exists())
        {
            localFolder.mkdirs();
        }
        if(!serverFolder.exists())
        {
            serverFolder.mkdirs();
        }
        
        
    }
    
    public void addMatch(MatchData match)
    {
        
    }
    
    private String getFileNameFromMatchData(MatchData match)
    {
        return "";
    }
}
