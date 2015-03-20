/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.filemanager;

import client.networking.SyncFilesClientThread;
import client.objects.activedata.ActiveData;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import client.objects.matchdata.MatchData;
import utils.Flag;
import utils.Utils;

/**
 *
 * @author Kyle
 */
public class ClientFileManager
{

    //singleton object
    private static ClientFileManager matchManager = null;    

    private Flag blockAddingFilesToSendFlag = new Flag(false);

    private static final String MATCH_LOCAL_FOLDER_PATH = "./saves/match/local";
    private static final String MATCH_SERVER_FOLDER_PATH = "./saves/match/server";
    
    private static final String ACTIVE_LOCAL_FOLDER_PATH = "./saves/active/local";
    private static final String ACTIVE_SERVER_FOLDER_PATH = "./saves/active/server";

    private File localMatchFolder = null;
    private File serverMatchFolder = null;
    
    private File localActiveFolder = null;
    private File serverActiveFolder = null;

    /**
     * Gets the singleton instance of this class.
     *
     * @return
     */
    public static ClientFileManager getInstance()
    {
        if (matchManager == null)
        {
            matchManager = new ClientFileManager();
        }
        return matchManager;
    }

    private ClientFileManager()
    {
        localMatchFolder = new File(MATCH_LOCAL_FOLDER_PATH);
        serverMatchFolder = new File(MATCH_SERVER_FOLDER_PATH);
        
        localActiveFolder = new File(ACTIVE_LOCAL_FOLDER_PATH);
        serverActiveFolder = new File(ACTIVE_SERVER_FOLDER_PATH);

        //create local folder, if doesn't exist
        if (!localMatchFolder.exists())
        {
            localMatchFolder.mkdirs();
        }
        //create server folder, if doesn't exist
        if (!serverMatchFolder.exists())
        {
            serverMatchFolder.mkdirs();
        }        
        //create local folder, if doesn't exist
        if (!localActiveFolder.exists())
        {
            localActiveFolder.mkdirs();
        }
        //create server folder, if doesn't exist
        if (!serverActiveFolder.exists())
        {
            serverActiveFolder.mkdirs();
        }

        //leave unlocked, should be locked for pulling all files from the SyncFilesThread only
        blockAddingFilesToSendFlag.unlock();
    }

    /**
     * Adds a completed match to the queue.
     *
     * @param match
     */
    public void addMatch(MatchData match)
    {
        //block until clear to add match data
        blockAddingFilesToSendFlag.await();
        FileWriter fw = null;
        String fileName = getFileNameFromMatchData(match);
        File localFile = new File(localMatchFolder, fileName);
        String content = match.serialize();
        try
        {
            System.out.println("Writing File: " + localFile.getCanonicalPath() + "\n"
                    + "Content: " + content);
            fw = new FileWriter(localFile.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();
        }
        catch (IOException ex)
        {
            Logger.getLogger(ClientFileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally
        {
            try
            {
                if (fw != null)
                {
                    fw.close();
                }
            }
            catch (IOException ex)
            {
                Logger.getLogger(ClientFileManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        
    }
    
    /**
     * Adds a completed match to the queue.
     * 
     * @param active 
     */
    public void addActive(ActiveData active)
    {
        //block until clear to add match data
        blockAddingFilesToSendFlag.await();
        FileWriter fw = null;
        String fileName = getFileNameFromActiveData(active);
        File localFile = new File(localActiveFolder, fileName);
        String content = active.serialize();
        try
        {
            System.out.println("Writing File: " + localFile.getCanonicalPath() + "\n"
                    + "Content: " + content);
            fw = new FileWriter(localFile.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();
        }
        catch (IOException ex)
        {
            Logger.getLogger(ClientFileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally
        {
            try
            {
                if (fw != null)
                {
                    fw.close();
                }
            }
            catch (IOException ex)
            {
                Logger.getLogger(ClientFileManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        
    }

    /**
     * Gets an File array from the local directory that need to be sent to the
     * server.
     * <br/>
     * Locks the folder so that the files can be fetched without modification.
     *
     * @return
     */
    public File[] getFilesToSend()
    {
        this.blockAddingFilesToSendFlag.lock();
        File[] matchFiles = localMatchFolder.listFiles();
        File[] activeFiles = localActiveFolder.listFiles();        
        this.blockAddingFilesToSendFlag.unlock();
        File[] files = Utils.appendFileArrays(matchFiles, activeFiles);
        return files;
    }

    /**
     * Retrieves the String for the file name from the Match Data object.
     *
     * @param match
     * @return
     */
    public static String getFileNameFromMatchData(MatchData match)
    {
        return String.valueOf(match.getType().toString()) + "_" 
                + String.valueOf(match.getMatchMatchNumber()) + "_"
                + String.valueOf(match.getMatchTeamNumber() + "_"
                        + String.valueOf(match.getMatchScouter().trim().replaceAll(" ", "_").replace("\\", "").replace("/", "").replace(".", ""))
                        + "_" + String.valueOf(match.serialize().hashCode()) + ".json");
    }
    
    /**
     * Retrieves the String for the file name from the Match Data object.
     *
     * @param match
     * @return
     */
    public static String getFileNameFromActiveData(ActiveData active)
    {
        return String.valueOf(active.getType().toString()) + "_" 
                + String.valueOf(String.valueOf(active.getMatchTeamNumber() + "_"
                        + String.valueOf(active.getMatchRobotScouter().trim().replaceAll(" ", "_").replace("\\", "").replace("/", "").replace(".", ""))
                        + "_" + String.valueOf(active.serialize().hashCode()) + ".json"));
    }
}
