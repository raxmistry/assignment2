package com.bsg.assignment2.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by rmistry on 2014/07/25.
 */
public class ClientSocketCommunicationWrapperImpl implements ClientSocketCommunicationWrapper {
    Socket clientSocket;
    private Integer port;
    private String hostname;
    private Logger logger = Logger.getLogger(ClientSocketCommunicationWrapperImpl.class.getName());

    @Override
    public void setPort(Integer port) {
        this.port = port;
    }

    @Override
    public void initiateSocket() {

        InetSocketAddress socketAddress = createConnectionAddress();
        if (socketAddress != null) {
            clientSocket = new Socket();
            try {
                clientSocket.connect(socketAddress);
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Client could not connect to server at " + hostname + ":" + port);
                e.printStackTrace();
            }
        } else {
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

    private InetSocketAddress createConnectionAddress() {
        if (hostname == null || port == null) {
            logger.log(Level.SEVERE, "Cannot initiate connection without a valid hostname/ipaddress and port");
            return null;
        }

        InetSocketAddress socketAddress = new InetSocketAddress(hostname, port);

        return socketAddress;
    }
}
