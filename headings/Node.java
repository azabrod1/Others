package headings;

import java.util.List;

/**A Node object contains a reference to the header it is associated with and a list of child nodes */
public class Node {
     private final List<Node> children;
     private final Heading heading;
     
     public Node (Heading heading, List<Node> children){
    	 this.heading = heading;
    	 this.children = children;
     }
     
    //Getter function for the Heading object that this node carries
     public Heading getHeading(){
    	 return heading;
     }
     
     /*  Retrieves a child Node; indexed by the input 'child'
      */
     public Node getChild(int child){
    	 return children.get(child);
     }
     
     public List<Node> getChildren(){
    	 return children;
     }
 	
     /*The following functions are included for testing the Outline maker and quickly viewing a
      * representation of the outline. 
      */
     public void print(){
    	 StringBuilder tree = new StringBuilder();
    	 this.print(true, " ", tree);
    	 System.out.println(tree.toString());
     }
     
     
     private void print(boolean last, String branches, StringBuilder tree) {
     	
     	tree.append(String.format("%s%s %d.   %s \n",branches, (last ? "└── " : "├── "), heading.weight, heading.text));
     	         
         for (int c = 0; c < children.size()- 1; c++) 
             children.get(c).print(false, branches + (last? "    " : "│   "), tree);
         
         if (!children.isEmpty()) {
             children.get(children.size() - 1).print(true, branches + (last?"    " : "│   "), tree);
         }
     }
     

 }
