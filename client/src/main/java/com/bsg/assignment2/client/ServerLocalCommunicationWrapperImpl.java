package com.bsg.assignment2.client;

import com.bsg.assignment2.common.LocalCommunicationWrapper;
import com.bsg.assignment2.common.ServerProtocol;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by rmistry on 2014/07/26.
 */
public class ServerLocalCommunicationWrapperImpl implements LocalCommunicationWrapper {

    ServerProtocol serverProtocol = new ServerProtocol();
    private Logger logger = Logger.getLogger(ServerLocalCommunicationWrapperImpl.class.getName());
    private PipedOutputStream outputStream;
    private PipedInputStream inputStream;
    private BlockingQueue<String> queue;

    public void queues(BlockingQueue<String> queue) {
        //logger.log(Level.INFO, "Queue contains element: " + queue.contains(SocketProtocol.CLIENT_INITIAL_READY));

        try {
            String object = (String) queue.take();

            logger.log(Level.INFO, "I retrieved a message from the queue: " + object);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void initiate() {
        try {
            logger.log(Level.INFO, "Starting the server Protocol");
            serverProtocol.startProtocol(outputStream, inputStream);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error trying to start the server protocol");
            e.printStackTrace();
        } finally {
            try {
                logger.log(Level.INFO, "Server closing streams");
                outputStream.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    }

    public void run() {
        queues(queue);
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
}
