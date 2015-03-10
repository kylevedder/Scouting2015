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
public class StackContainer implements StackBase
{
    public static String HEIGHT_KEY = "height";
    public static String NOODLE_KEY = "noodle";
    
    private final int POINTS_PER_LEVEL = 4;
    private final int POINTS_FOR_NOODLE = 6;

    private int stackHeight = 0;
    private boolean hasNoodle = false;

    
        
    /**
     * A wrapper object for a scored container on top of a tote stack.
     * 
     * @param stackHeight
     * @param hasNoodle 
     */
    public StackContainer(int stackHeight, boolean hasNoodle)
    {
        this.stackHeight = stackHeight;
        this.hasNoodle = hasNoodle;
    }

    /**
     * A wrapper object for a scored container on top of a tote stack.
     * 
     * @param stackHeight
     * @param hasNoodle 
     */
    public StackContainer(String stackHeight, boolean hasNoodle)
    {
        this.hasNoodle = hasNoodle;
        try
        {
            this.stackHeight = Integer.valueOf(stackHeight);            
        } 
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    /**
     * Score for the new stack.
     * @return 
     */
    public int getScore()
    {
        return POINTS_PER_LEVEL * stackHeight + ((hasNoodle)? POINTS_FOR_NOODLE : 0);
    }

    @Override
    public String toString()
    {
        return "Stack Height:" + stackHeight + " Noodle:" + hasNoodle;
    }

    @Override
    public JSONObject getJSONObject()
    {
        HashMap<String, Object> map = new HashMap<>();        
        map.put(HEIGHT_KEY, stackHeight);
        map.put(NOODLE_KEY, hasNoodle);
        JSONObject json = new JSONObject(map);        
        return json;
    }
    
    

}
