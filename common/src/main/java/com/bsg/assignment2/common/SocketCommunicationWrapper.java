package com.bsg.assignment2.common;

/**
 * Created by rmistry on 2014/07/25.
 */
public interface SocketCommunicationWrapper extends CommunicationWrapper {
    public void setPort(Integer port);

    public void initiateSocket();

    public void closeSocket();
}