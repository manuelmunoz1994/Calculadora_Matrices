package calculadoramatrices;

import auxiliar.Auxiliar;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Manuel Muñoz
 */
public class HiloServidor implements Runnable {

    private int numeroPCs;
    private String operacion;
    private String tamA;
    private String tamB;
    private Socket clientSocket;
    private ArrayList<eThread> al;
    private PantallaIntermedia gui;
    private ArrayList<Socket> sockets = new ArrayList<Socket>();

    public HiloServidor(int numeroPCs, String operacion, String tamA, String tamB, PantallaIntermedia gui) {
        al = new ArrayList<eThread>();
        this.numeroPCs = numeroPCs;
        this.operacion = operacion;
        this.tamA = tamA;
        this.tamB = tamB;
        this.gui = gui;

    }

    @Override
    public void run() {
        organizar(operacion);
    }

    private void organizar(String operacion) {

        switch (operacion) {
            case "Determinante":

                int[][] matriz = Auxiliar.crearMatriz(tamA);
                gui.setMatrizA(matriz);
           
                if (this.numeroPCs == 1) {
                    gui.comenzarTiempo();
                    gui.acabar(0, Auxiliar.Determinante(matriz));
                    
                }

                try {
                    ServerSocket serverSocket = new ServerSocket(1234);
                    int pos = 0;
                    while (pos < numeroPCs) {

                        clientSocket = serverSocket.accept();
                        sockets.add(clientSocket);
                        gui.empiezaPC(pos, clientSocket.getInetAddress().toString());
                        pos++;

                    }
                    pos = 0;

                    gui.comenzarTiempo();
                    while (pos < numeroPCs) {

                        eThread e = new eThread(sockets.get(pos), matriz, null, pos, gui, "Determinante", this.numeroPCs);
                        Thread et = new Thread(e);
                        et.start();
                        pos++;

                    }
                } catch (IOException ex) {
                    Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "Multiplicación":
                matriz = Auxiliar.crearMatriz(tamA);
                gui.setMatrizA(matriz);
                int[][] matrizB = Auxiliar.crearMatriz(tamB);
                gui.setMatrizB(matrizB);
                gui.crearMatrizFinal(matriz.length, matrizB[0].length);
                if (this.numeroPCs == 1) {
                    gui.comenzarTiempo();
                    
                    gui.acabarMatriz(Auxiliar.multiplicarMatrices(matriz, matrizB), 0, matriz.length);

                }

                try {
                    ServerSocket serverSocket = new ServerSocket(1234);
                    int pos = 0;
                    while (pos < numeroPCs) {

                        clientSocket = serverSocket.accept();
                        sockets.add(clientSocket);
                        gui.empiezaPC(pos, clientSocket.getInetAddress().toString());
                        pos++;

                    }
                    pos = 0;
                    gui.comenzarTiempo();
                    while (pos < numeroPCs) {

                        eThread e = new eThread(sockets.get(pos), matriz, matrizB, pos, gui, "Multiplicación", this.numeroPCs);
                        Thread et = new Thread(e);
                        et.start();
                        pos++;

                    }
                } catch (IOException ex) {
                    Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;

        }

    }
}
