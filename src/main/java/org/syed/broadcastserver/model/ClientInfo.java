package org.syed.broadcastserver.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.Socket;

public class ClientInfo {
    private String username;
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;

    public String getUsername() {
        return username;
    }

    public Socket getSocket() {
        return socket;
    }

    public BufferedReader getReader() {
        return reader;
    }

    public BufferedWriter getWriter() {
        return writer;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
