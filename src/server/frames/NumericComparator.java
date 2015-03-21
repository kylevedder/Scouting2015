/*
 * To change thislicense header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.frames;

import java.util.Comparator;

/**
 *
 * @author kyle
 */
public class NumericComparator implements Comparator
{

    @Override
    public int compare(Object t, Object t1)
    {
        String s1;
        String s2;

        double i1;
        double i2;

        int returnVal = 0;
        try
        {
            s1 = String.valueOf(t);
            s2 = String.valueOf(t1);
            i1 = Double.parseDouble(s1);
            i2 = Double.parseDouble(s2);
        }
        catch (Exception ex)
        {
            return 0;
        }
        //First integer is greater than the second
        if (i1 > i2)
        {
            returnVal = -1;
        }

        //Second integer is greater than the first
        if (i1 < i2)
        {
            returnVal = 1;
        }
        return returnVal;
    }

}
