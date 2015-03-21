/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import client.frames.MainFrame;
import client.frames.ActiveFrame;
import client.frames.MatchFrame;
import server.frames.ServerFrame;

/**
 *
 * @author kyle
 */
public class Main
{

    public static MainFrame main = null;
    public static ServerFrame serverFrame = null;
    public static ActiveFrame activeFrame = null;
    public static MatchFrame matchFrame = null;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        serverFrame = new ServerFrame();
        activeFrame = new ActiveFrame();
        matchFrame = new MatchFrame();
        main = new MainFrame();
    }

}
