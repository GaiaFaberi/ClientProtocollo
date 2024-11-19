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
            do{
                switch (in.readLine()) {
                    case "UL":
                        System.out.println(in.readLine());
                        continua = false;
                        break;
                
                    default:
                        break;
                }
                
            }while(continua);
        } catch (IOException e) {
            System.out.println("Connessione chiusa");
        }
    }
}
