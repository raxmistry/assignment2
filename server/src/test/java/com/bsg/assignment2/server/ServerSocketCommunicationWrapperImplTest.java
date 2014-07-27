package com.bsg.assignment2.server;

import com.bsg.assignment2.common.SocketCommunicationWrapper;
import org.junit.After;
import org.junit.Test;

import java.net.ConnectException;

public class ServerSocketCommunicationWrapperImplTest {

    SocketCommunicationWrapper socketCommunicationWrapper;

    @Test
    public void testSocketListener() {
        socketCommunicationWrapper = new ServerSocketCommunicationWrapperImpl();
        socketCommunicationWrapper.setPort(9999);


        try {
            socketCommunicationWrapper.initiateSocket();
        } catch (ConnectException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() {
        socketCommunicationWrapper.closeSocket();
    }

}