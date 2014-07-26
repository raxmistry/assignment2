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
            } else {
                logger.log(Level.INFO, "PipedOutputStream");
                outputStream.write(data.getBytes());
            }

            outputStream.flush();
        }
    }

    // Read available data from the input stream
    public byte[] getBytes(InputStream inputStream) throws IOException {

        String readUTF = null;
        synchronized (inputStream) {
            //TODO: Get bytes working properly to make this generic
            if (inputStream instanceof DataInputStream) {
                readUTF = ((DataInputStream) inputStream).readUTF();
            }

            if (inputStream instanceof PipedInputStream) {
                logger.log(Level.INFO, "PipedInputStream");
                int available = inputStream.available();

                logger.log(Level.INFO, "Available: " + available);
                byte[] bytes = new byte[available];

                logger.log(Level.INFO, "Reading bytes from stream");
                inputStream.read(bytes);
                readUTF = new String(bytes);
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
            } else {
                logger.log(Level.INFO, "PipedOutputStream");
            }

            outputStream.flush();
        }
    }
}