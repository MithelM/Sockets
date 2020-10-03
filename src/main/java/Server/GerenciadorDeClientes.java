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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ssgx4
 */
public class GerenciadorDeClientes extends Thread {

    private Socket cliente;
    private static final List<Socket> clientes = new ArrayList<Socket>();

    public GerenciadorDeClientes(Socket cliente) {
        clientes.add(cliente);
        this.cliente = cliente;
        start();
    }

    /**
     *
     */
    @Override
    public void run() {
        try {
            BufferedReader leitor = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            PrintWriter escritor = new PrintWriter(cliente.getOutputStream(), true);

            escritor.println("Bem vindo");

            String msg;
            while ((msg = leitor.readLine()) != null) {

                if (msg.equals("close")) {
                    escritor.println("conexao fechada \n");
                    this.cliente.close();

                } else if (msg.equals("close-server")) {

                    escritor.println("voce fechou a conexao com o servidor \n");

                    for (Socket cliente : clientes) {
                        cliente.close();
                    }
                  ServidorSocket.server.close();
                }
                    else {
                        escritor.println("voce disse: " + msg);
                    }

            }

        } catch (IOException e) {
            System.err.println("o cliente "+ cliente+ " teve problema na conexao" + e.getMessage());
        }
    }
}