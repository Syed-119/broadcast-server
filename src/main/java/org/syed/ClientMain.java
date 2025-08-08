package org.syed;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;



public class ClientMain {



    public ClientMain() throws IOException {
        String serverAddress = "127.0.0.1";
        int serverPort = 5000;

        try (
                Socket socket = new Socket(serverAddress, serverPort);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                BufferedReader serverInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ){
                    System.out.print("Enter your name: ");
                    String name = in.readLine();
                    out.println(name);

                    Thread readerThread = new Thread(() -> {
                        String response;
                        try {
                            while((response = serverInput.readLine()) != null ){
                                System.out.println(response);
                            }
                        } catch (IOException e){
                            System.out.println("Connection closed by server");
                        }
                    }); readerThread.start();

                    String userMsg;
                    while ((userMsg = in.readLine()) != null){
                        out.println(userMsg);
                        if (userMsg.equalsIgnoreCase("exit")){
                            break;
                        }
                    }

                    System.out.println("Disconnecting...");
                    socket.close();
        } catch (IOException e) {
            System.out.println("Could not connect to server");
        }
    }
}
