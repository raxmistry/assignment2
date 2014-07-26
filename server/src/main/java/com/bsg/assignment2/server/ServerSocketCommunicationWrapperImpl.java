package com.bsg.assignment2.server;

import com.bsg.assignment2.common.FileReader;
import com.bsg.assignment2.common.ServerProtocol;
import com.bsg.assignment2.common.ServerSocketCommunicationWrapper;

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

    private final ServerProtocol serverProtocol = new ServerProtocol();
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
            }

            // Start the data exchange protocol
            serverProtocol.serverProtocol(outputStream, inputStream);

            // Close the connection
            closeSocket();

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
