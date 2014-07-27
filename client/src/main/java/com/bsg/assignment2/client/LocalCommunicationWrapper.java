package com.bsg.assignment2.client;

import com.bsg.assignment2.common.CommunicationWrapper;

import java.util.concurrent.BlockingQueue;

/**
 * Created by rmistry on 2014/07/25.
 */
public interface LocalCommunicationWrapper extends CommunicationWrapper, Runnable {

    public void setqServerToClient(BlockingQueue<String> qServerToClient);

    public void setqClientToServer(BlockingQueue<String> qClientToServer);

    public void setFilename(String filename);

    public void initiateQueueDataExchange(BlockingQueue<String> qServerToClient, BlockingQueue<String> qClientToServer);
}
