package com.bsg.assignment2.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by rmistry on 2014/07/25.
 */
public class Client{
    public void temp() {
        Socket socket = new Socket();
        try {
            InetSocketAddress socketAddress = new InetSocketAddress("localhost", 9999);
            socket.connect(socketAddress);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
