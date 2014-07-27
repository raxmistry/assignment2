package com.bsg.assignment2.client;

import org.junit.Test;

import java.net.ConnectException;

public class ClientSocketCommunicationWrapperImplTest {

    @Test
    public void testInitiateSocket() throws Exception {
        //TODO: Use JMock to mock out the server and think about using this on the client too
        ClientSocketCommunicationWrapper clientSocketCommunicationWrapper = new ClientSocketCommunicationWrapperImpl();

        clientSocketCommunicationWrapper.setHostname("Rakeshs-MacBook-Air.local");
        //TODO: Implement binary file handling
        clientSocketCommunicationWrapper.setFilename("/Users/rmistry/test.data");
        clientSocketCommunicationWrapper.setPort(9999);
        clientSocketCommunicationWrapper.setOutputWriter(new ConsoleOutputWriterImpl());

        try {
            clientSocketCommunicationWrapper.initiateSocket();
        } catch (ConnectException e) {
            e.printStackTrace();
        }
    }
}