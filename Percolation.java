//package algorithm;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.lang.IllegalArgumentException;
//import edu.princeton.cs.algs4.In;

public class Percolation{
    private WeightedQuickUnionUF set;
    private WeightedQuickUnionUF onlyTop;
    private int count;  //number of open sites
    private boolean[] status;// shows if the site is open
    private int N;
    private int t_node;
    private int b_node;
    
    public Percolation(int number){
        
      count = 0;
      N = number;
      if (N <= 0) {
            throw new IllegalArgumentException("The input N is illegal!");
        }
      set = new WeightedQuickUnionUF(N*N+2);
      onlyTop = new WeightedQuickUnionUF(N*N+1);
      status = new boolean[N*N+2];//if open, status = 1; otherwise status = 0;
      t_node = 0;//index of the top node;
      b_node = N*N+1;//index of the bottom node;
      
      for (int i = 0; i<N*N+2; i++)
         {
          status[i] = false; //All sites are blocked initially;
         } 
      status[t_node] = true;
      status[b_node] = true;
      
    }
    public void open(int row, int col)
    { 
        // 1<=row,col<=N
      validateArray(row, col);
      int index = (row-1)*N + col;
      if(status[index]==true) return;
      status[index] = true;
      count++;
      
      if (row == 1)
      {
          set.union(t_node,index);
          onlyTop.union(t_node,index);
      }
      
      if (row == N)
      {
          set.union(b_node,index);
      }
      
     
      if (row>1 && isOpen(row-1,col))
      {
           set.union(index,(row-2)*N+col);
           onlyTop.union(index,(row-2)*N+col);
      }
      
      if (col>1 && isOpen(row,col-1))
      {
          set.union(index,(row-1)*N+col-1);
          onlyTop.union(index,(row-1)*N+col-1);
      }
              
      if (col<N && isOpen(row,col+1))
      {
          set.union(index,(row-1)*N+col+1);
          onlyTop.union(index,(row-1)*N+col+1);
      }
      if (row<N && isOpen(row+1,col))
      {
          set.union(index,row*N+col);
          onlyTop.union(index,row*N+col);
      }
     
    }
    
    
    
    
    public boolean isOpen(int row, int col)
    {   // input 1<=row,col<=N
        validateArray(row, col);
        int index = (row-1)*N + col;
        return status[index];
    }
    
    private void validateArray(int i, int j) {
       if (i < 1 || j < 1 || i > N || j > N) {
          throw new IllegalArgumentException("index: (" + i + ", " + j + ") are out of bound!");
        }
    }
       
    public int numberOfOpenSites(){
        return count;
    }
    
    public boolean isFull(int row, int col){
       validateArray(row, col);
       int index = (row-1)*N + col;
       return onlyTop.connected(t_node, index);
    }
    
    public boolean percolates(){
       return set.connected(t_node, b_node);
    }
    
    public static void main(String[] args)
    {
        Percolation percolation = new Percolation(3);
        percolation.open(1, 3);
        percolation.open(2, 3);
        percolation.open(3, 3);
        percolation.open(3, 1);
        System.out.println(percolation.isFull(3,1));
    }
}