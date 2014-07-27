package com.bsg.assignment2.common;

import java.net.ConnectException;

/**
 * General socket handling interface
 * Created by rmistry on 2014/07/25.
 */
public interface SocketCommunicationWrapper extends CommunicationWrapper {
    /**
     * Set the port for the socket
     *
     * @param port
     */
    public void setPort(Integer port);

    /**
     * Start the socket connection
     *
     * @throws ConnectException
     */
    public void initiateSocket() throws ConnectException;

    /**
     * Close the socket connection
     */
    public void closeSocket();
}