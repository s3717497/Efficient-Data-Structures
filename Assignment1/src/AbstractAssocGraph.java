import java.io.*;
import java.util.*;

/**
 * Abstract class for Association graph that implements some of the common functionality.
 *
 * Note, you should not need to modify this but can if need to.  Just make sure to test to make sure everything works.
 *
 * @author Jeffrey Chan, 2019.
 */
public abstract class AbstractAssocGraph implements AssociationGraph
{
	// corresponding row
	Map<String, Integer> vertices;
	int lastRow;
	
	protected static final int RESIZE_AMOUNT = 100;
	protected static final int EDGE_NOT_EXIST = -1;
	
	protected String getEdgeKey(String srcLabel, String tarLabel) {
    	return srcLabel + "->" + tarLabel;
    }
	protected String getSrcLabel(String key) {
    	int endIndex = key.indexOf("->");
    	return key.substring(0, endIndex);
    }
	protected String getTarLabel(String key) {
    	int startIndex = key.indexOf("->") + 2;
    	return key.substring(startIndex);
    }
	
	protected void addNeighbour(List<MyPair> neighbours, String vertLabel, String vertLabelMatch, String inOrOutVertex) {
		if (vertLabel.equals(vertLabelMatch)) 
			neighbours.add(new MyPair(inOrOutVertex, Math.abs(getEdgeWeight(vertLabel,inOrOutVertex))));
	}
	
	// sort by weight greatest to smallest (bubble sort)						/*make more efficient?*/
    protected void sortMyPairs(List<MyPair> pairs) {
    	boolean noSwap = true;
    	do {
    		noSwap = true;
	    	for (int i=1; i<pairs.size(); i++) {
	    		MyPair prevPair = pairs.get(i-1);
	    		MyPair pair 	= pairs.get(i);
	    		
	    		if (prevPair.getValue() < pair.getValue()) {
	    			pairs.set(i-1, pair);
	    			pairs.set(i, prevPair);
	    			noSwap = false;
	    		}
	    	}
    	} while (noSwap == false);
    }

} // end of abstract graph AbstractAssocGraph
