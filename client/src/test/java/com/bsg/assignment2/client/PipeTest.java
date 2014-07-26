package com.bsg.assignment2.client;

import org.junit.Test;

import java.io.*;

/**
 * Created by rmistry on 2014/07/26.
 */
public class PipeTest {

    @Test
    public void testSimplePipe() {

        PipedInputStream inputStream = new PipedInputStream();
        PipedOutputStream outputStream = new PipedOutputStream();

        try {
            inputStream.connect(outputStream);

            DataSource dataSource = new DataSource(outputStream);
            DataDestination dataDestination = new DataDestination(inputStream);

            Thread sourceThread = new Thread(dataSource);
            Thread destinationThread = new Thread(dataDestination);

            sourceThread.start();
            destinationThread.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


class DataSource implements Runnable {

    private final OutputStream outputStream;

    DataSource(OutputStream outputStream) {
        this.outputStream = outputStream;
    }


    @Override
    public void run() {
        String text = "SomeTextualData";
        try {
            outputStream.write(text.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class DataDestination implements Runnable {
    private final InputStream inputStream;

    DataDestination(InputStream inputStream) {
        this.inputStream = inputStream;
    }


    @Override
    public void run() {
        int available = 0;
        try {
            available = inputStream.available();
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] data = new byte[available];
        try {
            inputStream.read(data);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Destination: " + new String(data));
    }
}