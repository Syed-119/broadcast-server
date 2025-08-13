package org.syed.broadcastserver.view;
//TODO THIS CLASS CAN BE EXTENDED FOR INPUT VALIDATION
public class Helper {

    public static void promptUser(String prompt){
        System.out.print(prompt);
    }

    public static void printInfo(String message){
        System.out.println(message);
    }

    public static void printError(String error){
        System.err.println(error);
    }
}
