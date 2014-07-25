package com.bsg.assignment2.server;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ServerFileReaderImplTest {

    public static final int FILE_LENGTH = 23;
    public static final String FILENAME = "/Users/rmistry/test.data";

    @Test
    public void testGetMoreData() throws Exception {

        ServerFileReader serverFileReader = new ServerFileReaderImpl();
        serverFileReader.readyFile(FILENAME);
        byte[] data = serverFileReader.getMoreData();

        Assert.assertNotNull(data);
        Assert.assertEquals(FILE_LENGTH, data.length);
    }
}