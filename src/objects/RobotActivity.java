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
public class RobotActivity
{

    private RobotActivityType type = RobotActivityType.INACTIVE;
    private String comment = null;
    
    /**
     * Object for holding activity
     * @param type 
     */
    public RobotActivity(RobotActivityType type, String comment)
    {
        this.type = type;
        this.comment = comment;
    }
    
}
