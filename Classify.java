import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class Classify {
	
	private Attribute[] attributes;
	private Attribute Class;
	private Set<Attribute> usedAttributes;

	
	static ClassificationTree classify(String[][] init){
		
		Attribute[] _attributes = Attribute.makeAttributes(init);
		Attribute[] attributes  = Arrays.copyOfRange(_attributes, 0, init[1].length- 1);
		Attribute Class         = _attributes[_attributes.length - 1];
			
		
		Set<Row> table = Row.getTable(init, attributes, Class);		
		Classify model = new Classify(attributes, Class);
		Branch top = new  Branch(null, -1, model.calcEntropy(table));
		model.decide(top, table);
		top.print(" ", true);

		return new ClassificationTree(top);
	}
	
	private Classify(Attribute[] attributes, Attribute Class){
		this.attributes = attributes; this.Class = Class;
		this.usedAttributes = new HashSet<Attribute>();
		
	}
	
	private void decide(Branch decision, Set<Row> set){
		if(decision.entropy == 0){ //If all values in the set are of the same class, we are done
			decision.extra = Class.vToStr(set.iterator().next().Class()); return;
		}
		/*We find the best possible partition */
		double lowestEntropy            = Double.MAX_VALUE;
		Attribute best                  = null;
		Map<Integer,Set<Row>> partition = null;
		
		if(attributes.length == usedAttributes.size()) System.err.println("We ran out of attributes, classification not possible");
		
		for(Attribute attribute : attributes){
			if(usedAttributes.contains(attribute)) continue; //Do not split on an attribute we used before
			
			partition = split(set, attribute);
			
			double entropy = 0;
			for(Set<Row> rows : partition.values())
				entropy += rows.size() * calcEntropy(rows);
			
			if(entropy < lowestEntropy){
				lowestEntropy = entropy;
				best          = attribute;
			}
		}
		
		usedAttributes.add(best);
		lowestEntropy /= set.size(); //Since we scaled up entropy values for weighting earlier
		
		partition = split(set, best);
		decision.myDecision = best;
		decision.childGain = decision.entropy - lowestEntropy;
		decision.children = new Branch[partition.size()];
		int child = 0;
		
		for(Set<Row> rows : partition.values()){
			Branch next = new Branch(best, rows.iterator().next().valAttribute(best), calcEntropy(rows));
			decision.children[child++] = next;
			decide(next, rows);
		}
		usedAttributes.remove(best); //When going back up the call stack, update the class variable 
	}
	
	
	private Map<Integer,Set<Row>> split(Set<Row> set, Attribute at){
		
		Map<Integer,Set<Row>> partition = new HashMap<Integer, Set<Row>>();
		int attributeValue;
		for(Row row : set){
			attributeValue = row.valAttribute(at);
			if(!partition.containsKey(attributeValue)) partition.put(attributeValue,new HashSet<Row>());
			partition.get(attributeValue).add(row);
		}

		return partition;
	}
	
	
	private double calcEntropy(Set<Row> set){
		if(set.isEmpty())
			try {
				throw new Exception("Attempted to calculate entropy of an empty set\n");
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		double[] classes = new double[Class.cardinality()];
				
		for(Row row : set){ //Increment the counter for the number of times we saw the result
			classes[row.Class()]++;
		}
		
		int nonZero = 0; //If only one class appears in the set, it has an entropy of 0
	
		for(double count : classes)
			if(count > 0) ++nonZero;
		
		if(nonZero == 1) return 0;

		double entropy = 0;
		
		for(double count : classes)
			if(count > 0) entropy -= (count/(set.size())) *( Math.log(count/set.size())/Math.log(2));	
			
		return entropy;
		
		
	}
	/**Check if all the rows in the set are of the same class...meaning we are finished with this branch */
	private boolean sameClass(Set<Row> set){
		Iterator<Row> it = set.iterator();
		int Class = it.next().Class();
		
		while(it.hasNext()){
			if(Class != it.next().Class()) return false;
		}
		
		return true;
	}
	

}