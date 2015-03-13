/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filemanager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import objects.MatchData;
import threading.Flag;

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

    public Flag filesToSendFlag = new Flag(true);
    public static final List<File> filesToSend = Collections.synchronizedList(new ArrayList<File>());

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

        filesToSendFlag.lock();
        Thread fileSenderThread = new Thread(new FileClient(host, port));
    }

    /**
     * Adds a completed match to the queue.
     *
     * @param match
     */
    public void addMatch(MatchData match)
    {
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
        } catch (IOException ex)
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
            } catch (IOException ex)
            {
                Logger.getLogger(MatchManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        synchronized (filesToSend)
        {
            filesToSend.add(localFile);
        }
    }

    /**
     * Gets a file to send to
     *
     * @return
     */
    public File getFileToSend()
    {
        synchronized (filesToSend)
        {
            if (!filesToSend.isEmpty())
            {
                return filesToSend.get(0);
            }
            else
            {
                return null;
            }
        }
    }

    /**
     * Checks to see of there are files to send to the server.
     *
     * @return
     */
    public boolean hasFilesToSend()
    {
        synchronized (filesToSend)
        {
            return !filesToSend.isEmpty();
        }
    }

    /**
     * Removes the instance of passed file from the list
     *
     * @param f
     */
    public void removeFile(File f)
    {
        synchronized (filesToSend)//hi kyle -pimp
        {
            filesToSend.remove(f);
        }
    }

    /**
     * Retrieves the String for the file name from the Match Data object.
     *
     * @param match
     * @return
     */
    private String getFileNameFromMatchData(MatchData match)
    {
        return String.valueOf(match.getMatchMatchNumber()) + "_" + String.valueOf(match.getMatchTeamNumber() + ".txt");
    }
}
