package com.bsg.assignment2.common;

/**
 * Utility class to standardise protocol between the server and client when using socket communiction
 * Created by rmistry on 2014/07/26.
 */
public class SocketProtocol {
    public static final String CLIENT_INITIAL_READY = "[CLIENT_INITIAL_READY]";
    public static final String SERVER_INITIAL_OK = "[SERVER_INITIAL_OK]";
    public static final String SERVER_FILENAME_OK = "[SERVER_FILENAME_OK]";
    public static final String CLIENT_READY_FOR_DATA = "[CLIENT_READY_FOR_DATA]";
    public static final String CLIENT_DONE = "[CLIENT_DONE]";
}
