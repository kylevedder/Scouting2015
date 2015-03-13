/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threading;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kyle
 */
public class Flag
{    
    private volatile Semaphore semaphore = null;
    private volatile CountDownLatch latch = null;

    /**
     * Thread safe resource lock for indicating if resources are available.
     *
     * @param locked
     */
    public Flag(boolean locked)
    {
        //sets locked or unlocked        
        latch = new CountDownLatch((locked)? 1 : 0);
    }

    /**
     * Aquires the flag, making other aquire requests block until this thread calls unlock.
     */
    public void lock()
    {
        latch = new CountDownLatch(1);
    }
    
    
    /**
     * Unlocks the lock, allowing await() requests to continue.
     */
    public void unlock()
    {
        latch.countDown();
    }
    
    /**
     * Await the latch unlock.
     */
    public void await()
    {
        try
        {
            latch.await();
        } catch (InterruptedException ex)
        {
            Logger.getLogger(Flag.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
