/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import client.filemanager.ClientFileManager;
import client.networking.SyncFilesClientThread;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kyle
 */
public class FileUtils
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
}
