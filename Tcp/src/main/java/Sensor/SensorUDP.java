package Sensor;

import java.net.*;

public class SensorUDP {
    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket();
            InetAddress ipServidor = InetAddress.getByName("localhost");
            int puertoUDP = 9876;

            System.out.println("Sensor térmico activo...");

            while (true) {

                double temp = 20 + Math.random() * 10;
                String dato = String.format("%.2f ºC", temp);

                byte[] buffer = dato.getBytes();
                DatagramPacket paquete = new DatagramPacket(buffer, buffer.length, ipServidor, puertoUDP);

                socket.send(paquete);
                System.out.println("Sensor envió UDP: " + dato);

                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}