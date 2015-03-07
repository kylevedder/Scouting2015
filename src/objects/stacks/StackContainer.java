/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.stacks;

/**
 *
 * @author kyle
 */
public class StackContainer implements StackBase
{
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
    
    

}