package com.bsg.assignment2.common;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StreamHelper {
    private Logger logger = Logger.getLogger(StreamHelper.class.getName());

    public void sendToOutputStream(OutputStream outputStream, String data) throws IOException {
        if (outputStream instanceof DataOutputStream) {
            ((DataOutputStream) outputStream).writeUTF(data);
        } else {
            //Do something with the bytes
        }

        outputStream.flush();
    }

    // Read available data from the input stream
    public byte[] getBytes(InputStream inputStream) throws IOException {
        String readUTF = null;
        //TODO: Get bytes working properly to make this generic
        if (inputStream instanceof DataInputStream) {
            readUTF = ((DataInputStream) inputStream).readUTF();
        } else {
            // do something with bytes here
        }

        return readUTF.getBytes();
    }

    public void streamFile(OutputStream outputStream, String filename) throws IOException {
        FileReader fileReader = new FileReaderImpl();
        fileReader.readyFile(filename);
        String data = fileReader.getMoreData();
        logger.log(Level.INFO, "File data: " + data);

        if (outputStream instanceof DataOutputStream) {
            ((DataOutputStream) outputStream).writeUTF(data);
        } else {
            // do something with bytes
        }

        outputStream.flush();
    }
}