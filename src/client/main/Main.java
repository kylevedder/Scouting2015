/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.main;

import client.frames.MainFrame;
import client.filemanager.MatchManager;

/**
 *
 * @author kyle
 */
public class Main
{

    public static MatchManager matchManager = null;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {        
        matchManager = MatchManager.getInstance();
        MainFrame main = new MainFrame();
    }
    
}
