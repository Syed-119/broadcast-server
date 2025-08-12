package org.syed.broadcastserver.model;

import java.util.ArrayList;
import java.util.List;

public class ConnectedClients {
    private List<ClientInfo> clients;

    public ConnectedClients() {
        clients = new ArrayList<ClientInfo>();
    }

    synchronized public void addClient(ClientInfo client) {
        System.out.println("Adding client for " + Thread.currentThread().getName() + " -> " + client.getUsername());
        clients.add(client);
    }

    synchronized public void removeClient(ClientInfo client) {
        System.out.println("Removing client for " + Thread.currentThread().getName() + " -> " + (client != null ? client.getUsername() : "null"));
        clients.remove(client);
    }

    public void broadCast(String message, ClientInfo sender) {
        String formatted = "From" + (sender != null ? sender.getUsername() : "Unknown") + ": " + message;
        List<ClientInfo> snapshot;
        synchronized (this) {
            snapshot = new ArrayList<>(clients);

        }

        List<ClientInfo> failed = new ArrayList<>();
        for (ClientInfo client : snapshot) {
            if (client == sender) continue;

            try {
                client.getWriter().println(formatted);

                if (client.getWriter().checkError()){
                    failed.add(client);
                }
            } catch (Exception e) {
                failed.add(client);
            }

        }

        if (!failed.isEmpty()) {
            synchronized (this){
                for (ClientInfo client : failed) {
                    clients.remove(client);
                }
            }
        }
    }
}
