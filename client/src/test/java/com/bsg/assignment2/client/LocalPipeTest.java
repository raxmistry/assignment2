package com.bsg.assignment2.client;

import org.junit.Test;

import java.util.concurrent.SynchronousQueue;
import java.util.logging.Logger;

/**
 * Created by rmistry on 2014/07/26.
 */
public class LocalPipeTest {
    private Logger logger = Logger.getLogger(LocalPipeTest.class.getName());

    @Test
    public void testPipe() {

        SynchronousQueue<String> queue = new SynchronousQueue<String>();

        ServerLocalCommunicationWrapperImpl serverWrapper = new ServerLocalCommunicationWrapperImpl();
        serverWrapper.setQueue(queue);

        ClientLocalCommunicationWrapperImpl clientWrapper = new ClientLocalCommunicationWrapperImpl();
        clientWrapper.setQueue(queue);

        new Thread(serverWrapper).start();
        new Thread(clientWrapper).start();


//        PipedInputStream clientInputStream = new PipedInputStream();
//        PipedOutputStream clientOutputStream = new PipedOutputStream();
//        PipedInputStream serverInputStream = new PipedInputStream();
//        PipedOutputStream serverOutputStream = new PipedOutputStream();
//
//        // Connect the client Input Stream to the server OutputStream
//        try {
//            clientInputStream.connect(serverOutputStream);
//            serverInputStream.connect(clientOutputStream);
//        } catch (IOException e) {
//            logger.log(Level.SEVERE, "Error connecting pipe between client and server.");
//            e.printStackTrace();
//            System.exit(0);
//        }


//        LocalCommunicationWrapper serverWrapper = new ServerLocalCommunicationWrapperImpl();
//        serverWrapper.setInputStream(serverInputStream);
//        serverWrapper.setOutputStream(serverOutputStream);
//
//
//        LocalCommunicationWrapper clientWrapper = new ClientLocalCommunicationWrapperImpl();
//        clientWrapper.setInputStream(clientInputStream);
//        clientWrapper.setOutputStream(clientOutputStream);


    }
}
