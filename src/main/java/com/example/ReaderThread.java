package com.example;

import java.io.BufferedReader;

import java.io.IOException;


public class ReaderThread extends Thread {
   private BufferedReader in;

    public ReaderThread(BufferedReader in) {
        this.in = in;
    }

    public void run() {
        try {

            boolean continua = true;
            String userSender = "";
            String text = "";
            do{
                switch (in.readLine()) {
                    case "UL":
                        System.out.println(in.readLine());
                        break;
                    
                    case "PRIVATE":
                        userSender = in.readLine();
                        text = in.readLine();
                        System.out.println("(privato)" + userSender + ": " + text);
                        break;
                    
                    case "!":
                        System.out.println("Si e' verificato un problema.");
                        break;
                    
                    case "GB":
                        userSender = in.readLine();
                        text = in.readLine();
                        System.out.println("(globale)" + userSender + ": " + text);
                        break;

                        case "JL":
                        userSender = in.readLine();
                        text = in.readLine();
                        System.out.println(userSender + text);
                    default:
                        break;
                }
                
            }while(continua);
        } catch (IOException e) {
            System.out.println("Connessione chiusa");
        }
    }
}
