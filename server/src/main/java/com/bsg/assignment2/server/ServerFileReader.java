package com.bsg.assignment2.server;

import java.io.IOException;

/**
 * Created by rmistry on 2014/07/25.
 */
public interface ServerFileReader {
    void readyFile(String filename);
    byte[] getMoreData() throws IOException;
}
