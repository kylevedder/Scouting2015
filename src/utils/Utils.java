/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import client.filemanager.ClientFileManager;
import client.networking.SyncFilesClientThread;
import client.objects.UserDataInterface;
import client.objects.ObjectType;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.json.JSONObject;

/**
 *
 * @author Kyle
 */
public class Utils
{

    /**
     * Writes the given contents to the given file.
     *
     * @param fileToWriteTo
     * @param contents
     * @return
     */
    public static void writeToFile(File fileToWriteTo, String content)
    {
        FileWriter fw = null;
        try
        {
            System.out.println("Writing File: " + fileToWriteTo.getCanonicalPath() + "\n"
                    + "Content: " + content);
            fw = new FileWriter(fileToWriteTo.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();
        }
        catch (IOException ex)
        {
            Logger.getLogger(ClientFileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally
        {
            try
            {
                if (fw != null)
                {
                    fw.close();
                }
            }
            catch (IOException ex)
            {
                Logger.getLogger(ClientFileManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Reads the contents of a file and assembles it into a string.
     *
     * @param f
     * @return
     */
    public static String readFileContents(File f)
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

    /**
     * Appends two given file arrays.
     *
     * @param first
     * @param second
     * @return
     */
    public static File[] appendFileArrays(File[] first, File[] second)
    {
        List<File> both = new ArrayList<>(first.length + second.length);
        Collections.addAll(both, first);
        Collections.addAll(both, second);
        return both.toArray(new File[both.size()]);
    }

    /**
     * Removes all non-numeric chars from the string
     *
     * @param s0
     * @return
     */
    public static String removeNonNumericChars(String s0)
    {
        return s0.replaceAll("[^0-9]", "");
    }

    /**
     * Load a font from the fonts folder with the given path
     *
     * @param path - path to the font
     * @return
     */
    public static Font loadFont(String path)
    {
        InputStream is = Utils.class.getResourceAsStream(path);
        Font font = null;
        try
        {
            font = Font.createFont(Font.TRUETYPE_FONT, is);
            System.out.println("Loaded font from: " + Utils.class.getResource(path).getPath());
        }
        catch (FontFormatException ex)
        {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex)
        {

        }
        return font;
    }

    /**
     * Creates an OK/Cancel message with a custom body and title.
     *
     * @param title
     * @param contents
     * @return
     */
    public static boolean showOKCancelAndGetResponse(String title, String contents)
    {
        //0 is the OK response.
        return JOptionPane.showConfirmDialog(null, contents, title, JOptionPane.OK_CANCEL_OPTION) == 0;
    }

    /**
     * Creates a user input message with a custom body, title, and an empty
     * default suggestion.
     *
     * @param title
     * @param body
     * @return
     */
    public static String showInputBoxAndGetResponse(String title, String body)
    {
        return showInputBoxAndGetResponse(title, body, "");
    }

    /**
     * Creates a user input message with a custom body, title, and default
     * value.
     *
     * @param title
     * @param body
     * @param defaultValue
     * @return
     */
    public static String showInputBoxAndGetResponse(String title, String body, String defaultValue)
    {
        return (String) JOptionPane.showInputDialog(null,
                body,
                title,
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                defaultValue);
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
     * Creates a Warning message with a custom body.
     *
     * @param bodyMessage String holding message body
     */
    public static void showWarningBox(String bodyMessage)
    {
        JOptionPane.showMessageDialog(null, bodyMessage, "Warning!", JOptionPane.WARNING_MESSAGE);
    }
}
