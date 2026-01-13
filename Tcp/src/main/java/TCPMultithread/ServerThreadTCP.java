package TCPMultithread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThreadTCP {

    public ServerThreadTCP() throws IOException {
        ServerSocket puertoServidor = new ServerSocket(2020);
        System.out.println("El puerto 2020 está abierto. Esperando clientes...");

        while (true) {

            Socket socket = puertoServidor.accept();
            System.out.println("serverTarea.Cliente conectado.");

            TCPServerThread hiloCliente = new TCPServerThread(socket, this);

            hiloCliente.start();
        }
    }

    private int clientNumber = 1 ;

    public int getClientNumber(){
        return  clientNumber++;
    }


    public static void main(String[] args) {
        try {
            new ServerThreadTCP();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}