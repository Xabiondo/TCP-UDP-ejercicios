package Ejercicio2HoraUDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class ServerUDP {

    private DatagramSocket puertoServer ;
    private boolean estaActivo ;
    private byte[] buf = new byte[256] ;

    public ServerUDP() throws SocketException {
        puertoServer  = new DatagramSocket(4500);
    }

    public void run() throws IOException {
        estaActivo = true ;

        while (estaActivo){
            DatagramPacket paquete = new DatagramPacket(buf  , buf.length) ;
            puertoServer.receive(paquete);

            InetAddress direccion = paquete.getAddress() ;

            int puerto =  paquete.getPort() ;
            //Aqui deberia de poner la respuesta no  ?


            String recibido = new String(paquete.getData() , 0  , paquete.getLength()) ;

            if (recibido.equals("HORA")){
                estaActivo = false ;
                String hora = "23:25";
                buf = hora.getBytes();

            }
            paquete =  new DatagramPacket(buf , buf.length , direccion , puerto) ;

            puertoServer.send(paquete) ;
        }
    }
    public static void main(String[] args) throws IOException {
        ServerUDP serverUDP = new ServerUDP();
        serverUDP.run();

    }
}
