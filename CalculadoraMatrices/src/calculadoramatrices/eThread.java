package calculadoramatrices;

import auxiliar.Auxiliar;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Manuel Muñoz
 */
public class eThread implements Runnable {

    private Socket s;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private HiloServidor hs;
    private PantallaIntermedia gui;
    private int[][] matriz;
    private int[][] matrizB;
    private int pos;
    private String operacion;
    private PrintWriter pw;
    private int numPCs;
    private BufferedReader br;

    public eThread(Socket s, int[][] matriz, int[][] matrizB, int pos, PantallaIntermedia gui, String operacion, int numPcs) {

        try {
            this.matriz = matriz;
            this.matrizB = matrizB;
            this.pos = pos;
            this.s = s;
            this.gui = gui;
            out = new ObjectOutputStream(s.getOutputStream());
            in = new ObjectInputStream(s.getInputStream());
            this.hs = hs;
            this.operacion = operacion;
            pw = new PrintWriter(s.getOutputStream(), true);
            br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            this.numPCs = numPcs;

        } catch (IOException ex) {
            Logger.getLogger(eThread.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void run() {

        try {
            switch (operacion) {
                case "Determinante":

                    pw.println(operacion);
                    String auxiliar = br.readLine();
                    out.writeObject(matriz);
                    auxiliar = br.readLine();
                    out.writeObject(Integer.valueOf(pos));

                    Double res = (Double) in.readObject();
                    System.out.println(res);

                    gui.acabar(pos, res);

                    break;
                case "Multiplicación":
                    pw.println(operacion);
                    String auxiliar2 = br.readLine();
                    out.writeObject(matriz);
                    auxiliar2 = br.readLine();
                    out.writeObject(matrizB);
                    auxiliar2 = br.readLine();
                    out.writeObject(Integer.valueOf(pos));
                    auxiliar2 = br.readLine();
                    out.writeObject(Integer.valueOf(this.numPCs));

                    int[][] resM = (int[][]) in.readObject();

                    gui.acabarMatriz(resM, pos, matriz.length/numPCs);

                    break;

            }

        } catch (IOException ex) {
            Logger.getLogger(eThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(eThread.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
