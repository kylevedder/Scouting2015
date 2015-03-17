/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.filemanager;

import client.networking.SyncFilesClientThread;
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
import client.objects.MatchData;
import utils.Flag;

/**
 *
 * @author Kyle
 */
public class MatchManager
{

    //singleton object
    private static MatchManager matchManager = null;

    private String host = "127.0.0.1";
    private int port = 8080;

    private Flag blockAddingFilesToSendFlag = new Flag(false);

    private static final String LOCAL_FOLDER_PATH = "./saves/match/local";
    private static final String SERVER_FOLDER_PATH = "./saves/match/server";

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

        //create local folder, if doesn't exist
        if (!localFolder.exists())
        {
            localFolder.mkdirs();
        }
        //create server folder, if doesn't exist
        if (!serverFolder.exists())
        {
            serverFolder.mkdirs();
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
        File localFile = new File(localFolder, fileName);
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
            Logger.getLogger(MatchManager.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(MatchManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Thread fileSenderThread = new Thread(new SyncFilesClientThread(host, port, 5));
        fileSenderThread.start();
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
        File[] files = (localFolder.listFiles());
        this.blockAddingFilesToSendFlag.unlock();
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
        return String.valueOf(match.getMatchMatchNumber()) + "_"
                + String.valueOf(match.getMatchTeamNumber() + "_"
                        + String.valueOf(match.getMatchScouter().trim().replaceAll(" ", "_").replace("\\", "").replace("/", "").replace(".", ""))
                        + "_" + String.valueOf(match.serialize().hashCode()) + ".json");
    }
}
