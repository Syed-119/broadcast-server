package org.syed.broadcastserver.view;

import org.syed.broadcastserver.controller.ClientHandlerThread;
import org.syed.broadcastserver.model.ClientChangeListener;
import org.syed.broadcastserver.model.ClientInfo;
import org.syed.broadcastserver.model.ConnectedClients;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements ClientChangeListener{
    private static ServerSocket serverSocket;
    private static final int PORT=5000;
    static ConnectedClients connectedClients = new ConnectedClients();

    public Server() throws IOException {
        connectedClients.addListener(this);
        serverSocket = new ServerSocket(PORT);
        Helper.printInfo("Listening on port " + PORT);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            Helper.printInfo("Incoming Connection: " + clientSocket.getInetAddress());
            ClientHandlerThread.createAndStart(clientSocket.getInetAddress(), connectedClients, clientSocket);

        }

    }

    @Override
    public void onClientAdded(ClientInfo client) {
        Helper.printInfo("Adding client for " + Thread.currentThread().getName() +
                " -> " + client.getUsername());
    }


    @Override
    public void onClientRemoved(ClientInfo client) {
        Helper.printInfo("Removing client for " + Thread.currentThread().getName() +
                " -> " + (client != null ? client.getUsername() : "null"));
    }

    @Override
    public void onClientMessageReceived(ClientInfo client) {
        Helper.printInfo("Message received from: " + client.getUsername());
    }


}
