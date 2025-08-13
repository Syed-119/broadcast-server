package org.syed;

import org.syed.broadcastserver.view.Server;

import java.io.IOException;


public class ServerMain {

    public static void main(String[] args) throws IOException {
        try {
            new Server();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}