package com.bsg.assignment2.client;

import com.bsg.assignment2.common.*;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by rmistry on 2014/07/26.
 */
public class ServerLocalCommunicationWrapperImpl implements LocalCommunicationWrapper {

    public static final long TIMEOUT = 10000L;
    ServerProtocol serverProtocol = new ServerProtocol();
    private Logger logger = Logger.getLogger(ServerLocalCommunicationWrapperImpl.class.getName());
    private PipedOutputStream outputStream;
    private PipedInputStream inputStream;
    private BlockingQueue<String> qServerToClient;
    private BlockingQueue<String> qClientToServer;

    public void queues(BlockingQueue<String> qServerToClient, BlockingQueue<String> qClientToServer) {


        String head = null;
        head = pollQueue(qClientToServer, TIMEOUT, TimeUnit.MILLISECONDS);

        logger.log(Level.INFO, head);

        if (head.compareTo(SocketProtocol.CLIENT_INITIAL_READY) == 0) {
            logger.log(Level.INFO, "Putting Server initial ok");
            offerToQueue(qServerToClient, SocketProtocol.SERVER_INITIAL_OK, TIMEOUT, TimeUnit.MILLISECONDS);
        }

        head = pollQueue(qClientToServer, TIMEOUT, TimeUnit.MILLISECONDS);
        String filename = head;
        logger.log(Level.INFO, head);

        offerToQueue(qServerToClient, SocketProtocol.SERVER_FILENAME_OK, TIMEOUT, TimeUnit.MILLISECONDS);

        head = pollQueue(qClientToServer, TIMEOUT, TimeUnit.MILLISECONDS);
        logger.log(Level.INFO, head);
        if (head.compareTo(SocketProtocol.CLIENT_READY_FOR_DATA) == 0) {
            StreamHelper streamHelper = new StreamHelper();
            FileReader fileReader = new FileReaderImpl();
            fileReader.readyFile(filename);
            try {
                String data = fileReader.getMoreData();
                //TODO: How does this work for large data?
                offerToQueue(qServerToClient, data, TIMEOUT, TimeUnit.MILLISECONDS);
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Could not read data from file: " + filename);
                e.printStackTrace();
            }
        }

        head = pollQueue(qClientToServer, TIMEOUT, TimeUnit.MILLISECONDS);
        logger.log(Level.INFO, head);
        if (head.compareTo(SocketProtocol.CLIENT_DONE) == 0) {
            // Disconnect?
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
        queues(qServerToClient, qClientToServer);
    }

    @Override
    public void setOutputStream(PipedOutputStream outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    public void setInputStream(PipedInputStream inputStream) {
        this.inputStream = inputStream;
    }

    public void setqServerToClient(BlockingQueue<String> qServerToClient) {
        this.qServerToClient = qServerToClient;
    }

    public void setqClientToServer(BlockingQueue<String> qClientToServer) {
        this.qClientToServer = qClientToServer;
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
            //queue.put(data, timeout, timeUnit);
            queue.put(data);
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, "Error publishing to queue: ");
            e.printStackTrace();
        }
    }
}
