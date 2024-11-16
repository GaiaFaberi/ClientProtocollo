package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class ReaderThread extends Thread {
   private BufferedReader in;
   private Scanner scanner;
   private DataOutputStream out;

    public ReaderThread(BufferedReader in, DataOutputStream out, Scanner scanner) {
        this.in = in;
        this.scanner = scanner;
        this.out = out;
    }

    public void run() {
        try {

            String messaggio;
            System.out.println("Operazioni possibili:");
            System.out.println("1. lista utenti, comando: UserList");
            System.out.println("2. chat privata, comando: @username + messaggio");
            System.out.println("3. chat globale, comando: --GLOBAL + messaggio");
           
            do {
                messaggio = scanner.nextLine();         
                out.writeBytes(messaggio + "\n");
                System.out.println(in.readLine());
            }while(true) ;
        } catch (IOException e) {
            System.out.println("Connessione chiusa");
        }
    }
}
