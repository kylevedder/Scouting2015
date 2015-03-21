/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transmission;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.Utils;

/**
 *
 * @author Kyle
 */
public class FileTransmitter
{

    private static final String KEY_FILE_CONTENTS = "contents";
    private static final String KEY_FILE_NAME = "name";

    private static final String KEY_END_TRANSMITION = "end";
    private static final String VALUE_END_TRANSMITION = "transmition";

    private static String END_TRANSMISSION = null;

    private static FileTransmitter fileTransmitter = null;

    /**
     * Gets the singleton instance of the object.
     *
     * @return
     */
    public static FileTransmitter getInstance()
    {
        if (fileTransmitter == null)
        {
            fileTransmitter = new FileTransmitter();
        }
        return fileTransmitter;
    }

    /**
     * Sets up items for
     */
    private FileTransmitter()
    {
        Map<String, Object> map = new HashMap<>();
        map.put(KEY_END_TRANSMITION, VALUE_END_TRANSMITION);
        JSONObject json = new JSONObject(map);
        END_TRANSMISSION = json.toString();
    }    
    
    /**
     * Sends the given files over the data stream, ending the transmission, with
     * the agreed upon close
     *
     * @param filesToSend
     * @param out
     */
    public void sendFiles(File[] filesToSend, DataOutputStream out)
    {
        //Array of JSON objects which hold the data of each file
        JSONObject[] filesToSendAsJSON = new JSONObject[filesToSend.length];

        //populate JSONObject array with data
        for (int i = 0; i < filesToSend.length; i++)
        {
            File fileToSend = filesToSend[i];
            String fileContentsString = Utils.readFileContents(fileToSend);
            JSONObject fileToSendJSON = new JSONObject();
            fileToSendJSON.put(KEY_FILE_CONTENTS, fileContentsString);
            fileToSendJSON.put(KEY_FILE_NAME, fileToSend.getName());
            filesToSendAsJSON[i] = fileToSendJSON;
        }

        //generate JSONArray for sending
        JSONArray fileJSONArray = new JSONArray(filesToSendAsJSON);
        //generate out String
        String fileJSONArrayString = fileJSONArray.toString();
        
        System.out.println("CLIENT: Writing: " + fileJSONArrayString);
        System.out.println("CLIENT: Writing: " + FileTransmitter.getEndTransmissionString());
        try
        {
            //writes the JSON for the file to the server
            out.writeBytes(fileJSONArrayString + "\n");
            //end transmission of data
            out.writeBytes(FileTransmitter.getEndTransmissionString()  + "\n");
            out.flush();
        }
        catch (IOException ex)
        {
            Logger.getLogger(FileTransmitter.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Finised sending files.");
    }
    
    
    public void saveRecievedFiles()
    {
        
    }

    /**
     * String used to signify a transition end.
     *
     * @return
     */
    public static String getEndTransmissionString()
    {
        //ensures the END_TRANSMISSION var is setup
        if (fileTransmitter == null)
        {
            FileTransmitter.getInstance();
        }
        return END_TRANSMISSION;
    }

}
