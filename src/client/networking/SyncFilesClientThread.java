/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.networking;

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
        System.out.println("Starting new Sync Files Thread...");
        Socket connection = null;

        //try to connect
        for (int retries = 0; retries < this.numConnectRetries && connection == null; retries++)
        {
            try
            {
                connection = new Socket(this.address, this.port);
                System.out.println("Connected!");
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
                System.err.println("Connect exception, retrying...");
            }
        }

        if (connection == null)
        {
            Utils.showErrorBox("Unable to connect to server...");
            System.err.println("Unable to connect to server...");
            return;
        }

        File[] filesToSend = Main.matchManager.getFilesToSend();

        //setup output stream
        DataOutputStream outToServer = null;
        InputStream ins = null;
        BufferedReader reader = null;

        try
        {
            ins = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(ins));
            outToServer = new DataOutputStream(connection.getOutputStream());
        }
        catch (IOException ex)
        {
            Logger.getLogger(SyncFilesClientThread.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }

        for (File file : filesToSend)
        {
            try
            {

                String fileContentsString = Utils.readFileContents(file);
                if (fileContentsString != null)
                {
                    //create JSON object to send to server
                    JSONObject fileJSON = new JSONObject();
                    fileJSON.put(KEY_FILE_CONTENTS, fileContentsString);
                    fileJSON.put(KEY_FILE_NAME, file.getName());

                    //generate out String
                    String outToServerString = fileJSON.toString() + "\n";
                    //writes the JSON for the file to the server
                    outToServer.writeBytes(outToServerString);
                    //print debug line
                    System.out.println("Sent File: " + file.getName() + " \nWith data: " + outToServerString);
                }
                else
                {
                    System.out.println("File " + file.getName() + " unable to be read...");
                }
            }
            catch (IOException ex)
            {
                Logger.getLogger(SyncFilesClientThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        System.out.println("Finised sending files. \nNow reading files...");

        try
        {
            //In from client buffer
            StringBuilder stringBuffer = new StringBuilder();

            //read from the client
            String line;
            while ((line = reader.readLine()) != null)
            {
                stringBuffer.append(line);
                stringBuffer.append("\n");
            }

            //construct final string
            String recievedString = stringBuffer.toString();
            
//            Main.matchManager
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
            System.err.println("Error reading from server!");
        }

        System.out.println("Exiting SyncFilesThread...");
        try
        {
            outToServer.flush();
            outToServer.close();
            connection.close();
        }
        catch (IOException ex)
        {
            Logger.getLogger(SyncFilesClientThread.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}