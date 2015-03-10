/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.stacks;

import java.util.HashMap;
import org.json.JSONObject;


/**
 *
 * @author kyle
 */
public class StackTote implements StackBase
{

    public static String HEIGHT_KEY = "height";    
    
    private final int POINTS_PER_TOTE = 2;

    private int stackHeight = 0;

    /**
     * Creates a new tote stack of the given height
     *
     * @param height
     */
    public StackTote(int height)
    {
        this.stackHeight = height;
    }

    /**
     * A wrapper object for a scored tote stack
     *
     * @param height
     */
    public StackTote(String height)
    {
        try
        {
            this.stackHeight = Integer.parseInt(height);
        } 
        catch (Exception ex)
        {
            this.stackHeight = 0;
            ex.printStackTrace();
        }
    }

    /**
     * Gets the point value of the tote stack
     *
     * @return
     */
    public int getScore()
    {
        return POINTS_PER_TOTE * stackHeight;
    }

    @Override
    public String toString()
    {
        return "Height:" + stackHeight;
    }

    @Override
    public JSONObject getJSONObject()
    {
        HashMap<String, Object> map = new HashMap<>();        
        map.put(HEIGHT_KEY, stackHeight);
        JSONObject json = new JSONObject(map);
        return json;
    }

}
