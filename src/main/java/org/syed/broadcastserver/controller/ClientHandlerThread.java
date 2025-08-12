package org.syed.broadcastserver.controller;

import org.syed.broadcastserver.model.ClientInfo;
import org.syed.broadcastserver.model.ConnectedClients;
import org.syed.broadcastserver.view.Helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;


public class ClientHandlerThread implements Runnable {

    private Thread thread;
    ClientsController clientsController;
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;


    ClientHandlerThread(InetAddress address, ConnectedClients clients, Socket socket) throws IOException {
        this.thread = new Thread(this, address.toString());
        this.clientsController = new ClientsController(clients);
        this.clientSocket = socket;
        this.out = new PrintWriter(clientSocket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public static ClientHandlerThread createAndStart(InetAddress address, ConnectedClients clients, Socket socket) throws IOException {
        ClientHandlerThread clientHandlerThread = new ClientHandlerThread(address, clients, socket);
        clientHandlerThread.thread.start();
        return clientHandlerThread;

    }

    @Override
    public void run() {

        String username;
        try {
            username = in.readLine();
        } catch (IOException e) {
            return;
        }
        ClientInfo user = new ClientInfo(username, clientSocket, in, out);
        try {

            clientsController.addingClient(user);

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if (inputLine.isBlank()) continue;
                Helper.printInfo("Received message from " + user.getUsername());
                clientsController.broadcast(inputLine, user);

            }
        } catch (IOException e) {

        } finally {
            clientsController.removeClient(user);
        }

    }
}
