/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

/**
 *
 * @author kyle
 */
public class StackTote
{

    private final int POINTS_PER_TOTE = 2;

    private int height = 0;

    /**
     * Creates a new tote stack of the given height
     *
     * @param height
     */
    public StackTote(int height)
    {
        this.height = height;
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
            this.height = Integer.parseInt(height);
        } 
        catch (Exception ex)
        {
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
        return POINTS_PER_TOTE * height;
    }

    @Override
    public String toString()
    {
        return "Height:" + height;
    }

}
