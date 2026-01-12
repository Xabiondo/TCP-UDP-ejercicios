package Ejercicio2HoraUDP;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class ServerUDP {

    private DatagramSocket puertoServer ;
    private boolean estaActivo ;
    private byte[] buf = new byte[1024] ;

    public ServerUDP() throws SocketException {
        puertoServer  = new DatagramSocket(5000);
    }

    public void run() throws IOException {
        estaActivo = true ;

        while (estaActivo){
            DatagramPacket paqueteRecibido = new DatagramPacket(buf  , buf.length) ;
            puertoServer.receive(paqueteRecibido);

            InetAddress direccion = paqueteRecibido.getAddress() ;

            int puertoCliente =  paqueteRecibido.getPort() ;
            //Aqui deberia de poner la respuesta no  ?


            String recibido = new String(paqueteRecibido.getData() , 0  , paqueteRecibido.getLength()) ;
            System.out.println(recibido);

            String respuesta = "";

            if (recibido.equals("HORA")){
                respuesta = "23:25";

            } else if(recibido.equals("APAGADO")){
                respuesta = "apagando servidor" ;
                estaActivo = false ;
            } else {
                respuesta = "comando desconocido" ;
            }
            byte[] tamanyoRespuesta = respuesta.getBytes();
            DatagramPacket respuestaBinaria = new DatagramPacket(tamanyoRespuesta , tamanyoRespuesta.length , direccion , puertoCliente);
            puertoServer.send(respuestaBinaria);

        }
    }
    public static void main(String[] args) throws IOException {
        ServerUDP serverUDP = new ServerUDP();
        serverUDP.run();

    }
}
