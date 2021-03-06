package com.bsg.assignment2.client;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;

/**
 * Created by rmistry on 2014/07/26.
 */
public class LocalQueueTest {
    private Logger logger = Logger.getLogger(LocalQueueTest.class.getName());

    public static void main(String args[]) {
        BlockingQueue<String> qServerToClient = new LinkedBlockingQueue<String>();
        BlockingQueue<String> qClientToServer = new LinkedBlockingQueue<String>();

        ServerLocalCommunicationWrapperImpl serverWrapper = new ServerLocalCommunicationWrapperImpl();
        serverWrapper.setqServerToClient(qServerToClient);
        serverWrapper.setqClientToServer(qClientToServer);

        ClientLocalCommunicationWrapperImpl clientWrapper = new ClientLocalCommunicationWrapperImpl();
        clientWrapper.setqServerToClient(qServerToClient);
        clientWrapper.setqClientToServer(qClientToServer);
        clientWrapper.setFilename("/Users/rmistry/test.data");

        new Thread(serverWrapper).start();
        new Thread(clientWrapper).start();

    }
}
