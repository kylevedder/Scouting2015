/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.objects.stacks;

import org.json.JSONObject;



/**
 *
 * @author Kyle
 */
public interface StackBase
{
    public int getScore();   
    @Override
    public String toString();    
    
    public JSONObject serialize();
                
}
