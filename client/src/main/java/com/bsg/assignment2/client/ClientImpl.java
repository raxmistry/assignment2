package com.bsg.assignment2.client;

import com.bsg.assignment2.common.CommunicationWrapper;
import com.bsg.assignment2.common.LocalCommunicationWrapper;
import com.bsg.assignment2.common.SocketCommunicationWrapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.logging.Logger;

/**
 * Created by rmistry on 2014/07/25.
 */
public class ClientImpl implements Client {
    private Logger logger = Logger.getLogger(ClientImpl.class.getName());
    private CommunicationWrapper communicationWrapper;

    public static void main(String args[]) {

        if (args.length < 1 || args.length > 1) {
            System.out.println("Invalid usage. Please send exactly one command-line parameter");
            System.out.println("Usage: java -jar ClientImpl <server>");
            System.out.println("Where <server> can be 1) local or 2) hostname:port");
            System.exit(0);
        }


        String parm = args[0];
        String clientBeanId = null;
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "spring-beans.xml");

        if (parm.compareToIgnoreCase("local") == 0) {
            clientBeanId = "clientLocal";
            LocalCommunicationWrapper clientLocal = (ClientLocalCommunicationWrapperImpl) context.getBean(clientBeanId);
        } else {
            clientBeanId = "clientSocket";
            SocketCommunicationWrapper clientSocket = (ClientSocketCommunicationWrapperImpl) context.getBean(clientBeanId);
        }
    }


    @Override
    public void start() {

    }
}
