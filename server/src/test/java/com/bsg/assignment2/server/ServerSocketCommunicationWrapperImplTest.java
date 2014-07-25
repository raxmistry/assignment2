package com.bsg.assignment2.server;

import com.bsg.assignment2.common.SocketCommunicationWrapper;
import org.junit.After;
import org.junit.Test;

public class ServerSocketCommunicationWrapperImplTest {

    SocketCommunicationWrapper socketCommunicationWrapper;

    @Test
    public void testSocketListener() {
        socketCommunicationWrapper = new ServerSocketCommunicationWrapperImpl();
        socketCommunicationWrapper.setPort(9999);
        socketCommunicationWrapper.initiateSocket();

        //TODO: How to test this properly
    }

    @After
    public void tearDown() {
        socketCommunicationWrapper.closeSocket();
    }

}