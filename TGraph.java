import java.util.HashMap;

public class TGraph
{
    private int w_r;
    private int w_c;
    private int n_r;
    private int n_c;

    private int[][] left ;
    private int[][] right;

    public TGraph(Matrix H, int wc, int wr)
    {
        int cpt=0;

        this.w_c = wc;
        this.w_r = wr;

        this.n_r = H.getRows();
        this.n_c = H.getCols();
        
        this.left  = new int[this.n_r][this.w_r+1];
        this.right = new int[this.n_c][this.w_c+1];

        for(int i=0;i<n_r;i++)
        {
            cpt=0;
            this.left[i][0] = 0;
            for(int j=0;j<this.n_c;j++)
            {
                if(H.getElem(i,j) == 1)
                   this.left[i][++cpt] = j;
            }
        }

        for (int i=0;i<n_c;i++)
        {
            this.right[i][0] = 0;
            cpt=0;
            for (int j=0;j<n_r;j++)
            {
                if (H.getElem(j,i) == 1)
                    this.right[i][++cpt] = j;
            }
        }
    }

    public void display()
    {
        System.out.println("Left");
        this.display(this.left);

        System.out.println("Right");
        this.display(this.right);
    }

    private void display(int[][] tab)
    {
        for (int i=0;i<tab.length;i++)
        {
            for(int j=0;j<tab[0].length;j++)System.out.print("+----");

            System.out.println("+");
            for (int j=0;j<tab[0].length;j++)System.out.print(String.format("|%4d", tab[i][j]));
            System.out.println("|");
        }

        for(int j=0;j<tab[0].length;j++)System.out.print("+----");
        
        System.out.println("+");
    }

    public Matrix decode(Matrix code, int rounds) {
        Matrix returned_code = new Matrix(code);
        HashMap<Byte, Integer> count = new HashMap<Byte, Integer>();

        for (int N = 0; N < this.n_c; N++) {
            this.right[N][0] = returned_code.getElem(0, N);
        }

        for (int c = 0; c < rounds; c++) {
            boolean syndrom_is_correct = true;

            for (int i = 0; i < this.n_r; i++) {
                syndrom_is_correct = true;
                this.left[i][0] = 0;
                for (int j = 0; j < this.w_r + 1; j++) {
                    int K_val = this.right[(int) this.left[i][j]][0];
                    this.left[i][0] = K_val + this.left[i][0] % 2;
                }
                if (this.left[i][0] == 1) {
                    syndrom_is_correct = false;
                }
                
            }

            if (syndrom_is_correct) {
                for (int j = 0; j < this.n_c; j++) {
                    returned_code.setElem(0, j, (byte) this.right[j][0]);
                }
                return returned_code;
            }

            int max = 0;
            int i_max = 0;
            for (int i = 0; i < this.n_c; i++) {
                count.put((byte) i, 0);
                for (int j = 0; j < this.w_c + 1; j++) {
                    int index_in_left = this.right[i][j];
                    int count_value = count.get((byte) i) + this.left[index_in_left][0];
                    count.put((byte) i, count_value);
                }
                if (count.get((byte) i) > max) {
                    max = count.get((byte) i);
                    i_max = i;
                }
            }

            this.right[i_max][0] = 1 - this.right[i_max][0];

        }

        for (int i = 0; i < returned_code.getCols(); i++) {
            returned_code.setElem(0, i, (byte) -1);
        }
        return returned_code;


    }
}
