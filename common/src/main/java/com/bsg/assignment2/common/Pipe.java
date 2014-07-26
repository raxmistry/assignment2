package com.bsg.assignment2.common;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * Created by rmistry on 2014/07/26.
 */
public interface Pipe {
    public void setPipedInputStream(PipedInputStream pipedInputStream);

    public void setPipedOutputStream(PipedOutputStream pipedOutputStream);
}
