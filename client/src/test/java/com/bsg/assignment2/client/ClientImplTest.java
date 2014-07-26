package com.bsg.assignment2.client;

import org.junit.Test;

public class ClientImplTest {

    @Test
    public void testStart() {
        Client client = new ClientImpl();
        client.start();
    }

}