package com.bsg.assignment2.server;

import com.bsg.assignment2.common.FileReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by rmistry on 2014/07/25.
 */
public class FileReaderImpl implements FileReader {
    private Path path;

    @Override
    public void readyFile(String filename) {
        if (filename != null) {
            path = Paths.get(filename);
        }
    }

    @Override
    public byte[] getMoreData() throws IOException {
        return Files.readAllBytes(path);
    }
}
