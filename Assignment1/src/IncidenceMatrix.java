
import java.io.*;
import java.util.*;


/**
 * Incident matrix implementation for the AssociationGraph interface.
 *
 * Your task is to complete the implementation of this class.  You may add methods, but ensure your modified class compiles and runs.
 *
 * @author Jeffrey Chan, 2019.
 */
public class IncidenceMatrix extends AbstractAssocGraph
{

	/**
	 * Contructs empty graph.
	 */
	
	// some times edges removed from middle of graph, leaving hole. Fix?
	// make sort method more efficient
	// resize array
	// make sure neighbour adds all members if <k available
	
	// corresponding column
	private Map<String, Integer> edges;
	private int lastCol;
	private int[][] matrix = new int[5000][5000];
	
    public IncidenceMatrix() {
    	
    	 vertices = new HashMap<>();
    	 edges = new HashMap<>();
    	 
    	 lastRow = 0;
    	 lastCol = 0;
    }


    public void addVertex(String vertLabel) {
         vertices.put(vertLabel, lastRow++);
    } 


    // adds an extra column of values of weight, -weight, 0
    public void addEdge(String srcLabel, String tarLabel, int weight) {
    	String key = getEdgeKey(srcLabel, tarLabel);
        edges.put(key, lastCol++);
        
        updateWeightInMatrix(key, weight);
    }     

    
    public int getEdgeWeight(String srcLabel, String tarLabel) {
    	// since edges are bi-directional with -ve weights
    	String[] biKey = {getEdgeKey(srcLabel, tarLabel), getEdgeKey(tarLabel,srcLabel)};
    	
    	for (String key : biKey)
    		if (edges.containsKey(key)) {
    			int row = vertices.get(srcLabel);
    			int col = edges.get(key);
    			return (key.equals(biKey[0]) ? 1:-1) * matrix[row][col];
    		}
    	return -1;
	}
    
	public void updateWeightEdge(String srcLabel, String tarLabel, int weight) {
		String key = getEdgeKey(srcLabel, tarLabel);
		
		updateWeightInMatrix(key, weight);
		if (weight == 0)	
			edges.remove(key);
    } 


    public void removeVertex(String vertLabel) {
    	vertices.remove(vertLabel);
    	
    	// remove all edges that contain this vertex
    	for (String key : edges.keySet())
    		if (key.contains(vertLabel))
	    		edges.remove(key);
    	
    	// no need to reset weights in matrix, these values won't be accessed
    	// and will get replaced when a nnew vertex/edge added
    } 


	public List<MyPair> inNearestNeighbours(int k, String vertLabel) {
		return neighbours(k, vertLabel, true);
	}

    public List<MyPair> outNearestNeighbours(int k, String vertLabel) {
    	return neighbours(k, vertLabel, false);
    } 


    public void printVertices(PrintWriter os) {
    	for (String vertex : vertices.keySet())
    		os.println(vertex);
    	os.close();
    }


    public void printEdges(PrintWriter os) {
    	for (String edge : edges.keySet()) {
    		int row = vertices.get(getSrcLabel(edge));
    		int col = edges.get(edge);
    		os.println(getSrcLabel(edge) + " " + getTarLabel(edge) + " " +  matrix[row][col]);
    	}
    	
    	os.close();
    } 

    
    
    
    
    
    
    private void updateWeightInMatrix(String edgeKey, int weight) {
    	int col = edges.get(edgeKey);
    	 for (String vertex : vertices.keySet()) {
			 int row = vertices.get(vertex);
			 
			 if 	(vertex.equals(getSrcLabel(edgeKey)))	matrix[row][col] = weight;
			 else if(vertex.equals(getTarLabel(edgeKey)))	matrix[row][col] = -weight;
			 else											matrix[row][col] = 0;
    	 }
    }
    
    
    
    
  
    
    
    
    
    
    
    private List<MyPair> neighbours(int k, String vertLabel, boolean inOrOut) {
    	//return vertex and weight
        List<MyPair> neighbours = new ArrayList<MyPair>();
    	
    	// eg: A has AB, AD edges, returns B, D
    	for (String edge : edges.keySet()) {
    		String inVertex = getSrcLabel(edge);
			String outVertex = getTarLabel(edge);
			String outOrInVertex = inOrOut ? outVertex : inVertex;
			String inOrOutVertex = inOrOut ? inVertex : outVertex;
			
			addNeighbour(neighbours, vertLabel, outOrInVertex, inOrOutVertex);
//    			// place pair ordered by weight
//    			MyPair pairToAdd = new MyPair(outVertex, edge.weight);
//    			if (neighbours.isEmpty())
//    				neighbours.add(pairToAdd);
//    			else
//	    			for (int i=neighbours.size()-1; i>0; i--) 
//	    				if (pairToAdd.getValue() < neighbours.get(i).getValue())
//	    					neighbours.add(i, pairToAdd);
    	}
    	
    	sortMyPairs(neighbours);
        return (k == -1) ? neighbours : neighbours.subList(0, k);
    }
    

} // end of class IncidenceMatrix
