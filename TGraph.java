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
}
