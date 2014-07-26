package com.bsg.assignment2.common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

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

    //TODO: Change this os I can stream a binary file
    @Override
    public String getMoreData() throws IOException {
        List<String> stringList = Files.readAllLines(path);
        return String.valueOf(stringList);
    }
}
