/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.networking;

import client.networking.SyncFilesClientThread;
import static client.networking.SyncFilesClientThread.KEY_FILE_CONTENTS;
import static client.networking.SyncFilesClientThread.KEY_FILE_NAME;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;
import server.filemanager.ServerFileManager;
import utils.Utils;

/**
 *
 * @author kyle
 */
public class SyncFilesServerThread implements Runnable
{

    private volatile boolean isRunning = true;

    private ServerFileManager fileManager = ServerFileManager.getInstance();

    ServerSocket serverSocket = null;
    private int port;

    public SyncFilesServerThread(int port)
    {
        this.port = port;
    }

    /**
     * Initializes the server to accept connections.
     */
    private void init()
    {
        try
        {
            serverSocket = new ServerSocket(port);
        }
        catch (IOException ex)
        {
            System.err.println("Server unable to open socket on port: " + port);
        }
    }

    @Override
    public void run()
    {
        System.out.println("Starting SyncFilesServer...");
        init();
        while (isRunning)
        {
            mainLoop();
        }
    }

    /**
     * Hook for the primary loop through the code.
     */
    private void mainLoop()
    {
        try
        {
            System.out.println("Awaiting connection...");
            //block for new connection            
            Socket socket = serverSocket.accept();
            System.out.println("Connection Recieved!");

            //setup in and out
            InputStream ins = socket.getInputStream();
            DataOutputStream outToServer = new DataOutputStream(socket.getOutputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(ins));
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

            //split into individual JSON objects
            String[] jsonStrings = recievedString.split("\n");
            JSONObject[] jsonObjs = new JSONObject[jsonStrings.length];

            //add each JSON string as JSONObject to array
            for (int i = 0; i < jsonStrings.length; i++)
            {
                String jsonString = jsonStrings[i];
                jsonObjs[i] = new JSONObject(jsonString);
            }

            System.out.println("Server Recieved:\n" + recievedString + "\nSaving files...");   //Prints the string content read from input stream

            for (JSONObject json : jsonObjs)
            {
                fileManager.writeFile(json);
            }
            System.out.println("All files written.");

            for (File file : fileManager.getFilesToSend())
            {
                try
                {

                    String fileContentsString = Utils.readFile(file);
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
        }
        catch (IOException ex)
        {
            Logger.getLogger(SyncFilesServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Gracefully stops the thread.
     */
    public void kill()
    {
        this.isRunning = false;
    }

}
