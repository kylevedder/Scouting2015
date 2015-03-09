/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filemanager;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    private File localFolder = null;
    private File serverFolder = null;

    /**
     * Gets the singleton instance of this class.
     *
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
        localFolder = new File(LOCAL_FOLDER_PATH);
        serverFolder = new File(SERVER_FOLDER_PATH);

        if (!localFolder.exists())
        {
            localFolder.mkdirs();
        }
        if (!serverFolder.exists())
        {
            serverFolder.mkdirs();
        }
    }

    /**
     * Adds a completed match to the queue.
     * @param match 
     */
    public void addMatch(MatchData match)
    {
        String fileName = getFileNameFromMatchData(match);
        File localFile = new File(localFolder, fileName);
        
        try
        {
            System.out.println(localFile.getCanonicalPath());
        }
        catch (IOException ex)
        {
            Logger.getLogger(MatchManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String getFileNameFromMatchData(MatchData match)
    {
        return String.valueOf(match.getMatchMatchNumber()) + "_" + String.valueOf(match.getMatchTeamNumber() + ".txt");
    }
}
