package com.bsg.assignment2.client;

/**
 * Interface for writing data out. Data assumed to be String or byte array
 * Created by rmistry on 2014/07/27.
 */
public interface OutputWriter {

    public void writeData(String data);

    public void writeData(byte[] data);

}
