package com.bsg.assignment2.server;

import com.bsg.assignment2.common.FileReader;
import com.bsg.assignment2.common.ServerSocketCommunicationWrapper;
import com.bsg.assignment2.common.SocketProtocol;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by rmistry on 2014/07/25.
 */
public class ServerSocketCommunicationWrapperImpl implements ServerSocketCommunicationWrapper {

    Logger logger = Logger.getLogger(ServerSocketCommunicationWrapperImpl.class.getName());

    private Integer port;
    private ServerSocket serverSocket;
    private Integer timeout = 1000000;
    private FileReader fileReader;

    @Override
    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    @Override
    public void initiateSocket() {
        Socket client;
        DataOutputStream outputStream = null;
        DataInputStream inputStream = null;
        byte[] inputBytes;

        try {
            serverSocket = new ServerSocket(port);

            logger.log(Level.INFO, "Server socket address: " + serverSocket.getInetAddress());
            serverSocket.setSoTimeout(getTimeout());
            client = serverSocket.accept();

            outputStream = new DataOutputStream(client.getOutputStream());
            inputStream = new DataInputStream(client.getInputStream());


            if (client.isConnected()) {
                logger.log(Level.INFO, "Client is connected");
                logger.log(Level.INFO, "Client receive buffer size : " + client.getReceiveBufferSize());
                logger.log(Level.INFO, "Client send buffer size : " + client.getSendBufferSize());
            }

//            logger.log(Level.FINE, "Waiting for client to send data");
//            while (inputStream.available() == 0 ) {
//
//            }
//            logger.log(Level.FINE, "Client has finally sent some data");

            // Check for InitialReady
            inputBytes = getBytes(inputStream);
            String initialReady = new String(inputBytes);
            logger.log(Level.INFO, "intialReady = " + initialReady);
            if (initialReady.compareTo(SocketProtocol.CLIENT_INITIAL_READY) == 0) {
                // Reply okInitial
                logger.log(Level.INFO, "Received " + SocketProtocol.CLIENT_INITIAL_READY);
                sendToOutputStream(outputStream, SocketProtocol.SERVER_INITIAL_OK);
            }

            // Check for Filename
            inputBytes = getBytes(inputStream);
            String filename = new String(inputBytes);
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
                // Close connection
                closeSocket();
            }


        } catch (IOException e) {
            logger.log(Level.SEVERE, "Could not open a serverSocket on port: " + port);
            e.printStackTrace();
        } finally {
            // Close the input and output streams and socket
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            closeSocket();
        }
    }

    private void sendToOutputStream(DataOutputStream outputStream, String data) throws IOException {
        outputStream.writeBytes(data);
        outputStream.flush();
    }

    // Read available data from the input stream
    private byte[] getBytes(DataInputStream inputStream) throws IOException {
        int read;
        int available;
        byte[] inputBytes;
        read = 0;
        available = inputStream.available();
        inputBytes = new byte[available];

        while (available > 0) {
            read = inputStream.read(inputBytes);
        }

        logger.log(Level.INFO, "Number of bytes read from input stream: " + read);
        return inputBytes;
    }

    private void streamFile(DataOutputStream outputStream, String filename) throws IOException {
        fileReader = new FileReaderImpl();
        fileReader.readyFile(filename);
        byte b[] = fileReader.getMoreData();
//        logger.log(Level.INFO, "Byte array length " + b.length);
        outputStream.write(b);
        outputStream.flush();

        logger.log(Level.INFO, "Number of bytes written: " + outputStream.size());
    }

    @Override
    public void closeSocket() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error closing serverSocket on port: " + port);
            e.printStackTrace();
        }
    }

    @Override
    public void setFileReader(FileReader fileReader) {
        this.setFileReader(fileReader);
    }
}
