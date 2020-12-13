
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.BiFunction;

public class Kmeans {
	private final double score;
	private final String[][] result;
	private final double timeTaken;
	private final int  roundsTaken;
	private static final boolean printIntermediate = true;


	private Kmeans(double score, long timeTaken, String[][] result, int roundsTaken){
		this.score = score; this.timeTaken = timeTaken; this.result = result; this.roundsTaken = roundsTaken;
	}
	
	
	private static String[] genNames(double[][] data){
		String[] names = new String[data.length];
		for(int point = 0; point < data.length; ++point){
			StringBuilder s = new StringBuilder();
			s.append('('); 
			for(int d = 0; d < data[0].length - 1; ++d){
				s.append(data[point][d]); s.append(',');
			}; s.append(data[point][data[0].length -1]);
			s.append(')'); 
			names[point] = s.toString();
		}
		return names;
	}
	
	public String[][] getResult(){return result;}
	
	public double getScore(){
		return score;
	}
	
	public static Kmeans cluster(double[][] data, int K){
		
		return cluster(genNames(data), data, K, null);
		
	}
	
	
	public static Kmeans cluster(double[][] data, int K, BiFunction<double[], double[], Double> metric){
	
		return cluster(genNames(data), data, K, metric);
		
	}
	
	public static Kmeans cluster(String[] names, double[][] data, int K){
		
		return cluster(names, data, K, null);
		
	}
	
	public static Kmeans cluster(String[] names, double[][] data, int K, BiFunction<double[], double[], Double> metric ){

		long startTime = System.currentTimeMillis();
		int dims = data[0].length; //Number of dimensions the data has
	    double [][] cPoints  = new double[K][dims];
	    
	    
	    if(metric == null) metric = loadEuclideanMetric();
	    
	    double[] max = new double[dims];
	    Arrays.fill(max, Double.NEGATIVE_INFINITY);
	    double[] min = new double[dims];
	    Arrays.fill(min, Double.POSITIVE_INFINITY);
	    
	   
	    
	    //We try to get the range of values possible for each dimension 
	    for(double[] point : data){
	    	for(int d = 0; d < dims; ++d){
	    		if(point[d]  > max[d]) max[d] = point[d];
	    		if(point[d]  < min[d]) min[d] = point[d];
	    	}
	    }
	    
	    //Generate k random points
	    Random dGen = new Random();
	    
		for(double[] cluster : cPoints)
	    	for(int d = 0; d < dims; ++d)
	    	    cluster[d] = min[d] + (max[d] - min[d]) * dGen.nextDouble();
	    	    
		
		//We will place all the points into one of these k clusters
		//We store these assignments in an array
		int[] assignment = new int[data.length];
		Arrays.fill(assignment, -1);
		boolean stable;
		double d_score = 0; //Measure of how well our algorithm clustered the data
		double dist;
		int[] cardinality = new int[K];    //How many elements are in each cluster
		double[][] sums   = new double[K][dims]; // Sums of all points in the cluster: used for averaging
		int round  = 1;
		
		//Main algorithm
		do{
			Arrays.fill(cardinality, 0);
		
			stable = true;
			//For each point, find which cluster it is closest to
			for(int point = 0; point < data.length; ++point){
				double minDistance = Double.MAX_VALUE; int closest = -1;
				for(int k = 0; k < K; ++k){
					dist = metric.apply(cPoints[k], data[point]); //get the distance between the point and its cluster
					if(dist < minDistance){ //if its closer than any other explored so far, record the new closest cluster
						minDistance = dist;
						closest  = k;
					}
				}
				//If any point changed clusters from last time, we have not yet reached a stable state
				if(closest != assignment[point]){  
					stable = false; assignment[point] = closest;
				}
				
			
				/**** This is an optimization from the normal algorithm, we add up the sums of the dimensions in each cluster 
				 * while assigning members to the cluster to avoid another loop over all the data after the partition step. 
				 * A user should be wary of sum overflows when using large data sets
				 */
				cardinality[closest]++;
				for(int d = 0; d < dims; ++d)
					sums[closest][d] += data[point][d];

				/***************************/

			}

			if(stable){  //If none of the clusters changed, we have found a stable set of clusters
				
				for(int point = 0; point < data.length; ++point)
					//Sum of errors (distances from cluster points) is the score
						d_score += metric.apply(cPoints[assignment[point]], data[point]);
				 
				break;
			}
		
			if(printIntermediate) 
				displayIntermediate(round, K, assignment, cardinality, names);
			
			//Otherwise, we compute the cluster points for the next round
			for(int k = 0; k < K; ++k)
				for(int d = 0; d < dims; ++d){
					cPoints[k][d] = sums[k][d]/(cardinality[k]);
					sums[k][d]= 0; // reset for next round
				}
			
			++round;
		}while(true);

		//Now return results

		String[][] partition = new String[K][];
		int[] idx = new int[K];

		for(int k = 0; k < K; ++k)
			partition[k]= new String[cardinality[k]];
		
		
		int k;
		for(int point = 0; point < data.length; ++point){
			k = assignment[point];
			partition[k][idx[k]++] = names[point];
		}
		
		return new Kmeans(d_score/data.length, System.currentTimeMillis() - startTime, partition, round);
	}
	
	

	public void display(){
		System.out.printf("Final Result (Round %d) \n", this.roundsTaken);

		for(int cluster = 0; cluster < result.length; ++cluster){
			System.out.println("***Cluster: " + cluster + "***");
			for(String point : result[cluster])
				System.out.println(point);
		}
	}
	
	public static void displayIntermediate(int round, int K, int[] assignment, int[] cardinality, String[] names){
		System.out.printf("Round %d: \n", round);

		String[][] partition = new String[K][];
		int[] idx = new int[K];

		for(int k = 0; k < K; ++k)
			partition[k]= new String[cardinality[k]];
		
		
		int k;
		for(int point = 0; point < assignment.length; ++point){
			k = assignment[point];
			partition[k][idx[k]++] = names[point];
		}
	
		
		for(int cluster = 0; cluster < partition.length; ++cluster){
			System.out.println("***Cluster: " + cluster + "***");
			for(String point : partition[cluster])
				System.out.println(point);
		}
		
		
		System.out.println();
	}
	
	public void statistics(){
		System.out.println("Total time taken: " + timeTaken);
		System.out.println("Total rounds taken: " + roundsTaken);

		System.out.printf("Algorithm Score:  %.2f \n", score );
		
		int c = 0;
		for(String[] cluster : result)
			System.out.println("Cluster " + c++ + ": length:" + cluster.length);
	}

	
	private static BiFunction<double[], double[] , Double> loadEuclideanMetric(){
		return new BiFunction<double[], double[], Double>() {
			
			@Override
			public Double apply(double[] x, double[] y) {

				if(x.length != y.length) return null;
				
				double dSQR = 0;
				
				for(int n = 0; n < x.length; ++n)
					dSQR += (x[n] - y[n]) * (x[n] - y[n]);
					
				return Math.sqrt(dSQR);		
			}
		
    };
	}


}
