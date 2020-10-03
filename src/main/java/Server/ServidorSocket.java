/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author ssgx4
 */
public class ServidorSocket {
    
    public static ServerSocket server = null;
    
    public static void main(String[] args) {

        try {
            System.out.println("startando o servidor");
            server = new ServerSocket(9999);
            System.out.println("servidor startado");
            
            Socket s;

            while ((s = server.accept()) != null) {
                new GerenciadorDeClientes(s);
            }

        } catch (IOException e) {
            System.err.println("porta indisponivel");

            try {
                if (server != null) {
                    server.close();
                }
            } catch (IOException e1) {
                System.err.println("a porta esta ocupada ou servidor foi fechado");
                e1.printStackTrace();
            }

            System.exit(0);
        }

    }
}

