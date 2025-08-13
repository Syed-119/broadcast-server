package org.syed;

import org.syed.broadcastserver.view.Client;




public class ClientMain {

    public static void main(String[] args) {

        try {
            new Client();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
