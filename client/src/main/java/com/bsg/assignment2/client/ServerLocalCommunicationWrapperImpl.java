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
 * Local Communication Wrapper for the Server implementation.
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
    private String filename;

    /**
     * Start the data exchange protocol between the client and server
     *
     * @param qServerToClient
     * @param qClientToServer
     */
    @Override
    public void initiateQueueDataExchange(BlockingQueue<String> qServerToClient, BlockingQueue<String> qClientToServer) {


        String head = null;
        head = BlockingQueueHelper.pollQueue(qClientToServer, TIMEOUT, TimeUnit.MILLISECONDS);

        logger.log(Level.INFO, head);

        if (head.compareTo(SocketProtocol.CLIENT_INITIAL_READY) == 0) {
            logger.log(Level.INFO, "Putting Server initial ok");
            BlockingQueueHelper.offerToQueue(qServerToClient, SocketProtocol.SERVER_INITIAL_OK, TIMEOUT, TimeUnit.MILLISECONDS);
        }

        head = BlockingQueueHelper.pollQueue(qClientToServer, TIMEOUT, TimeUnit.MILLISECONDS);
        filename = head;
        logger.log(Level.INFO, head);

        BlockingQueueHelper.offerToQueue(qServerToClient, SocketProtocol.SERVER_FILENAME_OK, TIMEOUT, TimeUnit.MILLISECONDS);

        head = BlockingQueueHelper.pollQueue(qClientToServer, TIMEOUT, TimeUnit.MILLISECONDS);
        logger.log(Level.INFO, head);
        if (head.compareTo(SocketProtocol.CLIENT_READY_FOR_DATA) == 0) {
            StreamHelper streamHelper = new StreamHelper();
            FileReader fileReader = new FileReaderImpl();
            fileReader.readyFile(filename);
            try {
                String data = fileReader.getMoreData();
                BlockingQueueHelper.offerToQueue(qServerToClient, data, TIMEOUT, TimeUnit.MILLISECONDS);
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Could not read data from file: " + filename);
                e.printStackTrace();
            }
        }

        head = BlockingQueueHelper.pollQueue(qClientToServer, TIMEOUT, TimeUnit.MILLISECONDS);
        logger.log(Level.INFO, head);
        if (head.compareTo(SocketProtocol.CLIENT_DONE) == 0) {
            // Disconnect?
        }


    }

    /**
     * The local connection was meant to use PipeStreams instead of initiateQueueDataExchange. As I had issues, I resorted to Queues.
     * Unfortunately, this means that the protocol handshake is written twice.
     */
    private void initiate() {
        try {
            logger.log(Level.INFO, "Starting the server Protocol");
            serverProtocol.startProtocol(outputStream, inputStream);

        } catch (UnexpectedProtocolException e) {
            logger.log(Level.SEVERE, "Server received an incorrect protocol from the client.");
            e.printStackTrace();

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

    /**
     * Thread starter
     */
    @Override
    public void run() {
        initiateQueueDataExchange(qServerToClient, qClientToServer);
    }

    @Override
    public void setqServerToClient(BlockingQueue<String> qServerToClient) {
        this.qServerToClient = qServerToClient;
    }

    @Override
    public void setqClientToServer(BlockingQueue<String> qClientToServer) {
        this.qClientToServer = qClientToServer;
    }

    @Override
    public void setFilename(String filename) {
        this.filename = filename;
    }


}
