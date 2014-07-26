package com.bsg.assignment2.client;

import org.junit.Test;

public class ClientSocketCommunicationWrapperImplTest {

    @Test
    public void testInitiateSocket() throws Exception {
        //TODO: Use JMock to mock out the server and think about using this on the client too

        ClientSocketCommunicationWrapper clientSocketCommunicationWrapper = new ClientSocketCommunicationWrapperImpl();

        clientSocketCommunicationWrapper.setHostname("localhost");
        clientSocketCommunicationWrapper.setPort(9999);
        clientSocketCommunicationWrapper.initiateSocket();

    }
}