package com.bsg.assignment2.server;

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
