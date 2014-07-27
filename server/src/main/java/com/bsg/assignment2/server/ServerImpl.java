package com.bsg.assignment2.server;

import com.bsg.assignment2.common.CommunicationWrapper;
import com.bsg.assignment2.common.ServerSocketCommunicationWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.net.ConnectException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Server side application running as a SpringApplication.
 * Created by rmistry on 2014/07/25.
 */
public class ServerImpl implements Server {

    private static Logger logger = Logger.getLogger(ServerImpl.class.getName());
    CommunicationWrapper communicationWrapper;

    /**
     * No arguments are required. The server utilises the port from the spring-beans.xml config file. Default port 9999.
     *
     * @param args
     */
    public static void main(String args[]) {

        ApplicationContext context = new ClassPathXmlApplicationContext(
                "spring-beans.xml");

        ServerSocketCommunicationWrapper server = (ServerSocketCommunicationWrapperImpl) context.getBean("serverSocket");
        try {
            server.initiateSocket();
        } catch (ConnectException e) {
            logger.log(Level.SEVERE, "There was an error starting the server");
            e.printStackTrace();
            server.closeSocket();
        }
    }

    @Override
    @Autowired
    public void setCommunicationWrapper(CommunicationWrapper communicationWrapper) {
        this.communicationWrapper = communicationWrapper;
    }
}
