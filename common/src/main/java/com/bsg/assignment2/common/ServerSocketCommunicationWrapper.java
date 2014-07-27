package com.bsg.assignment2.common;

/**
 * Interface for the Server specific communication
 * Created by rmistry on 2014/07/25.
 */
public interface ServerSocketCommunicationWrapper extends SocketCommunicationWrapper {
    /**
     * Set the FileReader we want to use to read the files.
     *
     * @param fileReader
     */
    public void setFileReader(FileReader fileReader);

    /**
     * Set how long the server waits for a connection before timing out.
     *
     * @param timeout
     */
    public void setTimeout(Integer timeout);
}
