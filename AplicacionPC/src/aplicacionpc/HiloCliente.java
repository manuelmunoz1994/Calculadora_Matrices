package aplicacionpc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import auxiliar.Auxiliar;
import java.io.PrintWriter;

/**
 *
 * @author Manuel Muñoz
 */
public class HiloCliente implements Runnable {

    private Principal g;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Socket clientSocket;
    private BufferedReader br;
    private PrintWriter pw;

    public HiloCliente(Principal c) {
        try {
            this.g = c;
            
            clientSocket = new Socket("10.2.4.100", 1234);
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());
            br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            pw = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException ex) {
            Logger.getLogger(HiloCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        try {

            String operacion = br.readLine();
            pw.println("listo");

            organizar(operacion);

        } catch (IOException ex) {
            Logger.getLogger(HiloCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void organizar(String operacion) {

        int[][] matriz;
        int[][] matrizB;
        switch (operacion) {
            case "Determinante":

                try {
                    matriz = (int[][]) in.readObject();
                    pw.println("listo");
                    int pos = (Integer) in.readObject();
                    g.Calcula();
                    out.writeObject(Auxiliar.Determinante(matriz, pos));
                    g.Acabar();
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(HiloCliente.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(HiloCliente.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "Multiplicación":

                try {

                    matriz = (int[][]) in.readObject();
                    pw.println("listo");
                    matrizB = (int[][]) in.readObject();
                    pw.println("listo");
                    int pos = (Integer) in.readObject();
                    pw.println("listo");
                    int numPCs = (Integer) in.readObject();
                    g.Calcula();
                    out.writeObject(Auxiliar.multiplicarMatrices(matriz, matrizB, pos, matriz.length / numPCs));
                    g.Acabar();

                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(HiloCliente.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(HiloCliente.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;

        }

    }

}
