package com.bsg.assignment2.client;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by rmistry on 2014/07/25.
 */
public class ClientSocketCommunicationWrapperImpl implements ClientSocketCommunicationWrapper {

    private static final Logger logger = Logger.getLogger(ClientSocketCommunicationWrapperImpl.class.getName());
    private final ClientSocketProtocolImpl clientProtocol = new ClientSocketProtocolImpl();
    Socket clientSocket;
    private Integer port;
    private String hostname;
    private String filename;
    private OutputWriter outputWriter;

    @Override
    public void setPort(Integer port) {
        this.port = port;
    }

    @Override
    public void initiateSocket() throws ConnectException {

        logger.log(Level.INFO, "Started");
        DataInputStream inputStream = null;
        DataOutputStream outputStream = null;
        byte[] inputBytes;

        InetSocketAddress socketAddress = createConnectionAddress();
        if (socketAddress != null) {
            logger.log(Level.INFO, "Initiating socket");
            clientSocket = new Socket();
            try {
                clientSocket.connect(socketAddress);

                if (clientSocket.isConnected()) {
                    logger.log(Level.INFO, "Connected to server");
                }

                inputStream = new DataInputStream(clientSocket.getInputStream());
                outputStream = new DataOutputStream(clientSocket.getOutputStream());

                clientProtocol.setFilename(filename);
                clientProtocol.setOutputWriter(outputWriter);
                clientProtocol.startProtocol(inputStream, outputStream);


            } catch (ConnectException e) {
                throw e;
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Client could not connect to server at " + hostname + ":" + port);
                e.printStackTrace();
            } finally {
                try {
                    outputStream.flush();
                    outputStream.close();
                    inputStream.close();
                    clientSocket.close();
                } catch (Exception e) {
                    // If there's any exception closing the streams and connection just catch it.
                    e.printStackTrace();
                }
            }
        } else {
            logger.log(Level.SEVERE, "Could not resolve the connection to the server and port");
            return;
        }

    }

    @Override
    public void closeSocket() {
        try {
            clientSocket.close();
        } catch (IOException e) {
            logger.log(Level.WARNING, "Error closing client connection");
            e.printStackTrace();
        }
    }

    @Override
    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    @Override
    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Override
    @Autowired
    public void setOutputWriter(OutputWriter outputWriter) {
        this.outputWriter = outputWriter;
    }

    private InetSocketAddress createConnectionAddress() {
        if (hostname == null || port == null) {
            logger.log(Level.SEVERE, "Cannot initiate connection without a valid hostname/ipaddress and port");
            return null;
        }

        InetSocketAddress socketAddress = new InetSocketAddress(hostname, port);

        return socketAddress;
    }
}
