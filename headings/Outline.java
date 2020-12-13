package headings;
import java.util.List;
import java.util.ArrayList;

/** A user calls the static outline() function with a list of Heading objects and 
 * the function returns the root node of a tree of Headings. 
 * For simplicity and more smooth recursion, during the construction of the tree
 * we create a temporary Outline object to store some "global" variables.
 * The object can be garbage collected after outline() returns and is never seen by the user, 
 * hence the private constructor. 
 *
 */

public class Outline{
	private final List<Heading> headings;
	private int curr; //Which element in the list of headings are we currently processing
	

	private Outline(List<Heading> headings){
		this.headings = headings;
		this.curr = 0;
	}

	/*Inputs: ordered Java.Util list of headers to outline
	 *Purpose :Create outline tree of headers that mimic the structure of a document
	 *Returns: root node of the outline tree
	 *Conditions: All headers must have weight > 0. We allow headers to "skip" weights, i.e. go from H1 immediately 
	 *            to H3. We handle this by placing an header with no title in between the two headers
	 * */
	public static Node outline(List<Heading> headings){
		//Return an empty tree if headings is empty or null
		if(headings == null || headings.size() == 0) 
			return new Node(new Heading(0,"") , new ArrayList<Node>());

		try{
			Outline outline = new Outline(headings);
			Node result =  outline.build(0);
			
			//If we did not get through the whole list (curr != sizeOfList), something must have went wrong
			if(outline.curr != headings.size()){ outline.printError(); return null; }

			return result;

		}catch (Exception e) { 
			System.err.println(e.getMessage()); 
			return null; 
		}

	}

	
/* Inputs: recursion depth. The recursion depth is equal to the weight of the header of the Node
 * that the function will create. 
 * 
 * Purpose:  
 * 			1) Create a Node. This Node will be of the same depth in the overall tree as its recursion depth.
 * 			   The Node will normally contain the next header object in the list of headers (indexed by curr)
 * 			   unless there is a 'skip' in headers. (i.e. last header has weight 3, next has weight 5) in that case the Node
 * 			   just receives an empty Header
 * 
 * 			2) Recursively call build() to fill the list of the node's immediate children. The recursive calls build up the subtrees of those nodes
 * 
 * Effects: Increments curr by one if it uses the next header in the list Headers. Its recursive calls further increment curr.
 * 
 * Returns: Node built by this function
 */
	private Node build(int depth) throws Exception{

		if(headings.get(curr).weight < 1) //We do not allow weights less than 1
			throw new Exception("Invalid outline. Heading weights should be at least 1\n");

		List<Node> children = new ArrayList<Node>();
		Node toReturn;

		if(headings.get(curr).weight == depth) toReturn = new Node(headings.get(curr++), children);
		else                                   toReturn = new Node(new Heading(depth, ""), children);


		while(curr < headings.size() && depth < headings.get(curr).weight)
			children.add(build(depth + 1));		

		return toReturn;
	}

	/*Prints error message in case of failure */
	private void printError(){
		if(headings.get(curr).weight < 1){ 
			System.err.println("Invalid outline. Heading weights should be at least 1\n"); 
		} else{
			System.err.println("Invalid Outline format\n"); 
		}
	}

}
