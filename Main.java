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
    
    public static void main(String[] arg)
    {        
    
        Main.tache1();
        Main.tache2();
        Main.tache3();

    }

    public static void tache1()
    {
        Matrix hbase = loadMatrix("data/matrix-15-20-3-4", 15, 20);
        Matrix systeMatrix = null;

        System.out.println("Matrice de controle H : ");
        hbase.display();

        systeMatrix = Main.exo4(hbase);
        
        Main.exo5(systeMatrix);
        Main.exo6(systeMatrix);
    }

    public static void tache2()
    {
        Matrix hbase = loadMatrix("data/matrix-15-20-3-4", 15, 20);
        Matrix encoded = null;

        System.out.println("Encodage de u (x=u.G): \n");
        encoded = Main.exo6(hbase);

        System.out.println("Syndrome de x (S=H.x^t): \n");
        Matrix.getSymptome(hbase, encoded).transpose().display();

        Main.exo7(hbase);


        System.out.println("-------------------------------------\r\n" + //
                           "--Bruitage et correction du mot x :\r\n"   + 
                           "------------------------------------");

        Main.exo9(encoded, hbase);
    }

    public static void tache3()
    {
        System.out.println("Test de la tâche 3 relativement longue");
        Matrix hfat = loadMatrix("data/matrix-2048-6144-5-15", 2048, 6144);

        Main.exo13(hfat, 124);

        System.out.println();
        Main.exo13(hfat, 134);
        
        System.out.println();
        Main.exo13(hfat, 144);
        
        System.out.println();
        Main.exo13(hfat, 154);
    }

    public static void exo7(Matrix matrice)
    {
        System.out.println("Graph de Tanner : \n");
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
        System.out.println("Forme systèmatique de H :");
        matrice.sysTransform().display();
        return matrice.sysTransform();
    }

    public static void exo5(Matrix matrice)
    {
        System.out.println("Matrice génératrice G");

        Matrix genMatrix = matrice.genG();
        genMatrix.display();
    }

    public static Matrix exo6(Matrix matrix)
    {
        System.out.println("Mot binaire u: \n");
        byte[][] message = {{1,0,1,0,1}};
        Matrix encoded = new Matrix(message);
        encoded.display();

        encoded = encoded.multiply(matrix.sysTransform().genG());
        
        System.out.println("Encodage de u (x=u.G): \n");
        encoded.display();
        return encoded;
    }

    public static void exo9(Matrix code, Matrix h)
    {
        TGraph taner = null;
        Matrix decodeMatrix = null;

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


        for (int i=0;i<4;i++)
        {
            yTab[i] = new Matrix(y[i]);

            System.out.println("Mot de code x : \n");
            code.display();

            System.out.println("Vecteur d'erreurs e" + (i+1) + " : \n");
            
            System.out.print("[[");
            for (int j=0;j<e[0].length;j++)
                System.out.print(e[i][j] + " ");
            System.out.println("]]\n");

            System.out.println("Mot de code bruité y"+(i+1) + " =x+e"+ (i+1) + " : \n");
            yTab[i].display();

            System.out.println("Syndrome de y" + (i+1) + " : \n");
            h.multiply(yTab[i].transpose()).transpose().display();

            System.out.println("Correction de x" + (i+1) + " de y" + (i+1) + " : \n");
            taner = new TGraph(h, 3,4);
            (decodeMatrix = taner.decode(yTab[i], 100)).display();

            System.out.println("x"+ (i+1) + " = x : " + decodeMatrix.equals(code) + "\n");
            System.out.println("------------------------------------------------\r\n");
        }
    }

    public static void exo10(Matrix h)
    {
        System.out.println("Exo 10");
        Matrix geneMatrix = h.sysTransform().genG();
        System.out.println("n = " + geneMatrix.getCols());
        System.out.println("k = " + geneMatrix.getRows());
        System.out.println("Redondance = " + (geneMatrix.getCols() - geneMatrix.getRows()));
        System.out.println("Rendement = " + (double) geneMatrix.getRows() / geneMatrix.getCols());
    }

    public static Matrix exo11(Matrix h)
    {
        Matrix geneMatrix = h.sysTransform().genG();
        byte[][] message = new byte[1][geneMatrix.getRows()];

        for (int i=0;i<message[0].length;i++)
            message[0][i] = (byte) ((i+1)%2);

        Matrix x = new Matrix(message);
        x = x.multiply(geneMatrix);

        return x;
    }

    public static void exo12(Matrix matrice)
    {
        matrice.errGen(3000).display();
        matrice.errGen(3000).display();
        matrice.errGen(3000).display();
    }

    public static void exo13(Matrix matrice, int w)
    {
        Matrix x = Main.exo11(matrice);
        Matrix y = new Matrix(x.getRows(), x.getCols());
        Matrix decodeWord;
        Matrix error;
        TGraph graph = new TGraph(matrice, 5, 15);

        int failed  = 0;

        for (int i=0;i<10000;i++)
        {
            error = matrice.errGen(w);
            y = x.add(error);

            decodeWord = graph.decode(y, 200);
            for (int j=0;j<x.getCols();j++)
            {
                if (!x.equals(decodeWord))
                {
                    failed++;
                    break;
                }
            }
        }

        System.out.println("Pour w = " + w + ",");
        System.out.println((double) failed / (10000) * 100 + "% de cas critiques\n");
    }
}