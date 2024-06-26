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

        // Main.exo4(hbase);

        // Main.exo5(Main.exo4(hbase));
        // Main.exo6(hbase);

        //Main.exo7(hbase);
    
        Main.exo9(Main.exo6(hbase), hbase);
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
        // matrice.display();
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

    public static Matrix exo6(Matrix matrix)
    {
        System.out.println("Exo 6");
        matrix.display();
        System.out.println("Encode");
        byte[][] message = {{1,0,1,0,1}};
        Matrix encoded = new Matrix(message);
        encoded = encoded.multiply(matrix.sysTransform().genG());
        encoded.display();
        return encoded;
    }

    public static void exo9(Matrix code, Matrix h)
    {
        byte[][] e = {
                        {0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0}
                     }; 

        byte[][][] y = new byte[4][1][20];
        Matrix[] yTab = new Matrix[4];

        for (int i=0;i<e.length;i++)
            for (int j=0;j<e[0].length;j++)
                y[i][0][j] = (byte) ((e[i][j] + code.getElem(0,j))%2);


        System.out.println("Exo 9");
        System.out.println("Y");
        for (int i=0;i<4;i++)
        {
            yTab[i] = new Matrix(y[i]);
            yTab[i].display();
        }

        System.out.println("Syndrome");
        for (int i=0;i<4;i++)
        {
            System.out.println("S" + i);
            h.multiply(yTab[i].transpose()).display();
        }

        System.out.println("Decode");
        for (int i=0;i<4;i++)
        {
            System.out.println("D" + i);
            TGraph taner = new TGraph(h, 3,4);

            code.display();
            yTab[i].display();
            taner.decode(yTab[i], 100).display();
        }
    }

    public static void exo11(Matrix h)
    {
        System.out.println("Exo 11");
        Matrix geneMatrix = h.sysTransform().genG();
        TGraph taner = new TGraph(h, 5, 15);

        byte[][] message = {{1,0,1,0,1}};
        Matrix encoded = new Matrix(message);
        encoded = encoded.multiply(geneMatrix);
        encoded.display();
        
    }

    public static void exo12(Matrix h)
    {

    }
}