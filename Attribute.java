import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Attribute {
	private final Map<String, Integer> map = new HashMap<String, Integer>();
	private String[] values;
	private final String name;
	private final short col;

	public Attribute(String name, short col){
		this.name = name;
		this.col  = col;
	}
	
	public int idx(String str){
		return map.get(str);
	}
	
	public void updateValues(String[] values){
		this.values  = values;
		map.clear();
		//Map the atrributes to a number, sort of like an enum
		for(int s = 0; s < values.length; ++s) 
			map.put(values[s], s);  
	}
	
	public String vToStr(int idx){
		return values[idx];
	}
	
	public short col(){
		return col;
	}
	
	public String me(){
		return this.name;
	}
	
	public int cardinality(){
		return values.length;
	}
	/*
	public void setCol(int colNumber){
		this.colNumber = colNumber;
	}*/
	
	public static Attribute[] makeAttributes(String[][] data){
		Attribute[] attributes = new Attribute[data[1].length];
		Set<String> values= new HashSet<String>();
		//populate attributes
		for(short att = 0; att < data[0].length; ++att){
			attributes[att] = new Attribute(data[0][att],  att);
			
			for(int row = 1; row<data.length; ++row){
				if(!values.contains(data[row][att])) //Add the attribute value if it hasnt been discovered yet
					values.add(data[row][att]);	
			}
		
			attributes[att].updateValues(values.toArray(new String[values.size()]));
			values.clear();
		}
	
		return attributes;
	}

}
