package com.bsg.assignment2.server;

import com.bsg.assignment2.common.SocketCommunicationWrapper;

import java.net.ConnectException;

public class ServerSocketCommunicationWrapperImplTest {


    public static void main(String args[]) {
        SocketCommunicationWrapper socketCommunicationWrapper;
        socketCommunicationWrapper = new ServerSocketCommunicationWrapperImpl();
        socketCommunicationWrapper.setPort(9999);


        try {
            socketCommunicationWrapper.initiateSocket();
        } catch (ConnectException e) {
            e.printStackTrace();
        }
    }

    public void testSocketListener() {
    }

}