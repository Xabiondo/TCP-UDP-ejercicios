package org.example.Ejercicio1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorEcho {

    private ServerSocket puertoEscucha ;
    private Socket puertoCliente ;
    private PrintWriter output ;
    private BufferedReader input ;

    public void start(int port) throws IOException {
        puertoEscucha =  new ServerSocket(port);
        puertoCliente = puertoEscucha.accept() ;
        output = new PrintWriter(puertoCliente.getOutputStream() , true);
        input = new BufferedReader(new InputStreamReader(puertoCliente.getInputStream()));


        String saludo = input.readLine() ;

        if (saludo.equals("hola")){
            output.println("adios");
        }else output.println("Vaya educación");
    }

    public void stop() throws IOException {
        puertoCliente.close();
        puertoEscucha.close();
        output.close();
        input.close();
    }

    public static void main(String[] args) throws IOException {
        ServidorEcho servidor =  new ServidorEcho();
        servidor.start(5000);


    }


}
