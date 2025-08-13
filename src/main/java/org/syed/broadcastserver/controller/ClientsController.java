package org.syed.broadcastserver.controller;

import org.syed.broadcastserver.model.ClientInfo;
import org.syed.broadcastserver.model.ConnectedClients;

public class ClientsController {

    ConnectedClients clients;

    public ClientsController(ConnectedClients clients) {
        this.clients = clients;
    }

    public void addingClient(ClientInfo client){
        clients.addClient(client);
    }

    public void removeClient(ClientInfo client){
        clients.removeClient(client);
    }

    public void broadcast(String message, ClientInfo sender) {
        clients.broadcast(message, sender);
    }

    public void systemBroadcast(String message) {
        clients.systemBroadcast(message);
    }
}
