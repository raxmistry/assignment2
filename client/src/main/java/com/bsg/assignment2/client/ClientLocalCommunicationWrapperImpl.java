package com.bsg.assignment2.client;

import com.bsg.assignment2.common.SocketProtocol;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by rmistry on 2014/07/26.
 */
public class ClientLocalCommunicationWrapperImpl implements LocalCommunicationWrapper {
    public static final long TIMEOUT = 10000L;
    private ClientProtocol clientProtocol = new ClientProtocol();
    private Logger logger = Logger.getLogger(ClientLocalCommunicationWrapperImpl.class.getName());
    private String filename;

    private BlockingQueue<String> qServerToClient;
    private BlockingQueue<String> qClientToServer;

    public void queues(BlockingQueue<String> qServerToClient, BlockingQueue<String> qClientToServer) {


        offerToQueue(qClientToServer, SocketProtocol.CLIENT_INITIAL_READY, TIMEOUT, TimeUnit.MILLISECONDS);


        String head = null;
        head = pollQueue(qServerToClient, TIMEOUT, TimeUnit.MILLISECONDS);
        logger.log(Level.INFO, head);


        if (head.compareTo(SocketProtocol.SERVER_INITIAL_OK) == 0) {
            offerToQueue(qClientToServer, filename, TIMEOUT, TimeUnit.MILLISECONDS);
        }

        head = pollQueue(qServerToClient, TIMEOUT, TimeUnit.MILLISECONDS);
        logger.log(Level.INFO, head);
        if (head.compareTo(SocketProtocol.SERVER_FILENAME_OK) == 0) {
            offerToQueue(qClientToServer, SocketProtocol.CLIENT_READY_FOR_DATA, TIMEOUT, TimeUnit.MILLISECONDS);
        }

        head = pollQueue(qServerToClient, TIMEOUT, TimeUnit.MILLISECONDS);

        System.out.println(head);

        offerToQueue(qClientToServer, SocketProtocol.CLIENT_DONE, TIMEOUT, TimeUnit.MILLISECONDS);
    }

    private String pollQueue(BlockingQueue<String> queueName, Long timeout, TimeUnit timeUnit) {
        String data = null;
        try {
            data = queueName.poll(timeout, timeUnit);
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, "Error polling queue");
            e.printStackTrace();
        }

        return data;
    }

    private void offerToQueue(BlockingQueue<String> queue, String data, Long timeout, TimeUnit timeUnit) {
        try {
            queue.put(data);
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, "Error publishing to queue: ");
            e.printStackTrace();
        }
    }

    public void setqServerToClient(BlockingQueue<String> qServerToClient) {
        this.qServerToClient = qServerToClient;
    }


    public void setqClientToServer(BlockingQueue<String> qClientToServer) {
        this.qClientToServer = qClientToServer;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Override
    public void run() {
        queues(qServerToClient, qClientToServer);
    }
}
