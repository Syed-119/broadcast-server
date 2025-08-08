package org.syed.broadcastserver.model;

import java.util.ArrayList;
import java.util.List;

public class ConnectedClients {
    private List<ClientInfo> clients;

    public ConnectedClients() {
        clients = new ArrayList<ClientInfo>();
    }

    synchronized public void addClient(ClientInfo client) {
        System.out.println("Adding client for " + Thread.currentThread().getName());
        clients.add(client);
    }

    synchronized public void removeClient(ClientInfo client) {
        System.out.println("Removing client for " + Thread.currentThread().getName());
        clients.remove(client);
    }
}
