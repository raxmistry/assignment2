package com.bsg.assignment2.server;

import com.bsg.assignment2.common.FileReader;
import com.bsg.assignment2.common.ServerSocketCommunicationWrapper;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by rmistry on 2014/07/25.
 */
public class ServerSocketCommunicationWrapperImpl implements ServerSocketCommunicationWrapper {
    Logger logger = Logger.getLogger(ServerSocketCommunicationWrapperImpl.class.getName());

    private Integer port;
    private ServerSocket serverSocket;
    private Integer timeout;
    private FileReader fileReader;

    @Override
    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    @Override
    public void initiateSocket() {
        Socket client;
        try {
            serverSocket = new ServerSocket(port.intValue());
            fileReader = new FileReaderImpl();
            fileReader.readyFile("/Users/rmistry/test.data");
            byte b[] = fileReader.getMoreData();
            logger.log(Level.INFO, "Byte array length " + b.length);

            logger.log(Level.INFO, "Server socket address: " + serverSocket.getInetAddress());
            //serverSocket.setSoTimeout(getTimeout());
            client = serverSocket.accept();

//            BufferedOutputStream output = new BufferedOutputStream(client.getOutputStream());
            DataOutputStream outputStream = new DataOutputStream(client.getOutputStream());


            outputStream.write(b);

            outputStream.flush();
            outputStream.close();
            logger.log(Level.INFO, "Number of bytes written: " + outputStream.size());

            int count = 0;
            while (count < 100000) {
                count++;
            }

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Could not open a serverSocket on port: " + port);
            e.printStackTrace();
        }
    }

    public void closeSocket() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error closing serverSocket on port: " + port);
            e.printStackTrace();
        }
    }

    @Override
    public void setFileReader(FileReader fileReader) {
        this.setFileReader(fileReader);
    }
}
