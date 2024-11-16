package com.example;

import java.io.BufferedReader;
import java.io.IOException;

public class ReaderThread extends Thread {
    BufferedReader in;

    public ReaderThread(BufferedReader in) {
        this.in = in;
    }

    public void run() {
        try {
            while (true) {
                String messaggio = in.readLine();
                if (messaggio.equalsIgnoreCase("UL")) {
                    System.out.println("lista username: ");
                }
                System.out.println(messaggio);
            }
        } catch (IOException e) {
            System.out.println("Connessione chiusa");
        }
    }
}
