package com.bsg.assignment2.common;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Helper class to publish and read dat from BlockingQueues
 * Created by rmistry on 2014/07/27.
 */
public class BlockingQueueHelper {

    private static final Logger logger = Logger.getLogger(BlockingQueueHelper.class.getName());

    /**
     * Get data from a specific queues, waiting for the supplied period before timing out.
     *
     * @param queueName
     * @param timeout
     * @param timeUnit
     * @return
     */
    public static String pollQueue(BlockingQueue<String> queueName, Long timeout, TimeUnit timeUnit) {
        String data = null;
        try {
            data = queueName.poll(timeout, timeUnit);
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, "Error polling queue");
            e.printStackTrace();
        }
        return data;
    }

    /**
     * Put data on a specific queue. Timeout and Timeunit are not used.
     *
     * @param queue
     * @param data
     * @param timeout  - not used
     * @param timeUnit - not used
     */
    public static void offerToQueue(BlockingQueue<String> queue, String data, Long timeout, TimeUnit timeUnit) {
        try {
            queue.put(data);
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, "Error publishing to queue: ");
            e.printStackTrace();
        }
    }
}
