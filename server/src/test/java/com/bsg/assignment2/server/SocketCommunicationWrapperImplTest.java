package com.bsg.assignment2.server;

import org.junit.Assert;
import org.junit.After;
import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import static org.junit.Assert.*;

public class SocketCommunicationWrapperImplTest {

    SocketCommunicationWrapper socketCommunicationWrapper;

    @Test
    public void testSocketListener() {
        socketCommunicationWrapper = new SocketCommunicationWrapperImpl();
        socketCommunicationWrapper.setPort(9999);
        socketCommunicationWrapper.listen();

        //TODO: How to test this properly
    }

    @After
    public void tearDown() {
        socketCommunicationWrapper.closeSocket();
    }

}