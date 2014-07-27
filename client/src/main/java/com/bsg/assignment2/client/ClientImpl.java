package com.bsg.assignment2.client;

import com.bsg.assignment2.common.CommunicationWrapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main Client executable for starting the client. Single command-line parameter is allowed, indicating
 * the remote host and port or "local" for a local server connection. Client runs as a SpringApplication.
 * Created by rmistry on 2014/07/25.
 */
public class ClientImpl implements Client {
    private static Logger logger = Logger.getLogger(ClientImpl.class.getName());
    private CommunicationWrapper communicationWrapper;

    public static void main(String args[]) {

        //Check if the args provided are correct
        if (args.length < 1 || args.length > 1) {
            System.out.println("Invalid usage. Please send exactly one command-line parameter");
            System.out.println("Usage: java -jar ClientImpl <server>");
            System.out.println("Where <server> can be 1) local or 2) hostname:port");
            System.exit(0);
        }

        // Get the user to enter the filename to read
        System.out.println("Please enter a fully qualified filename to fetch: ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String filename = null;
        try {
            filename = br.readLine();
        } catch (IOException e) {
            logger.log(Level.WARNING, "Unable to read input from console");
            e.printStackTrace();
        }

        if (filename == null) {
            logger.log(Level.SEVERE, "A filename must be specified");
            System.exit(0);
        }


        String parm = args[0];
        String clientBeanId = null;

        ApplicationContext context = new ClassPathXmlApplicationContext(
                "spring-beans.xml");

        if (parm.compareToIgnoreCase("local") == 0) {

            // If a local connection, load up the initiateQueueDataExchange for communication as well as the beans

            LinkedBlockingQueue<String> qServerToClient = new LinkedBlockingQueue<String>();
            LinkedBlockingQueue<String> qClientToServer = new LinkedBlockingQueue<String>();

            clientBeanId = "clientLocal";
            LocalCommunicationWrapper clientLocal = (ClientLocalCommunicationWrapperImpl) context.getBean(clientBeanId);
            clientLocal.setFilename(filename);

            clientLocal.setqClientToServer(qClientToServer);
            clientLocal.setqServerToClient(qServerToClient);

            String serverLocalBeanId = "serverLocal";
            LocalCommunicationWrapper serverLocal = (ServerLocalCommunicationWrapperImpl) context.getBean(serverLocalBeanId);

            serverLocal.setqClientToServer(qClientToServer);
            serverLocal.setqServerToClient(qServerToClient);

            //Star the client and server as separate threads
            new Thread(clientLocal).start();
            new Thread(serverLocal).start();
        } else {
            // If not a local connection, assume its socket based
            clientBeanId = "clientSocket";
            ClientSocketCommunicationWrapper clientSocket = (ClientSocketCommunicationWrapperImpl) context.getBean(clientBeanId);

            // The hostname and port must be supplied in the format hostname:port
            String[] endPoint = parm.split(":");
            if (endPoint.length != 2) {
                logger.log(Level.SEVERE, "Invalid hostname and port supplied: " + parm);
                logger.log(Level.SEVERE, "parameter must take the form 'hostname:port''");
                System.exit(0);
            } else {
                String hostname = endPoint[0];
                String port = endPoint[1];

                // Initiate the Client connection.
                clientSocket.setHostname(hostname);
                clientSocket.setPort(new Integer(port));
                clientSocket.setFilename(filename);
                try {
                    clientSocket.initiateSocket();
                } catch (ConnectException exception) {
                    logger.log(Level.SEVERE, "There was an error connecting to the remote server. Please ensure the server is " +
                            "up and running. Also check that the hostname and port are correct");
                    exception.printStackTrace();
                }
            }
        }
    }
}
