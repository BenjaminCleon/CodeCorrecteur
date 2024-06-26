import java.util.*;
import java.io.*;


public class Matrix {
    private byte[][] data = null;
    private int rows = 0, cols = 0;
    
    public Matrix(int r, int c) {
        data = new byte[r][c];
        rows = r;
        cols = c;
    }
    
    public Matrix(byte[][] tab) {
        rows = tab.length;
        cols = tab[0].length;
        data = new byte[rows][cols];
        for (int i = 0 ; i < rows ; i ++)
            for (int j = 0 ; j < cols ; j ++) 
                data[i][j] = tab[i][j];
    }
    
    public int getRows() {
        return rows;
    }
    
    public int getCols() {
        return cols;
    }
    
    public byte getElem(int i, int j) {
        return data[i][j];
    }
    
    public void setElem(int i, int j, byte b) {
        data[i][j] = b;
    }
    
    public boolean isEqualTo(Matrix m){
        if ((rows != m.rows) || (cols != m.cols))
            return false;
        for (int i = 0; i < rows; i++) 
            for (int j = 0; j < cols; j++) 
                if (data[i][j] != m.data[i][j])
                    return false;
                return true;
    }
    
    public void shiftRow(int a, int b){
        byte tmp = 0;
        for (int i = 0; i < cols; i++){
            tmp = data[a][i];
            data[a][i] = data[b][i];
            data[b][i] = tmp;
        }
    }
    
    public void shiftCol(int a, int b){
        byte tmp = 0;
        for (int i = 0; i < rows; i++){
            tmp = data[i][a];
            data[i][a] = data[i][b];
            data[i][b] = tmp;
        }
    }
     
    public void display() {
        System.out.print("[");
        for (int i = 0; i < rows; i++) {
            if (i != 0) {
                System.out.print(" ");
            }
            
            System.out.print("[");
            
            for (int j = 0; j < cols; j++) {
                System.out.printf("%d", data[i][j]);
                
                if (j != cols - 1) {
                    System.out.print(" ");
                }
            }
            
            System.out.print("]");
            
            if (i == rows - 1) {
                System.out.print("]");
            }
            
            System.out.println();
        }
        System.out.println();
    }
    
    public Matrix transpose() {
        Matrix result = new Matrix(cols, rows);
        
        for (int i = 0; i < rows; i++) 
            for (int j = 0; j < cols; j++) 
                result.data[j][i] = data[i][j];
    
        return result;
    }
    
    public Matrix add(Matrix m){
        Matrix r = new Matrix(rows,m.cols);
        
        if ((m.rows != rows) || (m.cols != cols))
            System.out.printf("Erreur d'addition\n");
        
        for (int i = 0; i < rows; i++) 
            for (int j = 0; j < cols; j++) 
                r.data[i][j] = (byte) ((data[i][j] + m.data[i][j]) % 2);
        return r;
    }
    
    public Matrix multiply(Matrix m){
        Matrix r = new Matrix(rows,m.cols);
        
        this.display();
        m.display();

        if (m.rows != cols)
            System.out.printf("Erreur de multiplication\n");
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < m.cols; j++) {
                r.data[i][j] = 0;
                for (int k = 0; k < cols; k++){
                    r.data[i][j] =  (byte) ((r.data[i][j] + data[i][k] * m.data[k][j]) % 2);
                }
            }
        }
        
        return r;
    }

    public void addRow(int a, int b)
    {
        if ( !(a >= 0 && b >=0 && a < this.rows && b < this.rows) )return;

        for (int i = 0; i < this.cols; i++)
            this.data[b][i] = (byte) ((this.data[a][i] + this.data[b][i]) % 2);
    }

    public void addCol(int a, int b)
    {
        if ( !(a >= 0 && b >=0 && a < this.cols && b < this.cols) )return;
        
        for (int i = 0; i < this.rows; i++)
            this.data[i][b] = (byte) ((this.data[i][a] + this.data[i][b]) % 2);
    }

    public Matrix genG()
    {
        int k = this.cols - this.rows;
        int n = this.cols;
        Matrix generatrice = new Matrix(k, n);
        Matrix thisTranspose = this.transpose();

        // construction de la matrice génératrice
        for (int nb_ligne=0;nb_ligne<generatrice.rows;nb_ligne++)
        {
            for (int nb_col=0;nb_col<generatrice.cols;nb_col++)
            {
                if      (nb_col >= nb_ligne + k          ) generatrice.data[nb_ligne][nb_col] = thisTranspose.data[nb_ligne][nb_col-k];
                else if (nb_col == nb_ligne && nb_col < k) generatrice.data[nb_ligne][nb_col] = 1;
            }
        }

        return generatrice;
    }

    public Matrix sysTransform() {
        Matrix r = new Matrix(this.data);

        // Descente
        for (int i = 0; i < r.rows; i++) {
            int j = r.cols - r.rows + i;
            boolean permutation = false;
            for (int i_p = i; i_p < r.rows; i_p ++) {

                // Permutation
                if (r.getElem(i_p, j) == 1 && !permutation && i_p != i) {
                    r.shiftRow(i, i_p);
                    permutation = true;
                }

                // Add
                if (i_p > i && r.getElem(i_p, j) == 1 && permutation) {
                    r.addRow(i, i_p); 
                }
            }
        }

        // Remontée
        for (int i = r.rows - 1; i >= 0; i--) {
            int j = r.cols - r.rows + i;
            for (int i_p = i; i_p >= 0; i_p--) {
                // Add
                if (i_p < i && r.getElem(i_p, j) == 1) {
                   r.addRow(i, i_p); 
                }
            }
        }

        return r;
    }

    public Matrix errGen(int w)
    {
        return null;
    }
}

