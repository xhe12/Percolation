//package algorithm;
import edu.princeton.cs.algs4.StdRandom;
import java.lang.IllegalArgumentException;
public class PercolationStats {
    private int N;
    private int Trials;
    private double sum;
    private double avg;
    private double[] record;
    
    public PercolationStats(int n, int trials)    // perform trials independent experiments on an n-by-n grid
    {   N = n;
        if (n <= 0 || trials<=0) {
            throw new IllegalArgumentException("The inputs are illegal!");
        }
        Trials = trials;
        sum = 0;
        avg = 0;
        record = new double[Trials];
        //StdRandom rand = new StdRandom();
        for(int i=0;i<Trials;i++){
           Percolation percolation = new Percolation(N);
           while (!percolation.percolates()){
                int  row = StdRandom.uniform(N)+1;
                int  col = StdRandom.uniform(N)+1;
                if (!percolation.isOpen(row,col)){
                    percolation.open(row,col);
                }
            }
        record[i] = percolation.numberOfOpenSites()/Math.pow(N,2);
        sum += record[i];
        }
        avg = sum/Trials;
        
    }
    public double mean()                          // sample mean of percolation threshold
    {
        return avg;
    }

    public double stddev()                        // sample standard deviation of percolation threshold
    {
        double x = 0;
        for (int i=0; i<Trials;i++){
            x += Math.pow(record[i]-avg,2);
        }
        return Math.sqrt(x/(Trials-1));
    }    
    public double confidenceLo()                  // low  endpoint of 95% confidence interval
    {
        return avg-1.96*stddev()/Math.sqrt(Trials);
    }
    public double confidenceHi()                  // high endpoint of 95% confidence interval
    {
        return avg+1.96*stddev()/Math.sqrt(Trials);
    }
    public static void main(String[] args){
        PercolationStats experiment = new PercolationStats(200,100);
        System.out.printf("%f\n", experiment.mean());
        System.out.printf("%f\n", experiment.stddev());
        System.out.printf("%f\n", experiment.confidenceLo());
        System.out.printf("%f\n", experiment.confidenceHi());
    }
}
    
        

