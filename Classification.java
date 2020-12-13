
public class Classification {

	public static void main(String[] args) {
		
		
		String[][] init = {
				{"A", "B", "C", "D"    , "class" },
				{"a3", "b2", "c1", "d1", "Red"   },
				{"a1", "b1", "c2" ,"d1", "Red"   },
				{"a1", "b2", "c2", "d2", "Green" },
				{"a1", "b1", "c2", "d3", "Green" },
				{"a2", "b1", "c1", "d3", "Green" },
				{"a2", "b2", "c1", "d2", "Green" },
				{"a3", "b1", "c2", "d2", "Blue"  },
				{"a3", "b2", "c2", "d3", "Blue"  }
				
		};
		
		Classify.classify(init);
		

	}

}
