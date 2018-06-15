package auxiliar;

import java.text.MessageFormat;

/**
 *
 * @author Manuel Muñoz
 */
public class Auxiliar {

    public static int[][] multiplicarMatrices(int[][] x, int[][] y) {
        int[][] resultado;
        int xColumnas, xFilas, yColumnas, yFilas;

        xFilas = x.length;
        xColumnas = x[0].length;
        yFilas = y.length;
        yColumnas = y[0].length;
        resultado = new int[xFilas][yColumnas];

        if (xColumnas != yFilas) {
            throw new IllegalArgumentException(
                    MessageFormat.format("NO coinciden las columnas de A con las filas de B!: {0} != {1}.", xColumnas, yFilas));
        }

        for (int i = 0; i < xFilas; i++) {
            for (int j = 0; j < yColumnas; j++) {
                for (int k = 0; k < xColumnas; k++) {
                    resultado[i][j] += (x[i][k] * y[k][j]);
                }
            }
        }

        return (resultado);
    }

    public static int[][] crearMatriz(String tam) {
        String[] partes = tam.split("x");
        int x = Integer.valueOf(partes[0]);

        int y = Integer.valueOf(partes[1]);

        int[][] matriz = new int[x][y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                matriz[i][j] = (int) (Math.random() * 10);
            }
        }
        return matriz;
    }

    public static double Determinante(int[][] matriz) {
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

            resultado += matriz[0][i] * Math.pow(-1, (double) i) * Determinante(aux);
        }
        return (resultado);
    }
}
