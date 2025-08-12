package org.syed.broadcastserver.model;

public interface ClientChangeListener {
    void onClientAdded(ClientInfo client);
    void onClientRemoved(ClientInfo client);
}
