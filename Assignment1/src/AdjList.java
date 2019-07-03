
import java.io.*;
import java.util.*;

/**
 * Adjacency list implementation for the AssociationGraph interface.
 *
 * Your task is to complete the implementation of this class.  You may add methods, but ensure your modified class compiles and runs.
 *
 * @author Jeffrey Chan, 2019.
 */
public class AdjList extends AbstractAssocGraph
{

    // resizeArray of nodes
	
	// nodes formed from edges to the main vertices
	private Node[][] nodes;
	
    public AdjList() {
    	
    	lastRow = 0;
    	vertices = new HashMap<>();
    	nodes = new Node[100][100];

    } // end of AdjList()


    public void addVertex(String vertLabel) {
    	vertices.put(vertLabel, lastRow++);
    } 

    // find the inVertex row, create a new outVertex node
    public void addEdge(String srcLabel, String tarLabel, int weight) {
    	int row = vertices.get(srcLabel);
    	Node[] vertexNodes = nodes[row]; 
    	
    	// add a node in the first available space
    	for (int i=0; i<vertexNodes.length; i++)
    		if (vertexNodes[i] == null)	
    			vertexNodes[i] = new Node(tarLabel, weight);
    }

    // given in/outVertex label, find outVertex and its weight
    public int getEdgeWeight(String srcLabel, String tarLabel) {
    	int row = vertices.get(srcLabel);
    	Node[] vertexNodes = nodes[row]; 
    	
    	for (Node node : vertexNodes)
    		if (node.tarLabel.equals(tarLabel))
    			return node.weight;
    	return -1;
    }
    
    public void updateWeightEdge(String srcLabel, String tarLabel, int weight) {
    	int row = vertices.get(srcLabel);
    	Node[] vertexNodes = nodes[row]; 
    	
    	for (Node node : vertexNodes)
    		if (node.tarLabel.equals(tarLabel))
    			node.weight = weight;
    }


    public void removeVertex(String vertLabel) {
        vertices.remove(vertLabel);
        
        // remove edge from other vertices
        for (int row : vertices.values())
        	for (int i=0; i<nodes[row].length; i++)
        		if (nodes[row][i] != null && vertLabel.equals(nodes[row][i].tarLabel))
        			nodes[row][i] = null;
    } 
    
    public List<MyPair> inNearestNeighbours(int k, String vertLabel) {
    	//return vertex and weight
        List<MyPair> neighbours = new ArrayList<MyPair>();
    	
    	// eg: A has AB, AD edges, returns B, D
        for (String vertex : vertices.keySet()) {
        	
        	int row = vertices.get(vertex);
        	for (Node node : nodes[row]) {
        		String inVertex = vertex;
        		String outVertex = node.tarLabel;
        		
        		addNeighbour(neighbours, vertLabel, outVertex, inVertex);
        	}
    	}
    	
    	sortMyPairs(neighbours);
        return (k == -1) ? neighbours : neighbours.subList(0, k);
    } // end of inNearestNeighbours()


    public List<MyPair> outNearestNeighbours(int k, String vertLabel) {
        List<MyPair> neighbours = new ArrayList<MyPair>();
        int row = vertices.get(vertLabel);
        
        for (Node node : nodes[row]) {
        	String outVertex = node.tarLabel;
        	
        	neighbours.add(new MyPair(outVertex, getEdgeWeight(vertLabel,outVertex)));
        }
        	
		sortMyPairs(neighbours);
		return (k == -1) ? neighbours : neighbours.subList(0, k);
    } // end of outNearestNeighbours()


    public void printVertices(PrintWriter os) {
    	for (String vertex : vertices.keySet())
    		os.println(vertex);
    	os.close();
    }


    public void printEdges(PrintWriter os) {
    	for (String inVertex : vertices.keySet()) {
    		int row = vertices.get(inVertex);
    		
    		for (Node outVertex : nodes[row]) 
    			os.println(inVertex + " " + outVertex.tarLabel + " " + outVertex.weight);
    	}
    	os.close();
    }


    // represents an outVertex/edge
    protected class Node {
    	
    	String tarLabel;
    	int weight;
   
    	public Node(String label, int weight) {
    		tarLabel = label;
    		this.weight = weight;
    	}
    }
    
    
    
} // end of class AdjList
