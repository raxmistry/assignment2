package com.bsg.assignment2.client;

import com.bsg.assignment2.common.SocketProtocol;
import com.bsg.assignment2.common.StreamHelper;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Logger;

public class ClientProtocol {

    StreamHelper streamHelper = new StreamHelper();
    private Logger logger = Logger.getLogger(ClientProtocol.class.getName());
    private String filename;

    public ClientProtocol() {
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    void startProtocol(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] inputBytes;
        streamHelper.sendToOutputStream(outputStream, SocketProtocol.CLIENT_INITIAL_READY);

        inputBytes = streamHelper.getBytes(inputStream);
        String serverInitialOk = new String(inputBytes);
        if (serverInitialOk.compareTo(SocketProtocol.SERVER_INITIAL_OK) == 0) {
            //Send the filename we want to stream back
            streamHelper.sendToOutputStream(outputStream, getFilename());
        }

        inputBytes = streamHelper.getBytes(inputStream);
        String serverFilenameOk = new String(inputBytes);

        if (serverFilenameOk.compareTo(SocketProtocol.SERVER_FILENAME_OK) == 0) {
            streamHelper.sendToOutputStream(outputStream, SocketProtocol.CLIENT_READY_FOR_DATA);
        }

        // Get the data from the filename
        String data = null;
        if (inputStream instanceof DataInputStream) {
            data = ((DataInputStream) inputStream).readUTF();
        }
        System.out.println(data);


        // Let the server know that we're done
        streamHelper.sendToOutputStream(outputStream, SocketProtocol.CLIENT_DONE);
    }
}