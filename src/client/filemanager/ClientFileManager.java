/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.filemanager;

import client.objects.UserDataInterface;
import java.io.File;
import org.json.JSONObject;
import transmission.TransmittedJSONHandler;
import utils.FileUtils;
import utils.Flag;

/**
 *
 * @author Kyle
 */
public class ClientFileManager
{

    //singleton object
    private static ClientFileManager matchManager = null;
    
    private TransmittedJSONHandler transmittedJSONHandler = TransmittedJSONHandler.getInstance();

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
     * Saves user data in the local folder.
     * 
     * @param parentFolder
     * @param fileName
     * @param userData 
     */
    public void saveUserDataAsFile(UserDataInterface userData)
    {
        String fileName = userData.getFileName();        
        File localFile = new File(localFolder, fileName);
        FileUtils.writeToFile(localFile, userData.serialize());        
    }
    
    /**
     * Writes a file in the server file directory.
     * @param json 
     */
    public void writeServerFile(JSONObject json)
    {
        //block until clear to add match data
        blockAddingFilesToSendFlag.await();
        transmittedJSONHandler.saveAsFile(this.serverFolder, json);
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
