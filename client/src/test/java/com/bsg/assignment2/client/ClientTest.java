package com.bsg.assignment2.client;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Created by rmistry on 2014/07/25.
 */
public class ClientTest {

    @Test
    public void testClientConnects() {
        Socket socket = new Socket();
        SocketAddress socketAddress = new InetSocketAddress("localhost", 9999);
        try {
            socket.connect(socketAddress);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            byte[] b = null;

            while (reader.ready()) {
                System.out.println(reader.read());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
