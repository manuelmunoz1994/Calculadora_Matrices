package calculadoramatrices;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import javax.swing.JLabel;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author Manuel Muñoz
 */
public class PantallaIntermedia extends javax.swing.JFrame {

    private int numeroPCs;
    private String operacion;
    private String tamA;
    private String tamB;
    private int[][] matrizFinal;
    private ArrayList<JLabel> labels = new ArrayList<JLabel>();
    private long startTime;
    private long tiempoFinal;
    private int resultado;
    private int numAcabados;
    private int[][] matrizA;
    private int[][] matrizB;

    public PantallaIntermedia(int numeroPCs, String operacion, String tamA, String tamB) {
        initComponents();
        getContentPane().setBackground(Color.white);
        this.numeroPCs = numeroPCs;
        this.operacion = operacion;
        this.tamA = tamA;
        this.tamB = tamB;
        this.resultado = 0;
        this.numAcabados = 0;

        for (int x = 0; x < numeroPCs; x++) {
            JLabel jl = new javax.swing.JLabel();

            jl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/calculadoramatrices/COMPLETOa.png")));
            jl.setBackground(Color.white);
            getContentPane().add(jl);
            labels.add(jl);
           

        }

        HiloServidor e = new HiloServidor(numeroPCs, operacion, tamA, tamB, this);

        Thread et = new Thread(e);
        et.start();

    }

    public void setMatrizA(int[][] matriz) {
        this.matrizA = matriz;
    }

    public void setMatrizB(int[][] matriz) {
        this.matrizB = matriz;
    }

    public void comenzarPc(int pos) {
        labels.get(pos).setIcon(new javax.swing.ImageIcon(getClass().getResource("/calculadoramatrices/COMPLETOb.png")));

    }

    public void acabarPC(int pos) {
        labels.get(pos).setIcon(new javax.swing.ImageIcon(getClass().getResource("/calculadoramatrices/COMPLETOc.png")));
    }

    public synchronized void acabar(int pos, double res) {

        resultado += res;
        labels.get(pos).setIcon(new javax.swing.ImageIcon(getClass().getResource("/calculadoramatrices/COMPLETOc.png")));
        numAcabados++;
        if (numAcabados == numeroPCs) {

            pararTiempo();
            PantallaFinal pf = new PantallaFinal(this, true, tiempoFinal);
            this.setVisible(false);
            pf.setVisible(true);
        }

    }

    public void comenzarTiempo() {

        startTime = System.nanoTime();
        
    }

    public void pararTiempo() {

        long endTime = System.nanoTime();

        tiempoFinal = (endTime - startTime) / 1000000000;

    }

    public synchronized void acabarMatriz(int[][] res, int pos, int vueltas) {
        
        
        int aux = 0;
        int x = vueltas * pos;

        int acaba = x + vueltas;

        while (x < acaba) {
            matrizFinal[x] = res[aux];
            x++;
            aux++;
        }
       
        labels.get(pos).setIcon(new javax.swing.ImageIcon(getClass().getResource("/calculadoramatrices/COMPLETOc.png")));
        numAcabados++;

        if (numAcabados == numeroPCs) {

            pararTiempo();
            PantallaFinal pf = new PantallaFinal(this, false, tiempoFinal);
            
            this.setVisible(false);
            pf.setVisible(true);

        }

    }

    public void LOG(boolean deter) {

        PrintWriter writer = null;
        if (deter) {
            try {
                writer = new PrintWriter("LOG.txt", "UTF-8");
                writer.println(operacion);
                writer.println("");
                writer.println("Matriz A: ");
                writer.println("");
                for (int i = 0; i < matrizA.length; i++) {
                    writer.println(Arrays.toString(matrizA[i]));
                }
                writer.println("");
                writer.println("El resultado es: " + resultado);
                writer.println("");
                writer.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(PantallaFinal.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(PantallaFinal.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                writer.close();
            }
        } else {
            try {
                writer = new PrintWriter("LOG.txt", "UTF-8");
                writer.println(operacion);
                writer.println("");
                writer.println("Matriz A: ");
                writer.println("");
                for (int i = 0; i < matrizA.length; i++) {
                    writer.println(Arrays.toString(matrizA[i]));
                }
                writer.println("");
                writer.println("Matriz B: ");
                writer.println("");
                for (int i = 0; i < matrizB.length; i++) {
                    writer.println(Arrays.toString(matrizB[i]));
                }
                writer.println("");
                writer.println("El resultado es: ");
                writer.println("");
                for (int i = 0; i < matrizFinal.length; i++) {
                    writer.println(Arrays.toString(matrizFinal[i]));
                }

                writer.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(PantallaFinal.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(PantallaFinal.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                writer.close();
            }
        }
    }

    public void crearMatrizFinal(int fil, int col) {
        matrizFinal = new int[fil][col];
    }

    public void empiezaPC(int pos, String ip) {

        labels.get(pos).setIcon(new javax.swing.ImageIcon(getClass().getResource("/calculadoramatrices/COMPLETOb.png")));
        labels.get(pos).setHorizontalTextPosition(JLabel.CENTER);
        labels.get(pos).setText(ip);
        labels.get(pos).setFont(new java.awt.Font("Noto Sans", 1, 16));

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                maximizar(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridLayout(4, 0));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void maximizar(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_maximizar
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }//GEN-LAST:event_maximizar

    /**
     * @param args the command line arguments
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
