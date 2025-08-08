package org.syed.broadcastserver.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientInfo {
    private String username;
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    public ClientInfo(String username, Socket socket, BufferedReader reader, PrintWriter writer) {
        this.username = username;
        this.socket = socket;
        this.reader = reader;
        this.writer = writer;
    }

    public String getUsername() {
        return username;
    }

    public Socket getSocket() {
        return socket;
    }

    public BufferedReader getReader() {
        return reader;
    }

    public PrintWriter getWriter() {
        return writer;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
