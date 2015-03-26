/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.objects.stacks;

import java.util.HashMap;
import org.json.JSONObject;


/**
 *
 * @author kyle
 */
public class StackTote implements StackBase
{

    public static String KEY_HEIGHT = "height";    
    
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
     * Gets the height of the stack.
     * @return 
     */
    public int getHeight()
    {
        return stackHeight;
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
        return "Height: " + getHeight();
    }

    @Override
    public JSONObject serialize()
    {
        HashMap<String, Object> map = new HashMap<>();        
        map.put(KEY_HEIGHT, stackHeight);
        JSONObject json = new JSONObject(map);
        return json;
    }

    /**
     * Converts the JSON string into an object.
     * @param jsonStr
     * @return 
     */
    public static StackTote deserialize(String jsonStr)
    {
        JSONObject json = new JSONObject(jsonStr);
        return deserialize(json);
    }
    
    /**
     * Converts the JSON object into an object.
     * @param json
     * @return 
     */
    public static StackTote deserialize(JSONObject json)
    {        
        return new StackTote(json.getInt(KEY_HEIGHT));
    }
}
