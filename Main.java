import java.util.*;
import java.io.*;

public class Main {
    
    public static Matrix loadMatrix(String file, int r, int c) {
        byte[] tmp =  new byte[r * c];
        byte[][] data = new byte[r][c];
        try {
            FileInputStream fos = new FileInputStream(file);
            fos.read(tmp);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(int i = 0; i < r; i++)
            for (int j = 0; j< c; j++)
                data[i][j] = tmp[i * c + j];
            return new Matrix(data);
    }
    
    public static void main(String[] arg){        
        Matrix hbase = loadMatrix("data/matrix-15-20-3-4", 15, 20);
        Matrix transformed = hbase.sysTransform();
        transformed.display();

        // Main.exo2();
        // Main.exo3(hbase);

        // Main.exo5(Main.exo4(hbase));
        // Main.exo6(hbase);

        Main.exo7(hbase);
    }

    public static void exo7(Matrix matrice)
    {
        matrice.display();
        TGraph taner = new TGraph(matrice, 3,4);
        taner.display();
    }

    public static void exo2() {
        System.out.println("Exo 2");
        byte[][] tab = {{1,0,0},{0,1,0},{0,0,1}};
        byte[][] tab2 = {{1,1,0},{0,0,0},{1,0,1}};
        
        Matrix m = new Matrix(tab);
        Matrix m2 = new Matrix(tab2);
        m.display();

        // test des fonctions de matrices
        m.add(m2).display();
        m.multiply(m2).display();

        m2.display();
        m2.transpose().display();

    }

    public static void exo3(Matrix matrice) {
        System.out.println("Exo 3");
        matrice.display();
        System.out.println("Add row");
        matrice.addRow(3, 4);
        matrice.display();
        System.out.println("Add col");
        matrice.addCol(1, 2);
        matrice.display();
    }

    public static Matrix exo4(Matrix matrice) 
    {
        System.out.println("Exo 4");
        matrice.display();
        System.out.println("SysTransform");
        matrice.sysTransform().display();
        return matrice.sysTransform();
    }

    public static void exo5(Matrix matrice)
    {
        System.out.println("Exo 5");
        matrice.display();
        System.out.println("GenMatrix");
        Matrix genMatrix = matrice.genG();
        genMatrix.display();
    }

    public static void exo6(Matrix matrix)
    {
        System.out.println("Exo 6");
        matrix.display();
        System.out.println("Encode");
        byte[][] message = {{1,0,1,0,1}};
        Matrix encoded = new Matrix(message);
        encoded = encoded.multiply(matrix.sysTransform().genG());
        encoded.display();
    }
}
