package com.bsg.assignment2.common;

import org.junit.Assert;
import org.junit.Test;

public class FileReaderImplTest {

    public static final int FILE_LENGTH = 4209;
    public static final String FILENAME = "/Users/rmistry/test.data";

    @Test
    public void testGetMoreData() throws Exception {

        FileReader fileReader = new FileReaderImpl();
        fileReader.readyFile(FILENAME);
        String data = fileReader.getMoreData();

        Assert.assertNotNull(data);
        Assert.assertEquals(FILE_LENGTH, data.length());
    }
}