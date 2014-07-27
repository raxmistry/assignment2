package com.bsg.assignment2.client;

import java.net.ConnectException;

public class ClientSocketCommunicationWrapperImplTest {

    public static void main(String args[]) throws Exception {
        ClientSocketCommunicationWrapper clientSocketCommunicationWrapper = new ClientSocketCommunicationWrapperImpl();

        clientSocketCommunicationWrapper.setHostname("localhost");
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