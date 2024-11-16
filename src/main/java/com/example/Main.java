package com.example;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //10.22.9.14
        try (Socket socket = new Socket("192.168.1.150", 3000)) {
            System.out.println("Connesso al server");
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.writeBytes("s" + "\n");
            String rispostaServer = in.readLine();

            if (rispostaServer.equals("l?")) {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Hai già un account?: ");
                String scelta = scanner.nextLine();

                if (scelta.equalsIgnoreCase("no")) {
                    out.writeBytes("su?" + "\n");
                    register(scanner, socket, in, out);
                }
                out.writeBytes("si?" + "\n");
                login(scanner, socket, in, out);

                
                
                ReaderThread readerThread = new ReaderThread(in, out, scanner);
                
                readerThread.start();
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    

    private static void register(Scanner scanner, Socket socket, BufferedReader in, DataOutputStream out) throws IOException {
        boolean continua = true;
        if (in.readLine().equals("suC?")) {

            while (continua) {
                System.out.println("Inserisci il nome utente: ");
                String username = scanner.nextLine();
                System.out.println("Inserisci la password: ");
                String password = scanner.nextLine();

                out.writeBytes(username + "\n");
                out.writeBytes(password + "\n");
                String risposta = in.readLine();
                if ("su!".equals(risposta)) {
                    System.out.println("Errore: username già in uso.");
                } else if ("suS".equals(risposta)) {
                    System.out.println("Registrazione avvenuta con successo.");
                    continua = false;
                }
            }
            

        }

        
    }

    private static void login(Scanner scanner, Socket socket, BufferedReader in, DataOutputStream out) throws IOException {

        boolean continua = true;
        if(in.readLine().equals("siC?")){
            while(continua){
                System.out.println("Inserici l'username: ");
                String username = scanner.nextLine();
                System.out.println("Inserisci la password: ");

                String password = scanner.nextLine();
                                    
                out.writeBytes(username + "\n");
                out.writeBytes(password + "\n");
            
                String risposta = in.readLine();
                if ("si!".equals(risposta)) {
                    System.out.println("Credenziali errate. Riprova: ");
                } else if ("siS".equals(risposta)) {
                    System.out.println("Accesso effettuato");
                    continua = false;
                }

            }
            
            
        }

        
    }
}