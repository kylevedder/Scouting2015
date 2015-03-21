/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import client.frames.MainFrame;
import client.filemanager.ClientFileManager;
import server.networking.SyncFilesServerThread;
import transmission.FileTransmitter;

/**
 *
 * @author kyle
 */
public class Main
{   

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {        
        MainFrame main = new MainFrame();
        Thread serverThread = new Thread(new SyncFilesServerThread(Globals.PORT));
        serverThread.start();
    }

}
