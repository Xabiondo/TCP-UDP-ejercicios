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
        DatagramPacket paquete = new DatagramPacket(buf , buf.length , direccionServidor , 5000);
        puerto.send(paquete);

        byte[] respuestaServidor = new byte[1024];

        DatagramPacket paqueteUsuario = new DatagramPacket(respuestaServidor , respuestaServidor.length) ;
        puerto.receive(paqueteUsuario);

        String recibido = new String(
                paqueteUsuario.getData() , 0 , paquete.getLength()) ;
        System.out.println(recibido);
                return recibido ;



    }
    public void close(){
        puerto.close();
    }
    public static void main(String[] args) throws IOException {
        ClienteUDP clienteUDP = new ClienteUDP() ;
        clienteUDP.enviarMensaje("que pasa");



    }
}
