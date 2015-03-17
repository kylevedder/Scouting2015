/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.networking;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Main;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

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
        int retryTimes = 0;

        //try to connect
        while (connection == null)
        {
            if (retryTimes < this.numConnectRetries)
            {
                try
                {
                    connection = new Socket(this.address, this.port);
                }
                catch (IOException ex)
                {
                    //ignored
                }
            }
            else
            {
                System.err.println("Unable to connect to server...");
                return;
            }
            retryTimes++;
        }

        File[] filesToSend = Main.matchManager.getFilesToSend();

        //setup output stream
        DataOutputStream outToServer = null;
        try
        {
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

                String fileContentsString = readFile(file);
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

    /**
     * Reads the contents of a file and assembles it into a string.
     *
     * @param f
     * @return
     */
    private String readFile(File f)
    {
        String contents = null;
        try (BufferedReader br = new BufferedReader(new FileReader(f));)
        {

            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null)
            {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            contents = sb.toString();
        }
        catch (FileNotFoundException ex)
        {
            Logger.getLogger(SyncFilesClientThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex)
        {
            Logger.getLogger(SyncFilesClientThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        return contents;
    }
}
