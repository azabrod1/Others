import java.util.HashSet;
import java.util.Set;

public class Row {
	private   final Attribute[] attributes;
	private   final int[] values;
	private   final int Class;
	
	protected Row(Attribute[] attributes, int[] values, int Class){
		this.attributes = attributes;
		this.values     = values;
		this.Class      = Class;
	}

	public static Set<Row> getTable(String[][] data, Attribute[] attributes, Attribute Class){

		// populate the table
		Set<Row> table = new HashSet<Row>();

		for(int row = 1; row < data.length; ++row){
			int[] vals = new int[data[0].length-1];
			for(int att = 0; att < data[0].length-1; ++att)
				vals[att]  = attributes[att].idx(data[row][att]);
			
			table.add(new Row(attributes, vals, Class.idx(data[row][data[0].length-1] )));
		}		

		return table;

	}
	
	public int valAttribute(Attribute attribute){
		return values[attribute.col()];
	}

	public int  length(){
		return values.length;
	}

	public int v(int attribute){
		return values[attribute];
	}
	
	public String s(int attribute){
		return attributes[attribute].vToStr(values[attribute]);
	}
	
	public int Class(){
		return Class;
	}
}
