package com.bsg.assignment2.server;

import com.bsg.assignment2.common.CommunicationWrapper;

/**
 * Created by rmistry on 2014/07/25.
 */
public class ServerImpl implements Server {

    CommunicationWrapper communicationWrapper;

    @Override
    public void setCommunicationWrapper(CommunicationWrapper communicationWrapper) {
        this.communicationWrapper = communicationWrapper;
    }
}
