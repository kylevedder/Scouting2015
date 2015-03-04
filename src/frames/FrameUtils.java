/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frames;

import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/**
 *
 * @author kyle
 */
public class FrameUtils
{
    /**
     * Generates an etched border with a title in the top left hand corner
     * @param title The title to give the border
     * @return 
     */
    public static Border createEtchedTitledBorder(String title)
    {
        Border border = BorderFactory.createTitledBorder(new EtchedBorder(), title, TitledBorder.LEFT, TitledBorder.TOP);
        return border;
    }
}
