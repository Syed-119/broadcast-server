package org.syed.broadcastserver.model;

import java.util.ArrayList;
import java.util.List;

public class ConnectedClients {
    private List<ClientInfo> clients;
    private List<ClientChangeListener> listeners = new ArrayList<>();

    public ConnectedClients() {
        clients = new ArrayList<>();
    }

    synchronized public boolean addClient(ClientInfo client) {
        if (client == null || client.getUsername() == null) return false;
        if (clients.stream().anyMatch(c -> c.getUsername().equalsIgnoreCase(client.getUsername()))) {
            return false;
        }
        clients.add(client);
        notifyClientAdded(client);
        systemBroadcast(client.getUsername() + " has joined the chat.");
        return true;
    }

    synchronized public void removeClient(ClientInfo client) {
        clients.remove(client);
        notifyClientRemoved(client);
        systemBroadcast(client.getUsername() + " has left the chat.");
    }

    public synchronized void addListener(ClientChangeListener listener){
        listeners.add(listener);
    }

    public synchronized void clientMessage(ClientInfo client){
        notifyClientMessage(client);
    }


    private void notifyClientAdded(ClientInfo client){
        for (ClientChangeListener listener : listeners) {
            listener.onClientAdded(client);
        }
    }

    private void notifyClientRemoved(ClientInfo client){
        for (ClientChangeListener listener : listeners) {
            listener.onClientRemoved(client);
        }
    }

    private void notifyClientMessage(ClientInfo client){
        for (ClientChangeListener listener : listeners) {
            listener.onClientMessageReceived(client);
        }
    }

    public void broadcast(String message, ClientInfo sender) {
        String formatted = "From " + (sender != null ? sender.getUsername() : "Unknown") + ": " + message;
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

    public void systemBroadcast(String message){
        List<ClientInfo> snapshot;
        synchronized (this) {
            snapshot = new ArrayList<>(clients);
        }
        for (ClientInfo client : snapshot) {
            try {
                client.getWriter().println("[SYSTEM] " + message);
            }
            catch (Exception e) {return;}
        }
    }
}
