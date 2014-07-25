package com.bsg.assignment2.client;

import com.bsg.assignment2.common.SocketCommunicationWrapper;

/**
 * Created by rmistry on 2014/07/25.
 */
public interface ClientSocketCommunicationWrapper extends SocketCommunicationWrapper {
    public void setHostname(String hostname);
}
