package Ejercicio2HoraUDP;

import java.io.IOException;
import java.net.*;

public class ClienteUDP {

    private DatagramSocket puerto ;
    private InetAddress direccionServidor ;

    private byte[] buf ;

    public ClienteUDP() throws SocketException, UnknownHostException {
        puerto = new DatagramSocket() ;
        direccionServidor = InetAddress.getByName("localhost") ;
    }

    public String enviarMensaje(String mensaje) throws IOException {
        buf = mensaje.getBytes() ;
        DatagramPacket paquete = new DatagramPacket(buf , buf.length , direccionServidor , 4500);
        puerto.send(paquete);

        paquete = new DatagramPacket(buf , buf.length) ;
        puerto.receive(paquete);

        String recibido = new String(
                paquete.getData() , 0 , paquete.getLength()) ;
                return recibido ;


    }
    public void close(){
        puerto.close();
    }
    public static void main(String[] args) throws IOException {
        ClienteUDP clienteUDP = new ClienteUDP() ;
        clienteUDP.enviarMensaje("HORA");
        String hora = clienteUDP.enviarMensaje("HORA");
        System.out.println(hora);


    }
}
