package com.bsg.assignment2.common;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by rmistry on 2014/07/25.
 */
public interface LocalCommunicationWrapper extends CommunicationWrapper {
    public void setOutputStream(OutputStream outputStream);

    public void setInputStream(InputStream inputStream);
}
