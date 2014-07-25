package com.bsg.assignment2.server;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by rmistry on 2014/07/25.
 */
public class SocketCommunicationWrapperImpl implements SocketCommunicationWrapper {
    Logger logger = Logger.getLogger(SocketCommunicationWrapperImpl.class.getName());

    private Integer port;
    private CommunicationWrapper communicationWrapper;
    private ServerSocket serverSocket;
    private Integer timeout;
    private ServerFileReader serverFileReader;

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
    public void listen() {
        Socket client;
        try {
            serverSocket = new ServerSocket(port.intValue());
            //serverSocket.setSoTimeout(getTimeout());
            client = serverSocket.accept();

            BufferedOutputStream output = new BufferedOutputStream(client.getOutputStream());
            serverFileReader = new ServerFileReaderImpl();
            serverFileReader.readyFile("/Users/rmistry/test.data");
            byte b[]= serverFileReader.getMoreData();
            logger.log(Level.INFO, "Byte array " + b);
            output.write(b);

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
    public void setServerFileReader(ServerFileReader serverFileReader) {
        this.setServerFileReader(serverFileReader);
    }
}
