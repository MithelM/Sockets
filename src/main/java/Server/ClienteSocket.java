/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author ssgx4
 */
public class ClienteSocket {

    public static void main(String[] args) {
        try {
            System.out.println("Conectando ao servidor...");
            
            final Socket s = new Socket("127.0.0.1", 9999);
            System.out.println("Conectado");
                    
            new Thread() {
                
                @Override
                public void run() {
                    
                    String msg = null;
                    try {
                        BufferedReader leitor = new BufferedReader(new InputStreamReader(s.getInputStream()));
                        while ((msg = leitor.readLine()) != null) {
                            
                            System.out.println("o servidor: " + msg);
                        }

                    } catch (IOException e) {
                        System.out.println("impossivel ler a mensagem do servidor");
                        e.printStackTrace();
                    }
                }
            }.start();

            // escrenvendo para o servidor
            PrintWriter escritor = new PrintWriter(s.getOutputStream(), true);
           System.out.println("digite uma mensagem: ");
            while (true) {
                Scanner leitor = new Scanner(System.in);
                String msg = leitor.nextLine();
                if (msg.equals("sair")) {
                    escritor.println("close");
                    leitor.close();
                    System.exit(0);
              
                }
                escritor.println(msg);
            }

        } catch (IOException e) {
            System.err.println("o servidor pode estar fora ar");
            e.printStackTrace();
        }
    }
}
