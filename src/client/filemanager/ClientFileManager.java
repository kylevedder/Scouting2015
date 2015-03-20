/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.filemanager;

import client.networking.SyncFilesClientThread;
import client.objects.UserDataInterface;
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
import main.Globals;
import org.json.JSONObject;
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

    private static final String ACTIVE_LOCAL_FOLDER_PATH = "./saves/local";
    private static final String ACTIVE_SERVER_FOLDER_PATH = "./saves/server";

    private File localFolder = null;
    private File serverFolder = null;

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

        localFolder = new File(ACTIVE_LOCAL_FOLDER_PATH);
        serverFolder = new File(ACTIVE_SERVER_FOLDER_PATH);

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
     * Saves user data in the appropriate folder.
     * 
     * @param parentFolder
     * @param fileName
     * @param userData 
     */
    public void saveUserDataAsFile(UserDataInterface userData)
    {
        String fileName = userData.getFileName();        
        File localFile = new File(localFolder, fileName);
        Utils.writeToFile(localFile, userData.serialize());
        
    }
    
    /**
     * Takes one or several JSON strings and saves them as files in the server folder.
     * @param jsonContents 
     */
    public void saveData(String jsonContents)
    {
        JSONObject json = new JSONObject(jsonContents);        
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
        File[] files = localFolder.listFiles();
        this.blockAddingFilesToSendFlag.unlock();        
        return files;
    }
}
