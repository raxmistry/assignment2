package com.bsg.assignment2.server;

import com.bsg.assignment2.common.FileReader;
import com.bsg.assignment2.common.FileReaderImpl;
import com.bsg.assignment2.common.LocalCommunicationWrapper;
import com.bsg.assignment2.common.ServerProtocol;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Logger;

/**
 * Created by rmistry on 2014/07/26.
 */
public class ServerLocalCommunicationWrapperImpl implements LocalCommunicationWrapper, Runnable {

    ServerProtocol serverProtocol = new ServerProtocol();
    private FileReader fileReader;
    private Logger logger = Logger.getLogger(ServerLocalCommunicationWrapperImpl.class.getName());
    private OutputStream outputStream;
    private InputStream inputStream;

    public void initiate() {

        fileReader = new FileReaderImpl();
        String filename = "/Users/rmistry/test.data";


        String data = "SomeTestData";

        try {
            serverProtocol.serverProtocol(outputStream, inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        initiate();
    }

    @Override
    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }
}
