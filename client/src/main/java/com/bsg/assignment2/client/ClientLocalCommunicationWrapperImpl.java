package com.bsg.assignment2.client;

import com.bsg.assignment2.common.LocalCommunicationWrapper;
import com.bsg.assignment2.common.SocketProtocol;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by rmistry on 2014/07/26.
 */
public class ClientLocalCommunicationWrapperImpl implements LocalCommunicationWrapper {
    private PipedInputStream inputStream;
    private PipedOutputStream outputStream;
    private ClientProtocol clientProtocol = new ClientProtocol();
    private Logger logger = Logger.getLogger(ClientLocalCommunicationWrapperImpl.class.getName());

    private BlockingQueue<String> queue = null;

    public void queues(BlockingQueue<String> queue) {
        queue.offer(SocketProtocol.CLIENT_INITIAL_READY);
    }

    public void initiate() {


        clientProtocol.setFilename("/Users/rmistry/test.data");
        try {
            logger.log(Level.INFO, "Starting the client Protocol");
            clientProtocol.startProtocol(inputStream, outputStream);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Could not initiate client data exchange protocol");
            e.printStackTrace();
        } finally {

            try {
                logger.log(Level.INFO, "Client closing streams");
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void setOutputStream(PipedOutputStream outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    public void setInputStream(PipedInputStream inputStream) {
        this.inputStream = inputStream;
    }


    public void setQueue(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        queues(queue);
    }
}
