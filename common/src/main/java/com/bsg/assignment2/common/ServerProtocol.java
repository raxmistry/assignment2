package com.bsg.assignment2.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerProtocol {
    private final StreamHelper streamHelper = new StreamHelper();
    //    private final ServerSocketCommunicationWrapperImpl serverSocketCommunicationWrapperImpl;
    Logger logger = Logger.getLogger(ServerProtocol.class.getName());
    private String filename;
    private FileReaderImpl fileReader;


    public void serverProtocol(OutputStream outputStream, InputStream inputStream) throws IOException {
        byte[] inputBytes;// Check for InitialReady
        inputBytes = streamHelper.getBytes(inputStream);
        String initialReady = new String(inputBytes);
        logger.log(Level.INFO, "initialReady = " + initialReady);
        if (initialReady.compareTo(SocketProtocol.CLIENT_INITIAL_READY) == 0) {
            // Reply okInitial
            logger.log(Level.INFO, "Received " + SocketProtocol.CLIENT_INITIAL_READY);
            streamHelper.sendToOutputStream(outputStream, SocketProtocol.SERVER_INITIAL_OK);
        }

        // Check for Filename
        inputBytes = streamHelper.getBytes(inputStream);
        String filename = new String(inputBytes);
        logger.log(Level.INFO, "Filename = " + filename);
        if (filename != null) {
            // Reply okFilename
            logger.log(Level.INFO, "Send " + SocketProtocol.SERVER_FILENAME_OK);
            streamHelper.sendToOutputStream(outputStream, SocketProtocol.SERVER_FILENAME_OK);
        }

        // Get ReadyForData state from client
        inputBytes = streamHelper.getBytes(inputStream);
        String clientReadyForData = new String(inputBytes);
        if (clientReadyForData.compareTo(SocketProtocol.CLIENT_READY_FOR_DATA) == 0) {
            logger.log(Level.INFO, "Received " + SocketProtocol.CLIENT_READY_FOR_DATA);
            // Reply with data
            streamHelper.streamFile(outputStream, filename);
        }


        // Get done state from client
        inputBytes = streamHelper.getBytes(inputStream);
        String clientDone = new String(inputBytes);
        if (clientDone.compareTo(SocketProtocol.CLIENT_DONE) == 0) {
            logger.log(Level.INFO, "Received " + SocketProtocol.CLIENT_DONE);

        }
    }


//    // Write to the output stream and flush
//    private void sendToOutputStream(OutputStream outputStream, String data) throws IOException {
//
//        streamHelper.sendToOutputStream(outputStream, data);
//    }
//
//    // Read available data from the input stream
//    private byte[] getBytes(InputStream inputStream) throws IOException {
//        //TODO: Get bytes working properly to make this generic
//
//        return streamHelper.getBytes(inputStream);
//    }
//
//
//    private void streamFile(OutputStream outputStream, String filename) throws IOException {
//
//        streamHelper.streamFile(outputStream, filename);
//    }
}