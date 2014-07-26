package com.bsg.assignment2.client;

import com.bsg.assignment2.common.SocketProtocol;
import org.junit.Test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by rmistry on 2014/07/25.
 */
public class ClientTest {

    public static final int BUFFER_LENGTH = 23;
    Logger logger = Logger.getLogger(ClientTest.class.getName());

    @Test
    public void testClientConnects() {
        Socket socket = new Socket();
        SocketAddress socketAddress = new InetSocketAddress("Rakeshs-MacBook-Air.local", 9999);
        try {
            socket.connect(socketAddress);

            if (socket.isConnected()) {
                logger.log(Level.INFO, "Connect to server");
            }

            InputStream is = socket.getInputStream();

            DataInputStream inputStream = new DataInputStream(is);
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());


            outputStream.writeBytes(SocketProtocol.CLIENT_INITIAL_READY);
            outputStream.flush();

            byte[] b = new byte[BUFFER_LENGTH];

            int read = inputStream.read(b);
            logger.log(Level.INFO, "Read = " + read);
            if (b != null) {
                System.out.println(new String(b));
            }

            logger.log(Level.INFO, "Finish read");


            outputStream.writeBytes("/Users/rmistry/test.data");
            outputStream.flush();

            inputStream.close();
            outputStream.close();


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
