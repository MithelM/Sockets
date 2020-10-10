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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ssgx4
 */
public class GerenciadorDeClientes extends Thread {

    private Socket cliente;
    private static final Map<String, GerenciadorDeClientes> clientes = new HashMap<String, GerenciadorDeClientes>();
    protected String nomeCliente;
    protected BufferedReader leitor;
    protected PrintWriter escritor;

    public GerenciadorDeClientes(Socket cliente) {
        this.cliente = cliente;
        start();
    }

    /**
     *
     */
    @Override
    public void run() {
        try {
            leitor = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            escritor = new PrintWriter(cliente.getOutputStream(), true);

            getEscritor().println("Digite seu nome..");
            String msg = leitor.readLine();
            this.nomeCliente = msg;
            getEscritor().println("Bem vindo " + this.getNomeCliente());

            clientes.put(this.getNomeCliente(), this);

            while ((msg = leitor.readLine()) != null) {

                if (msg.equals("::close")) {
                    getEscritor().println("conexao fechada \n");
                    this.cliente.close();

                } else if (msg.equals("::close-server")) {

                    getEscritor().println(this.getNomeCliente() + ", fechou a conexao com o servidor \n");

                    ServidorSocket.server.close();
                } else if (msg.toLowerCase().startsWith("::msg")) {
                    String nomeDestinatario = msg.substring(5, msg.length());
                    System.out.println("enviando para: " + nomeDestinatario);

                    GerenciadorDeClientes destinatario = clientes.get(nomeDestinatario);

                    if (destinatario == null) {
                        getEscritor().println("o cliente informado nao existe");
                    } else {
                        escritor.println("digite uma mensagem para " + destinatario.getNomeCliente());
                        destinatario.getEscritor().println(this.getNomeCliente() + " disse: " + leitor.readLine());
                        escritor.println("mensagem enviada..");
                    }

                } else {
                    getEscritor().println(this.getNomeCliente() + ", enivou: " + msg);
                }

            }

        } catch (IOException e) {
            System.err.println("o cliente " + cliente + " teve problema na conexao" + e.getMessage());
        }
    }

    /**
     * @return the escritor
     */
    public PrintWriter getEscritor() {
        return escritor;
    }

    /**
     * @return the nomeCliente
     */
    public String getNomeCliente() {
        return nomeCliente;
    }

}
