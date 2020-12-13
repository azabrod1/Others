package headings;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class HWGrids {

	public static void main(String[] args) {

		Map<Grid,Integer> grids = new HashMap<Grid,Integer>();
		for(int x = 1; x <= 4; ++x)
			for(int y = 1; y <=4; ++y)
				for(int u = 1; u <= 4; ++u)
					for(int v = 1; v <= 4; ++v){
						Grid g = new Grid(x,y,u,v);
						if(grids.containsKey(g))
							grids.put(g, grids.get(g) + 1);
						
						else 
							grids.put(g, 1);
			}
				
		System.out.println(grids.size());
		
		System.out.println(entropy(grids.values()));
		
		
		
	}
	
	public static double entropy(Collection<Integer> vals){
		
		double entropy = 0;
		double sum = 0;
		
		for(double value : vals)
			sum += value;
	
	
		for(double value : vals)
			entropy -= (value/sum) * Math.log(value/sum)/Math.log(2);

		return entropy; 
		
	}
	
	
	
	
	

}
