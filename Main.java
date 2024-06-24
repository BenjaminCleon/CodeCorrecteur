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
        
        byte[][] tab = {{1,0,0},{0,1,0},{0,0,1}};
        Matrix m = new Matrix(tab);
        m.display();
        
        Matrix hbase = loadMatrix("data/matrix-15-20-3-4", 15, 20);
        hbase.display();

        // Exercice 2
        byte[][] tab_1 = {{1,1,1},{1,1,1}, {1,1,1}};
        Matrix m_2 = new Matrix(tab_1);

        // Addition
        Matrix m_3 = m_2.add(m);
        m_3.display();
        System.out.println("Expected:");
        byte[][] tab_2 = {{0,1,1},{1,0,1}, {1,1,0}};
        Matrix m_4 = new Matrix(tab_2);
        m_4.display();

        // Multiplication
        Matrix m_5 = m_2.multiply(m_2);
        m_5.display();


        // Transpo
        byte[][] tab_3 = {{1,0,0},{0,0,1}, {1,1,0}};
        Matrix m_6 = new Matrix(tab_3);
        m_6.display();
        m_6.transpose();
        m_6.display();

    }
}
