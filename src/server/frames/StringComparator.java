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
public class StringComparator implements Comparator
{

    @Override
    public int compare(Object t, Object t1)
    {
        String s1 = String.valueOf(t);
        String s2 = String.valueOf(t1);
        if (s1 != null && !s1.isEmpty() && s2 != null && !s1.isEmpty())
        {
            char c1 = s1.charAt(0);
            char c2 = s2.charAt(0);

            int returnVal = 0;

            //First integer is greater than the second
            if (c1 > c2)
            {
                returnVal = -1;
            }

            //Second integer is greater than the first
            if (c1 < c2)
            {
                returnVal = 1;
            }
            return returnVal;
        }
        else
        {
            return 0;
        }
    }

}
