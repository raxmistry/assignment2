package com.bsg.assignment2.common;

import java.io.IOException;

/**
 * Created by rmistry on 2014/07/25.
 */
public interface FileReader {
    void readyFile(String filename);

    String getMoreData() throws IOException;


}
