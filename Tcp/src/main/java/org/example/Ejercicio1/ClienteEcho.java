package org.example.Ejercicio1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClienteEcho {

    private Socket puertoCliente ;
    private PrintWriter output ;
    private BufferedReader input ;

    public void empezarConexión(String ip , int port) throws IOException {
        puertoCliente = new Socket(ip , port) ;
        output = new PrintWriter(puertoCliente.getOutputStream() , true);
        input = new BufferedReader(new InputStreamReader(puertoCliente.getInputStream())) ;
    }

    public String enviarMensaje(String mensaje) throws IOException {
        output.println(mensaje);
        String respuestaServidor = input.readLine();
        return  respuestaServidor ;

    }

    public void pararConexion() throws IOException {
        input.close();
        output.close();
        puertoCliente.close();
    }
    public static void main(String []args) throws IOException {
        ClienteEcho cliente = new ClienteEcho();
        cliente.empezarConexión("localhost" , 5000);
        String respuuesta = cliente.enviarMensaje("hola");
        System.out.println("El servidor dice " + respuuesta);

    }


}
