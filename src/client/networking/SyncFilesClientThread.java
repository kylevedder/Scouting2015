/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.networking;

import client.filemanager.ClientFileManager;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Main;
import org.json.JSONObject;
import transmission.TransmittedJSONHandler;
import utils.Utils;

/**
 *
 * @author kyle
 */
public class SyncFilesClientThread implements Runnable
{

    private int port = -1;
    private String address = null;
    private int numConnectRetries = 0;

    public static String KEY_FILE_NAME = "name";
    public static String KEY_FILE_CONTENTS = "contents";
    
    private TransmittedJSONHandler transmittedJSONHandler = TransmittedJSONHandler.getInstance();
    private ClientFileManager clientFileManager = ClientFileManager.getInstance();

    /**
     * Attempts to connect to the server and, if successful, sends the contents
     * of all files from the local directory to the server. Then, pulls the
     * latest server copy of all files.
     * <br/>
     * Once launched, it does not stop until its actions are completed.
     *
     * @param address
     * @param port
     * @param numConnectRetries
     */
    public SyncFilesClientThread(String address, int port, int numConnectRetries)
    {
        this.port = port;
        this.address = address;
        this.numConnectRetries = numConnectRetries;        
    }

    @Override
    public void run()
    {
        System.out.println("CLIENT: Starting new Sync Files Thread...");
        Socket connection = null;

        //try to connect
        for (int retries = 0; retries < this.numConnectRetries && connection == null; retries++)
        {
            try
            {
                connection = new Socket(this.address, this.port);
                System.out.println("CLIENT: Connected!");
            }
            catch (IOException ex)
            {                
                System.err.println("CLIENT: Connect exception, retrying...");
            }
        }

        if (connection == null)
        {
            Utils.showErrorBox("Unable to connect to server...");
            System.err.println("CLIENT: Unable to connect to server...");
            return;
        }        

        //setup IOs
        DataOutputStream outToServer = null;
        InputStream ins = null;
        BufferedReader reader = null;                

        try
        {
            //setup ins and outs
            ins = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(ins));
            outToServer = new DataOutputStream(connection.getOutputStream());      
            
            //get files to send
            File[] filesToSend = clientFileManager.getFilesToSend();
            //send files
            transmittedJSONHandler.sendFiles(filesToSend, outToServer);
            
            JSONObject[] jsonObjs = transmittedJSONHandler.recieveSentJSON(reader);
            
            //save each JSON object
            for(JSONObject json: jsonObjs)
            {
                clientFileManager.writeServerFile(json);
            }
            
            
        }
        catch (IOException ex)
        {
            Logger.getLogger(SyncFilesClientThread.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }                

    }
}