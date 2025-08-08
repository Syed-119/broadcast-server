package org.syed;

import org.syed.broadcastserver.handlers.ClientHandlerThread;
import org.syed.broadcastserver.model.ConnectedClients;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {
    private static ServerSocket serverSocket;
    private static final int PORT=5000;
    static ConnectedClients connectedClients = new ConnectedClients();

    public static void main(String[] args) throws IOException {
        serverSocket = new ServerSocket(PORT);
        System.out.println("Listening on port " + PORT);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected: " + clientSocket.getInetAddress());

            ClientHandlerThread thread = ClientHandlerThread.createAndStart(clientSocket.getInetAddress(), connectedClients, clientSocket);

        }



    }
}