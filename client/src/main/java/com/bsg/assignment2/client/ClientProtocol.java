package com.bsg.assignment2.client;

/**
 * Interface for ClientProtocol
 * Created by rmistry on 2014/07/27.
 */
public interface ClientProtocol {
    /**
     * Allows a custom Output Writer to be injected into the ClientProtocol
     *
     * @param outputWriter
     */
    public void setOutputWriter(OutputWriter outputWriter);
}
