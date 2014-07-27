package com.bsg.assignment2.client;

import com.bsg.assignment2.common.SocketProtocol;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Local Communication Wrapper for the client.
 * Created by rmistry on 2014/07/26.
 */
public class ClientLocalCommunicationWrapperImpl implements LocalCommunicationWrapper, ClientProtocol {
    public static final long TIMEOUT = 10000L;
    private ClientProtocolImpl clientProtocol = new ClientProtocolImpl();
    private Logger logger = Logger.getLogger(ClientLocalCommunicationWrapperImpl.class.getName());
    private String filename;

    private BlockingQueue<String> qServerToClient;
    private BlockingQueue<String> qClientToServer;
    private OutputWriter outputWriter;

    /**
     * Start data exchange between the client and server using queues.
     *
     * @param qServerToClient
     * @param qClientToServer
     */
    @Override
    public void initiateQueueDataExchange(BlockingQueue<String> qServerToClient, BlockingQueue<String> qClientToServer) {


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

        outputWriter.writeData(head);

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
        initiateQueueDataExchange(qServerToClient, qClientToServer);
    }

    @Override
    @Autowired
    public void setOutputWriter(OutputWriter outputWriter) {
        this.outputWriter = outputWriter;
    }
}
