package com.bsg.assignment2.common;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * Created by rmistry on 2014/07/25.
 */
public interface LocalCommunicationWrapper extends CommunicationWrapper, Runnable {
    public void setOutputStream(PipedOutputStream outputStream);

    public void setInputStream(PipedInputStream inputStream);
}
