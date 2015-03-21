/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transmission;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.FileUtils;

/**
 *
 * @author Kyle
 */
public class TransmittedJSONHandler
{

    private static final String KEY_FILE_CONTENTS = "contents";
    private static final String KEY_FILE_NAME = "name";

    private static final String KEY_END_TRANSMITION = "end";
    private static final String VALUE_END_TRANSMITION = "transmition";

    private static String END_TRANSMISSION = null;

    private static TransmittedJSONHandler fileTransmitter = null;

    /**
     * Gets the singleton instance of the object.
     *
     * @return
     */
    public static TransmittedJSONHandler getInstance()
    {
        if (fileTransmitter == null)
        {
            fileTransmitter = new TransmittedJSONHandler();
        }
        return fileTransmitter;
    }

    /**
     * Sets up items for
     */
    private TransmittedJSONHandler()
    {
        Map<String, Object> map = new HashMap<>();
        map.put(KEY_END_TRANSMITION, VALUE_END_TRANSMITION);
        JSONObject json = new JSONObject(map);
        END_TRANSMISSION = json.toString();
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
            TransmittedJSONHandler.getInstance();
        }
        return END_TRANSMISSION;
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
            String fileContentsString = FileUtils.readFileContents(fileToSend);
            JSONObject fileContentsJSON = new JSONObject(fileContentsString);
            JSONObject fileToSendJSON = new JSONObject();
            fileToSendJSON.put(KEY_FILE_CONTENTS, fileContentsJSON);
            fileToSendJSON.put(KEY_FILE_NAME, fileToSend.getName());
            filesToSendAsJSON[i] = fileToSendJSON;
        }

        //generate JSONArray for sending
        JSONArray fileJSONArray = new JSONArray(filesToSendAsJSON);
        //generate out String
        String fileJSONArrayString = fileJSONArray.toString();

        System.out.println("CLIENT: Writing: " + fileJSONArrayString);
        System.out.println("CLIENT: Writing: " + TransmittedJSONHandler.getEndTransmissionString());
        try
        {
            //writes the JSON for the file to the server
            out.writeBytes(fileJSONArrayString + "\n");
            //end transmission of data
            out.writeBytes(TransmittedJSONHandler.getEndTransmissionString() + "\n");
            out.flush();
        }
        catch (IOException ex)
        {
            Logger.getLogger(TransmittedJSONHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Finised sending files.");
    }

    /**
     * Recieves sent JSON objects and converts them into a JSONObject array.
     * @param reader
     * @return 
     */
    public JSONObject[] recieveSentJSON(BufferedReader reader)
    {
        JSONObject[] jsonObjects = null;
        try
        {
            //In from client buffer
            StringBuilder stringBuffer = new StringBuilder();

            System.out.println("SERVER: Reading from client...");

            //read from the client
            String line;

            //while not end transmission
            while (!(line = reader.readLine().trim()).equals(TransmittedJSONHandler.getEndTransmissionString()))
            {
                System.out.println("SERVER: RECIEVED: " + line);
                stringBuffer.append(line);
                stringBuffer.append("\n");
            }
            
            JSONArray jsonArray = new JSONArray(stringBuffer.toString());
            jsonObjects = new JSONObject[jsonArray.length()];
            
            //set each element of the JSON array
            for (int i = 0; i < jsonArray.length(); i++)
            {
                jsonObjects[i] = jsonArray.getJSONObject(i);
            }
        }
        catch (IOException ex)
        {
            Logger.getLogger(TransmittedJSONHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return jsonObjects;
    }

    /**
     * Saves the contents of a saved JSON file as a String
     *
     * @param rootFolder
     * @param transmittedJSON
     */
    public void saveAsFile(File rootFolder, JSONObject transmittedJSON)
    {
        File fileToSave = new File(rootFolder, this.getFileName(transmittedJSON));
        FileUtils.writeToFile(fileToSave, this.getFileContents(transmittedJSON).toString());
    }

    /**
     * Gets the file name for the file to be saved as from the transmitted JSON.
     *
     * @param transmittedJSON
     * @return
     */
    public String getFileName(JSONObject transmittedJSON)
    {
        return transmittedJSON.getString(KEY_FILE_NAME);
    }

    /**
     * Gets the file contents from the transmitted JSON.
     *
     * @param transmittedJSON
     * @return
     */
    public JSONObject getFileContents(JSONObject transmittedJSON)
    {
        return transmittedJSON.getJSONObject(KEY_FILE_CONTENTS);
    }
}
