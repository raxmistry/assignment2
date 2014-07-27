package com.bsg.assignment2.client;

import com.bsg.assignment2.common.SocketCommunicationWrapper;

/**
 * Interface for the client specific socket connection
 * Created by rmistry on 2014/07/25.
 */
public interface ClientSocketCommunicationWrapper extends SocketCommunicationWrapper {
    /**
     * Set the hostname that the client should connet to
     *
     * @param hostname
     */
    public void setHostname(String hostname);

    /**
     * Set the filename that the client must request
     *
     * @param filename
     */
    public void setFilename(String filename);

    /**
     * Set the OutputWriter that the ClientProtocol will write the data out with
     */
    public void setOutputWriter(OutputWriter outputWriter);
}
