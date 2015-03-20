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
import org.json.JSONObject;
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
     * @param json 
     */
    public void writeFile(JSONObject json)
    {
        //block until clear to add match data
        blockAddingFilesToSendFlag.await();

        //setup file writer
        FileWriter fw = null;

        //extract file name from the JSON
        String fileName = json.getString(SyncFilesClientThread.KEY_FILE_NAME);
                        
        File localFile = new File(savesFolder, fileName);
        String content = json.toString();
        try
        {
            System.out.println("Writing Server File: " + localFile.getCanonicalPath() + "\n"
                    + "Content: " + content);
            fw = new FileWriter(localFile.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();
        }
        catch (IOException ex)
        {
            Logger.getLogger(ServerFileManager.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(ServerFileManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }    

    /**
     * Gets an File array from the local directory that need to be sent to the
     * client.
     * <br/>
     * Locks the folder so that the files can be fetched without modification.
     *
     * @return
     */
    public File[] getFilesToSend()
    {
        this.blockAddingFilesToSendFlag.lock();
        File[] files = savesFolder.listFiles();        
        this.blockAddingFilesToSendFlag.unlock();        
        return files;
    }    
}
