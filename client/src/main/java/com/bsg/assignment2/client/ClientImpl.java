package com.bsg.assignment2.client;

import com.bsg.assignment2.common.CommunicationWrapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by rmistry on 2014/07/25.
 */
public class ClientImpl implements Client {
    private static Logger logger = Logger.getLogger(ClientImpl.class.getName());
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


            LinkedBlockingQueue<String> qServerToClient = new LinkedBlockingQueue<String>();
            LinkedBlockingQueue<String> qClientToServer = new LinkedBlockingQueue<String>();

            clientBeanId = "clientLocal";
            LocalCommunicationWrapper clientLocal = (ClientLocalCommunicationWrapperImpl) context.getBean(clientBeanId);

            clientLocal.setqClientToServer(qClientToServer);
            clientLocal.setqServerToClient(qServerToClient);

            //clientLocal.setFilename("/Users/rmistry/test.data");

            String serverLocalBeanId = "serverLocal";
            LocalCommunicationWrapper serverLocal = (ServerLocalCommunicationWrapperImpl) context.getBean(serverLocalBeanId);

            serverLocal.setqClientToServer(qClientToServer);
            serverLocal.setqServerToClient(qServerToClient);

            new Thread(clientLocal).start();
            new Thread(serverLocal).start();
        } else {
            clientBeanId = "clientSocket";
            ClientSocketCommunicationWrapper clientSocket = (ClientSocketCommunicationWrapperImpl) context.getBean(clientBeanId);

            String[] endPoint = parm.split(":");
            if (endPoint.length != 2) {
                logger.log(Level.SEVERE, "Invalid hostname and port supplied: " + parm);
                System.exit(0);
            } else {
                String hostname = endPoint[0];
                String port = endPoint[1];

                clientSocket.setHostname(hostname);
                clientSocket.setPort(new Integer(port));

                //clientSocket.setFilename("/Users/rmistry/test.data");
                clientSocket.initiateSocket();
            }
        }
    }


    @Override
    public void start() {

    }
}
