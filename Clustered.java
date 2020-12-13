import java.util.Random;
import java.util.function.BiFunction;

public class Clustered {

	public static void main(String[] args) {
		
		//hw1();
		exam();
		
	}
	
	public static void exam(){
		int k = 2;
		
		String[] names  = {"o1", "o2", "o3", "o4", "o5", "o6", "o7", "o8"};
		
		int numItems = names.length;

		
		double[] energy = {175, 128, 435, 124, 113, 116, 455, 226};
		
		double[] sugar  = {7.4, 6.0, 20.9, 1.5, 2.2, 2.8, 1.4, 11.8};
		
		double[] price  = {5.3, 76.7, 7.5, 62.5, 16.9, 48.2, 8.5, 37.5};
		
		double[][] energyPrice = new double[numItems][2];
		double[][] energySugar = new double[numItems][2];
		double[][] sugarPrice  = new double[numItems][2];
		double[][] energySugarPrice = new double[numItems][3];


		
		for(int i = 0; i < numItems; ++i){
			energyPrice[i][0] = energy[i];
			energyPrice[i][1] = price[i];
			
			energySugar[i][0] = energy[i];
			energySugar[i][1] = sugar[i];
			
			sugarPrice[i][0] = sugar[i];
			sugarPrice[i][1] = price[i];

			energySugarPrice[i][0] = energy[i];
			energySugarPrice[i][1] = sugar[i];
			energySugarPrice[i][2] = price[i];
			
		}
				

		Kmeans partition = Kmeans.cluster(names, energySugarPrice, k);

		partition.display();
		partition.statistics();
		
		
		
		

		
		
		
		
		
		
		
	}
	
	
	
	
	
	
	
	public static void hw1(){
		BiFunction<double[], double[], Double> CityBlock = (x, y) -> {
			double distance = 0;
			
			for(int d = 0; d < x.length; ++d)
				distance += Math.abs(x[d] - y[d]);
			
			return distance;
		};
		int k = 2;
	
		double[][] data = {
				{12,25}, {96, 36}, {58,12}, {36,48}, {86, 87}, {54,72}, {14,88}, {44, 38}, {22,12}, {25,22}
		};
		
		String[] names = {"o1", "o2", "o3", "o4", "o5", "o6", "o7", "o8", "o9", "o10"};
		
		//Kmeans.printClusters(data, k);
		
		double[][] data2 = new double[15000][4];
		
		Random dGen = new Random();
			    
		for(int i = 0; i < 15000; i+= 3)
	    	for(int d = 0; d < 4; ++d)
	    	    data2[i][d] = 30 + (50 - 30) * dGen.nextDouble();
		
		for(int i = 1; i < 15000; i+= 3)
	    	for(int d = 0; d < 4; ++d)
	    	    data2[i][d] = 70 + (100 - 70) * dGen.nextDouble();
		
		for(int i = 2; i < 15000; i+= 3)
	    	for(int d = 0; d < 4; ++d)
	    	    data2[i][d] = -10 + (35 + 10) * dGen.nextDouble();
		


		Kmeans partition = Kmeans.cluster(data2, k);

		
		partition.statistics();
		//partition.display();
		//partition.display();
		

	
		
		
		
		
	}
	
	
	
	
	
	
	
	
	

}
