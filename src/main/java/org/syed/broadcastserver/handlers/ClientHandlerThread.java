package org.syed.broadcastserver.handlers;

import org.syed.broadcastserver.model.ClientInfo;
import org.syed.broadcastserver.model.ConnectedClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;


public class ClientHandlerThread implements Runnable {

    private Thread thread;
    ConnectedClients connectedClients;
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;


    ClientHandlerThread(InetAddress address, ConnectedClients clients, Socket socket) throws IOException {
        this.thread = new Thread(this, address.toString());
        this.connectedClients = clients;
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

        ClientInfo clientInfo = new ClientInfo();
        connectedClients.addClient();
        System.out.println(thread.getName() + " Starting");

        try {
            String inputLine;
            while ((inputLine = in.readLine()) !=null) {
                System.out.println("Recieved message from " + clientSocket.getInetAddress() + " " + thread.getName() + ": " + inputLine);
            }
        } catch (IOException e) {
            System.out.println("Error handling client: " + e.getMessage());
        }

    }
}
