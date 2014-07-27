package com.bsg.assignment2.server;

import com.bsg.assignment2.common.FileReader;
import com.bsg.assignment2.common.ServerProtocol;
import com.bsg.assignment2.common.ServerSocketCommunicationWrapper;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Server socket communication wrapper initiates the socket listener for the server.
 * Created by rmistry on 2014/07/25.
 */
public class ServerSocketCommunicationWrapperImpl implements ServerSocketCommunicationWrapper {

    private final ServerProtocol serverProtocol = new ServerProtocol();
    Logger logger = Logger.getLogger(ServerSocketCommunicationWrapperImpl.class.getName());

    private Integer port;
    private ServerSocket serverSocket;
    //Default timeout if we don't receive a connection
    private Integer timeout = 100000;
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
    public void initiateSocket() throws ConnectException {
        Socket client;
        DataOutputStream outputStream = null;
        DataInputStream inputStream = null;
        byte[] inputBytes;

        try {
            // Create the socket
            serverSocket = new ServerSocket(port);

            logger.log(Level.INFO, "Server socket address: " + serverSocket.getInetAddress());
            serverSocket.setSoTimeout(getTimeout());


            boolean state = true;
            // The server will run indefinitely until explicitly killed.
            while (state) {
                //Wait for client connections for the defined timeout period.
                client = serverSocket.accept();

                // Get the streams from the client connection
                outputStream = new DataOutputStream(client.getOutputStream());
                inputStream = new DataInputStream(client.getInputStream());

                if (client.isConnected()) {
                    logger.log(Level.INFO, "Client is connected");
                }
                // Start the data exchange protocol
                serverProtocol.startProtocol(outputStream, inputStream);
            }

            // Close the connection
            closeSocket();

        } catch (ConnectException e) {
            throw e;
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
