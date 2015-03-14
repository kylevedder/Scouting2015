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
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.Charset;
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
        Socket connection = null;
        while (connection == null)
        {
            try
            {
                connection = new Socket(this.address, this.port);
            } catch (IOException ex)
            {

            }
        }
        while (isRunning)
        {
            Main.matchManager.filesToSendFlag.await();

            File fileToSend = null;
            try
            {
                if (Main.matchManager.hasFilesToSend())
                {
                    //Access the file
                    fileToSend = Main.matchManager.getFileToSend();

                    //setup connection                    
                    InputStream fileStream = new BufferedInputStream(new FileInputStream(fileToSend));
                    OutputStream out = connection.getOutputStream();
                    
                    PrintWriter write = new PrintWriter(out);                    
                    
                    //spit the "filestream" into the "out" stream
                    IOUtils.copy(fileStream, out);
                    //appends the new line to get the line to be read on the server end.
                    byte[] b = "\n".getBytes(Charset.forName("UTF-8"));
                    out.write(b);
                    //sends the buffered content out
                    out.flush();                   

                    System.out.println("Sent File: " + fileToSend.getName());
                }
                else
                {
                    Main.matchManager.filesToSendFlag.lock();
                }
            } catch (IOException ex)
            {
                Logger.getLogger(FileClient.class.getName()).log(Level.SEVERE, null, ex);
                //return any unwritten files to the queue
                Main.matchManager.returnFile(fileToSend);
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
