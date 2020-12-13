package headings;

import java.util.Arrays;

public class Grid {

	boolean[][] grid = new boolean[5][5];

	
	public Grid(int u1, int v1, int u2, int v2){
		
		paint(u1,v1, grid);
		paint(u2,v2, grid);

		
		
		
	}
	
	public void paint(int u, int v, boolean[][] grid){
		grid[1][u] = true;
		
		int y2 = (int) Math.round((double)(v-u)/3.0);
		grid[2][u+y2] = true;
		
		int y3 = (int) Math.round(((double)(2*v-2*u))/3.0);
		grid[3][u+y3] = true;
		
		grid[4][v]  = true;
	
		
	}
	
	
	
	@Override public int hashCode(){
		
		int hash = 0;
		
		for(int x = 1; x <= 4; ++x)
			for(int y = 1; y <= 4; ++y){
				if(grid[x][y]) 
					hash += x*17 + y * 1011;
			}
		
		return hash;
	}
	
	@Override public boolean equals(Object o){
		
	       if (o == this) return true;
	       
	        if (!(o instanceof Grid)) {
	            return false;
	        }

	        Grid that = (Grid) o;

	        for(int x = 1; x <= 4; ++x)
				for(int y = 1; y <= 4; ++y)
					if(this.grid[x][y] != that.grid[x][y] ) 
						return false;
				
			return true; 
	           
	    }
	
	
}
