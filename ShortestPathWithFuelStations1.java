import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

class Vertex implements Comparable<Vertex>
{
    public final String name;
    public Edge[] adjacencies;
    public double minDistance = Double.POSITIVE_INFINITY;
    public Vertex previous;
    public Vertex(String argName) { name = argName; }
    public String toString() { return name; }
    public int compareTo(Vertex other)
    {
        return Double.compare(minDistance, other.minDistance);
    }
}

class Edge
{
    public final Vertex target;
    public final double weight;
    public Edge(Vertex argTarget, double argWeight)
    { target = argTarget; weight = argWeight; }
}

public class ShortestPathWithFuelStations1
{
    public static void computePaths(Vertex source)
    {
        source.minDistance = 0.;
        PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
      	vertexQueue.add(source);

	while (!vertexQueue.isEmpty()) {
	    Vertex u = vertexQueue.poll();

            // Visit each edge exiting u
            for (Edge e : u.adjacencies)
            {
                Vertex v = e.target;
                double weight = e.weight;
                double distanceThroughU = u.minDistance + weight;
		if (distanceThroughU < v.minDistance) {
		    vertexQueue.remove(v);
		    v.minDistance = distanceThroughU ;
		    v.previous = u;
		    vertexQueue.add(v);
		}
            }
        }
    }

    public static List<Vertex> getShortestPathTo(Vertex target)
    {
        List<Vertex> path = new ArrayList<Vertex>();
        for (Vertex vertex = target; vertex != null; vertex = vertex.previous)
            path.add(vertex);
        Collections.reverse(path);
        return path;
    }

    public static void main(String[] args) throws Exception
    {
    	//Scanner scan = new Scanner(System.in);
    	
    	Scanner scan = new Scanner(new File("C:\\SC\\Work\\Projects\\ANF\\WorkSpace\\ShortestPathWithFuelStations\\src\\input8.txt"));
    	
    	System.out.println("Enter the driving range of the vehicle");
    	double dDrivingRange = scan.nextDouble();
    	
    	System.out.println("Enter the distance to empty from the vehicle");
    	double dDistanceToEmpty = scan.nextDouble();
    	
    	System.out.println("Enter the Number of Vertices");
    	int iNumberOfVertices = scan.nextInt();
    	
    	System.out.println("Enter the Number of Vertices with fuel stations  0 to " + String.valueOf(iNumberOfVertices-1));
    	int iNumberOfVerticesWithFuelStations = scan.nextInt();
    	
    	System.out.println("Enter the Vertices with Fuel Station");
    	Map<Integer, Integer> mpFuelStationVertices = new HashMap<Integer, Integer>();
    	for(int i=0;i<iNumberOfVerticesWithFuelStations;i++){
    		int iFuelStationVertices = scan.nextInt();
    		mpFuelStationVertices.put(iFuelStationVertices,iFuelStationVertices);
    	}
    	
    	
    	
    	Vertex[] vertexArray = new Vertex[iNumberOfVertices];
    	for(int i=0;i<iNumberOfVertices;i++){
    		vertexArray[i]=new Vertex("Vertex_"+String.valueOf(i));
    	}
    	for(int i=0;i<iNumberOfVertices;i++){
        	
    		System.out.println("Enter the adjancies for "+vertexArray[i].name);
    		Edge[] edges = new Edge[iNumberOfVertices];
    		for(int j=0;j<iNumberOfVertices;j++){
       			int iEdgesDistance = scan.nextInt();
       			int iNoOfEdges = 0;
       			double dNewDistanceToEmpty = dDistanceToEmpty;
       			
       			if(mpFuelStationVertices.containsKey(i)){
       				dNewDistanceToEmpty = dDrivingRange;
       			}
       			if(iEdgesDistance>0 && (dDrivingRange >= iEdgesDistance) && (dNewDistanceToEmpty >= iEdgesDistance)){
       				iNoOfEdges++;
       				edges[j] = new Edge(vertexArray[j],iEdgesDistance);
       			}else{
       				edges[j] = new Edge(vertexArray[j],Double.POSITIVE_INFINITY);
       			}
       		}
    		vertexArray[i].adjacencies = edges;
       	}
    	
    	System.out.println("Enter the Source Vertex values 0 to " + String.valueOf(iNumberOfVertices-1));
    	int iSource = scan.nextInt();
    	
    	Vertex sVertex = vertexArray[iSource];
        computePaths(sVertex);
        
        System.out.println("Enter the Destination Vertex values 0 to " + String.valueOf(iNumberOfVertices-1));
    	int iDestination = scan.nextInt();
    	
    	System.out.println("==============================================================================================");
    	System.out.println("===========================  P R I N T I N G    R E S U L T      N O W  ======================");
    	System.out.println("==============================================================================================");
        for (Vertex v : vertexArray){
	    	if(vertexArray[iDestination].name.equals(v.name)){
	    		if(v.minDistance == Double.POSITIVE_INFINITY){
	    			System.err.println("You cannot travel from Source to Destination");
	    		}
			    System.out.println("Distance to " + v + ": " + v.minDistance);
			    List<Vertex> path = getShortestPathTo(v);
			    System.out.println("Path: " + path);
	    	}
		}
    }
}
