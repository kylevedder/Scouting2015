/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.filemanager;

import client.networking.SyncFilesClientThread;
import client.objects.ObjectType;
import client.objects.activedata.ActiveData;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import client.objects.matchdata.MatchData;
import java.util.ArrayList;
import org.json.JSONObject;
import transmission.TransmittedJSONHandler;
import utils.Flag;
import utils.Utils;

/**
 *
 * @author Kyle
 */
public class ServerFileManager
{

    //singleton object
    private static ServerFileManager matchManager = null;

    private TransmittedJSONHandler fileTransmitter = TransmittedJSONHandler.getInstance();

    private Flag blockAddingFilesToSendFlag = new Flag(false);

    private static final String SAVES_FOLDER_PATH = "./serverSaves";

    private File savesFolder = null;

    /**
     * Gets the singleton instance of this class.
     *
     * @return
     */
    public static ServerFileManager getInstance()
    {
        if (matchManager == null)
        {
            matchManager = new ServerFileManager();
        }
        return matchManager;
    }

    private ServerFileManager()
    {
        savesFolder = new File(SAVES_FOLDER_PATH);

        //create local folder, if doesn't exist
        if (!savesFolder.exists())
        {
            savesFolder.mkdirs();
        }
        //leave unlocked, should be locked for pulling all files from the SyncFilesThread only
        blockAddingFilesToSendFlag.unlock();
    }

    /**
     * Writes a file in the appropriate file directory.
     *
     * @param json
     */
    public void writeFile(JSONObject json)
    {
        //block until clear to add match data
        blockAddingFilesToSendFlag.await();
        fileTransmitter.saveAsFile(savesFolder, json);
    }

    /**
     * Gets an File array from the local directory that need to be sent to the
     * client.
     * <br/>
     * Locks the folder so that the files can be fetched without modification.
     *
     * @return
     */
    public File[] getAllFiles()
    {
        this.blockAddingFilesToSendFlag.lock();
        File[] files = savesFolder.listFiles();
        this.blockAddingFilesToSendFlag.unlock();
        return files;
    }

    /**
     * Gets all active files from the server directory.
     * @return 
     */
    public ArrayList<File> getActiveFiles()
    {
        File[] allFiles = this.getAllFiles();
        ArrayList<File> filesToSend = new ArrayList<File>();
        for (File f : allFiles)
        {
            if (f.getName().toLowerCase().startsWith(ObjectType.ACTIVE.toString().toLowerCase()))
            {
                filesToSend.add(f);
            }
        }        
        return filesToSend;
    }
    
    /**
     * Gets all match files from the server directory.
     * @return 
     */
    public ArrayList<File> getMatchFiles()
    {
        File[] allFiles = this.getAllFiles();
        ArrayList<File> filesToSend = new ArrayList<File>();
        for (File f : allFiles)
        {
            if (f.getName().toLowerCase().startsWith(ObjectType.MATCH.toString().toLowerCase()))
            {
                filesToSend.add(f);
            }
        }        
        return filesToSend;
    }
}
