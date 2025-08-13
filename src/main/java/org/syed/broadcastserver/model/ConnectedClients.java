package org.syed.broadcastserver.model;

import java.util.ArrayList;
import java.util.List;

public class ConnectedClients {
    private List<ClientInfo> clients;
    private List<ClientChangeListener> listeners = new ArrayList<>();

    public ConnectedClients() {
        clients = new ArrayList<ClientInfo>();
    }

    synchronized public void addClient(ClientInfo client) {
        clients.add(client);
        notifyClientAdded(client);
    }

    synchronized public void removeClient(ClientInfo client) {
        clients.remove(client);
        notifyClientRemoved(client);
    }

    public synchronized void addListener(ClientChangeListener listener){
        listeners.add(listener);
    }

    public synchronized void removeListener(ClientChangeListener listener){
        listeners.remove(listener);
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

    public void broadcast(String message, ClientInfo sender) {
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
