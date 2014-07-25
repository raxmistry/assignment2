package com.bsg.assignment2.client;

import org.junit.Test;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by rmistry on 2014/07/25.
 */
public class ClientTest {

    Logger logger = Logger.getLogger(ClientTest.class.getName());
    @Test
    public void testClientConnects() {
        Socket socket = new Socket();
        SocketAddress socketAddress = new InetSocketAddress("localhost", 9999);
        try {
            socket.connect(socketAddress);

            if (socket.isConnected()) {
                logger.log(Level.INFO, "Connect to server");
            }

            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            byte[] b = new byte[0];

            logger.log(Level.INFO, "About to read");

            int read = inputStream.read(b);
            logger.log(Level.INFO, "Read = " + read);
            if (b != null) {
                System.out.println(b.length);
            }

            logger.log(Level.INFO, "Finish read");

            inputStream.close();


        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                logger.log(Level.INFO, "Closing client connection");
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
