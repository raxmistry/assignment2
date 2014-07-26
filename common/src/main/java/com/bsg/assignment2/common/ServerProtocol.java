package com.bsg.assignment2.common;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerProtocol {
    //    private final ServerSocketCommunicationWrapperImpl serverSocketCommunicationWrapperImpl;
    Logger logger = Logger.getLogger(ServerProtocol.class.getName());
    private String filename;
    private FileReaderImpl fileReader;


    public void serverProtocol(OutputStream outputStream, InputStream inputStream) throws IOException {
        byte[] inputBytes;// Check for InitialReady
        inputBytes = getBytes(inputStream);
        String initialReady = new String(inputBytes);
        logger.log(Level.INFO, "initialReady = " + initialReady);
        if (initialReady.compareTo(SocketProtocol.CLIENT_INITIAL_READY) == 0) {
            // Reply okInitial
            logger.log(Level.INFO, "Received " + SocketProtocol.CLIENT_INITIAL_READY);
            sendToOutputStream(outputStream, SocketProtocol.SERVER_INITIAL_OK);
        }

        // Check for Filename
        inputBytes = getBytes(inputStream);
        String filename = new String(inputBytes);
        logger.log(Level.INFO, "Filename = " + filename);
        if (filename != null) {
            // Reply okFilename
            logger.log(Level.INFO, "Send " + SocketProtocol.SERVER_FILENAME_OK);
            sendToOutputStream(outputStream, SocketProtocol.SERVER_FILENAME_OK);
        }

        // Get ReadyForData state from client
        inputBytes = getBytes(inputStream);
        String clientReadyForData = new String(inputBytes);
        if (clientReadyForData.compareTo(SocketProtocol.CLIENT_READY_FOR_DATA) == 0) {
            logger.log(Level.INFO, "Received " + SocketProtocol.CLIENT_READY_FOR_DATA);
            // Reply with data
            streamFile(outputStream, filename);
        }


        // Get done state from client
        inputBytes = getBytes(inputStream);
        String clientDone = new String(inputBytes);
        if (clientDone.compareTo(SocketProtocol.CLIENT_DONE) == 0) {
            logger.log(Level.INFO, "Received " + SocketProtocol.CLIENT_DONE);

        }
    }


    // Write to the output stream and flush
    private void sendToOutputStream(OutputStream outputStream, String data) throws IOException {
        if (outputStream instanceof DataOutputStream) {
            ((DataOutputStream) outputStream).writeUTF(data);
        } else {
            //Dome something with the bytes
        }

        outputStream.flush();
    }

    // Read available data from the input stream
    private byte[] getBytes(InputStream inputStream) throws IOException {
        String readUTF = null;
        //TODO: Get bytes working properly to make this generic
        if (inputStream instanceof DataInputStream) {
            readUTF = ((DataInputStream) inputStream).readUTF();
        } else {
            // do something with bytes here
        }

        return readUTF.getBytes();
    }


    private void streamFile(OutputStream outputStream, String filename) throws IOException {
        fileReader = new FileReaderImpl();
        fileReader.readyFile(filename);
        String data = fileReader.getMoreData();
        logger.log(Level.INFO, "File data: " + data);

        if (outputStream instanceof DataOutputStream) {
            ((DataOutputStream) outputStream).writeUTF(data);
        } else {
            // do something with bytes
        }

        outputStream.flush();
    }
}