package auxiliar;

import java.text.MessageFormat;

/**
 *
 * @author Manuel Muñoz
 */
public class Auxiliar {

    public static int[][] multiplicarMatrices(int[][] x, int[][] y, int pos, int vueltas) {
        int[][] resultado;
        int xColumnas, xFilas, yColumnas, yFilas;

        xFilas = pos * vueltas + vueltas;
        xColumnas = x[0].length;
        yFilas = y.length;
        yColumnas = y[0].length;
        resultado = new int[vueltas][yColumnas];

        int aux = 0;

        for (int i = pos * vueltas; i < xFilas; i++) {

            for (int j = 0; j < yColumnas; j++) {
                for (int k = 0; k < xColumnas; k++) {
                    resultado[aux][j] += (x[i][k] * y[k][j]);
                }
            }
            aux++;
        }

        return (resultado);
    }

    public static double Determinante(int[][] matriz, int pos) {
        int[][] aux;
        double resultado = 0;

        if (matriz.length == 1) {
            resultado = matriz[0][0];
            return (resultado);
        }

        if (matriz.length == 2) {
            resultado = ((matriz[0][0] * matriz[1][1]) - (matriz[0][1] * matriz[1][0]));
            return (resultado);
        }

        for (int i = pos; i < pos + 1; i++) {
            aux = new int[matriz.length - 1][matriz[0].length - 1];

            for (int j = 1; j < matriz.length; j++) {
                for (int k = 0; k < matriz[0].length; k++) {
                    if (k < i) {
                        aux[j - 1][k] = matriz[j][k];
                    } else if (k > i) {
                        aux[j - 1][k - 1] = matriz[j][k];
                    }
                }
            }

            resultado += matriz[0][i] * Math.pow(-1, (double) i) * Determinanteb(aux);
        }
        return (resultado);
    }

    public static double Determinanteb(int[][] matriz) {
        int[][] aux;
        double resultado = 0;

        if (matriz.length == 1) {
            resultado = matriz[0][0];
            return (resultado);
        }

        if (matriz.length == 2) {
            resultado = ((matriz[0][0] * matriz[1][1]) - (matriz[0][1] * matriz[1][0]));
            return (resultado);
        }

        for (int i = 0; i < matriz[0].length; i++) {
            aux = new int[matriz.length - 1][matriz[0].length - 1];

            for (int j = 1; j < matriz.length; j++) {
                for (int k = 0; k < matriz[0].length; k++) {
                    if (k < i) {
                        aux[j - 1][k] = matriz[j][k];
                    } else if (k > i) {
                        aux[j - 1][k - 1] = matriz[j][k];
                    }
                }
            }

            resultado += matriz[0][i] * Math.pow(-1, (double) i) * Determinanteb(aux);
        }
        return (resultado);
    }

}
