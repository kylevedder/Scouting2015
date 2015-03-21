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
import org.json.JSONArray;
import org.json.JSONObject;
import server.filemanager.ServerFileManager;
import transmission.TransmittedJSONHandler;
import utils.Utils;

/**
 *
 * @author kyle
 */
public class SyncFilesServerThread implements Runnable
{

    private volatile boolean isRunning = true;

    private ServerFileManager serverFileManager = ServerFileManager.getInstance();
    private TransmittedJSONHandler fileTransmitter = TransmittedJSONHandler.getInstance();

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
            System.out.println("SERVER: Awaiting connection...");
            //block for new connection            
            Socket socket = serverSocket.accept();
            System.out.println("SERVER: Connection Recieved!");

            //setup in and out
            InputStream ins = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(ins));
            DataOutputStream outToClient = new DataOutputStream(socket.getOutputStream());

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

            System.out.println("SERVER: Finised reading...");

            //construct a JSON array from the recieved data
            JSONArray jsonArray = new JSONArray(stringBuffer.toString());

            System.out.println("SERVER: JSON Saved, writing to files...");

            for (int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject jsonOfFile = jsonArray.getJSONObject(i);
                serverFileManager.writeFile(jsonOfFile);
            }

            System.out.println("SERVER: All files written.\n Now, sending all files to client...");

            //get all files to send
            fileTransmitter.sendFiles(serverFileManager.getFilesToSend(), outToClient);
            System.out.println("SERVER: All Files sent!");
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
