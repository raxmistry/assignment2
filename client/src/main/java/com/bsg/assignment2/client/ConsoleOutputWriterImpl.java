package com.bsg.assignment2.client;

import org.springframework.stereotype.Component;

/**
 * A simple class to write the given data to System.out
 * Created by rmistry on 2014/07/27.
 */
@Component
public class ConsoleOutputWriterImpl implements OutputWriter {
    @Override
    public void writeData(String data) {
        System.out.println(data);
    }

    @Override
    public void writeData(byte[] data) {
        System.out.println(new String(data));
    }
}
