package com.bsg.assignment2.common;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StreamHelper {
    private Logger logger = Logger.getLogger(StreamHelper.class.getName());

    public void sendToOutputStream(OutputStream outputStream, String data) throws IOException {
        synchronized (outputStream) {
            if (outputStream instanceof DataOutputStream) {
                ((DataOutputStream) outputStream).writeUTF(data);
            }
            outputStream.flush();
        }
    }

    // Read available data from the input stream
    public byte[] getBytes(InputStream inputStream) throws IOException {

        String readUTF = null;
        synchronized (inputStream) {
            if (inputStream instanceof DataInputStream) {
                readUTF = ((DataInputStream) inputStream).readUTF();
            }
        }

        return readUTF.getBytes();
    }

    public void streamFile(OutputStream outputStream, String filename) throws IOException {
        FileReader fileReader = new FileReaderImpl();
        fileReader.readyFile(filename);
        String data = fileReader.getMoreData();
        logger.log(Level.INFO, "File data: " + data);

        synchronized (outputStream) {
            if (outputStream instanceof DataOutputStream) {
                ((DataOutputStream) outputStream).writeUTF(data);
            }

            outputStream.flush();
        }
    }
}