/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import javax.swing.JOptionPane;

/**
 *
 * @author Kyle
 */
public class Utils
{
    /**
     * Removes all non-numeric chars from the string
     * @param s0
     * @return 
     */
    public static String removeNonNumericChars(String s0)
    {
        return s0.replaceAll("[^0-9]", "");
    }
    
    
    /**
     * Creates an error message with a custom body.
     *
     * @param errorMessage String holding error message body
     */
    public static void showErrorBox(String errorMessage)
    {
        JOptionPane.showMessageDialog(null, errorMessage, "Error!", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Creates an Info message with a custom body.
     *
     * @param bodyMessage String holding message body
     */
    public static void showPopupBox(String bodyMessage)
    {
        JOptionPane.showMessageDialog(null, bodyMessage, "Great Success!", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Creates an Warning message with a custom body.
     *
     * @param bodyMessage String holding message body
     */
    public static void showWarningBox(String bodyMessage)
    {
        JOptionPane.showMessageDialog(null, bodyMessage, "Warning!", JOptionPane.WARNING_MESSAGE);
    }   
}
