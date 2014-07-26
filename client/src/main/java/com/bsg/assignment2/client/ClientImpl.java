package com.bsg.assignment2.client;

import com.bsg.assignment2.common.CommunicationWrapper;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by rmistry on 2014/07/25.
 */
public class ClientImpl implements Client {
    private Logger logger = Logger.getLogger(ClientImpl.class.getName());
    private CommunicationWrapper communicationWrapper;

    @Override
    public void start() {
        //TODO: Implement

        int count = 0;
        while (count < 1) {
            System.out.println("Enter a filename to display: ");
            byte b[] = new byte[1000];

            //TODO: Change this to command-line input?
            b = new String("/Users/rmistry/test.data").getBytes();

            logger.log(Level.INFO, "File to read from server: " + new String(b));

            count++;
        }
    }
}
