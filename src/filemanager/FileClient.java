/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filemanager;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Main;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author kyle
 */
public class FileClient implements Runnable
{

    private boolean isRunning = true;

    private int port;
    private String address = "";

    public FileClient(String address, int port)
    {
        this.port = port;
        this.address = address;
    }

    @Override
    public void run()
    {
        while (isRunning)
        {
            Main.matchManager.filesToSendFlag.await();
                        
            if (Main.matchManager.hasFilesToSend())
            {
                //Access the file
                File fileToSend = Main.matchManager.getFileToSend();
                try
                {

                    //setup connection
                    Socket connection = new Socket(this.address, this.port);

                    InputStream fileStream = new BufferedInputStream(new FileInputStream(fileToSend));
                    OutputStream out = connection.getOutputStream();

                    //spit the "filestream" into the "out" stream
                    IOUtils.copy(fileStream, out);
                    
                    //now, with the file sent, remove the file from the list
                    Main.matchManager.removeFile(fileToSend);
                } catch (IOException ex)
                {
                    Logger.getLogger(FileClient.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else
            {
                Main.matchManager.filesToSendFlag.unlock();
            }
        }
    }

    /**
     * Gracefully kills the thread
     */
    public void kill()
    {
        this.isRunning = false;
    }
}
