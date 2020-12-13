import java.util.ArrayList;

public class Branch{
	
	final Attribute parentDecision;
	Attribute myDecision;
	int attValue;
	Branch[] children = null;
	final double entropy;
	double childGain; 
	String extra = null;
	
	Branch(Attribute parentDecision,  int attValue, double entropy){
		this.parentDecision = parentDecision; this.attValue = attValue; this.entropy = entropy;
	}
	
    public void print(String prefix, boolean isTail) {
    	int numChildren = children == null? 0 : children.length;
    	
    	String strToPrint = (parentDecision != null? parentDecision.vToStr(attValue) : "Tree") +  (
    			extra != null? " (" + extra + ") " : "");
    	
        System.out.println(prefix + (isTail ? "└── " : "├── ") + strToPrint);
        for (int i = 0; i < numChildren- 1; i++) {
            children[i].print(prefix + (isTail ? "    " : "│   "), false);
        }
        if (numChildren > 0) {
            children[numChildren - 1]
                    .print(prefix + (isTail ?"    " : "│   "), true);
        }
    }
	

}

