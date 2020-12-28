package api;

import java.io.Serializable;
//import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONObject;

//import com.google.gson.annotations.SerializedName;


public class DWGraph_DS implements directed_weighted_graph,Serializable  {
	
	private int mc;
	private int edge_size;
	HashMap<Integer,node_data> graph;
	

	HashMap<Integer, HashMap<Integer,edge_data>> neighbors;
	
	public void M() {
		JSONObject obj_JsonObject = new JSONObject();

	}

	public DWGraph_DS() {
		 graph=new HashMap<Integer,node_data>();
		 neighbors=new HashMap<Integer,HashMap<Integer,edge_data>>();
	}
	
	@Override
	public node_data getNode(int key) {
		if(key==0) {
			for (node_data node:this.graph.values()) {
				if(node.getKey()==key) {
					return node;
				}
			}
		} 
		else if(this.graph.containsKey(key)) {
			return this.graph.get(key);
		}
		return null;
	}

	@Override
	public edge_data getEdge(int src, int dest) {
		if(this.getNode(src)==null||this.getNode(dest)==null) {
			return null;
		}
		if(!this.graph.containsKey(src)||!this.graph.containsKey(dest)) {
			return null;
		}
		if(this.neighbors.containsKey(src)==true) {
		if(this.neighbors.get(src).get(dest)!=null) {
		return this.neighbors.get(src).get(dest);
		}
		}
		return null;
	 }
//add the to graph
	@Override
	public void addNode(node_data n) {
	  if(!this.graph.containsKey(n.getKey())) {
		  n.setTag(n.getKey());
		  graph.put(n.getKey(), n);
		  mc++;
		  }

	}
	@Override
	public void connect(int src, int dest, double w) {
		//if is in the graph and have the age

		boolean connect=true;
		//check if the nodes src and dest exist then check if then check if connect
		 if(getEdge(src,dest)!=null&&this.getNode(src)!=null&&this.getNode(dest)!=null) {

				Edge n = new Edge(src, dest, w);
				this.neighbors.get(src).replace(dest, n);
				connect = false;

		 }
	//if is dosent connect
		 if(getEdge(src,dest)==null&&this.getNode(src)!=null&&this.getNode(dest)!=null) {
			 Edge z=new Edge(src,dest,w);
			 HashMap<Integer,edge_data>nebors_sec=new HashMap<Integer,edge_data>();	

			 nebors_sec.putIfAbsent(dest, z);
			 HashMap<Integer,edge_data>nebors_dest=new HashMap<Integer,edge_data>();

		     nebors_dest.putIfAbsent(src, z);
			 

					if(this.neighbors.containsKey(src)==false&&this.neighbors.get(src)!=null){
						this.neighbors.put(src, nebors_dest);
					}
					if(this.neighbors.get(src)==null) {
						this.neighbors.put(src, nebors_sec);
					}

				//if node1 is exist and node 2 not exist we need to put them if not the input will be nullexption
				if(this.neighbors.containsKey(src)==true) {
					this.neighbors.get(src).put(dest, z);

					//if bouth noe exist we need to put them 
				}else if(this.neighbors.containsKey(src)==false&&this.neighbors.containsKey(dest)==false) {

					this.neighbors.put(src, nebors_sec);
				}
		 }
		 mc++;
		 if(connect==true) {
		 edge_size++; 
		 }
	}

	@Override
	public Collection<node_data> getV() {
		return this.graph.values();
	}
	
//return all the edge_data  collection of the node id
	@Override
	public Collection<edge_data> getE(int node_id) {
		if(!this.graph.containsKey(node_id)) {
			return null;	
			}
		if(this.neighbors.containsKey(node_id)==false) {
			return null;
		}
		//this for to add the edge and to return the collection
			List<edge_data>f=new LinkedList<edge_data>();
			    for (int z:this.neighbors.get(node_id).keySet()) {
					f.add(this.getEdge(node_id,z));
				}  
			    return f;
			}

	@Override
	public node_data removeNode(int key) {
		if(!this.graph.containsKey(key)) {
			return null;
		}
		for (Integer node:this.neighbors.keySet()) {
			if(this.getEdge(node, key)!=null) {
			this.neighbors.get(key).remove(node,this.getEdge(key, node));
			this.neighbors.get(node).remove(key,this.getEdge(node, key));
			}
		}
		this.neighbors.remove(key);
		mc++;
	   return this.graph.remove(key);
	}
//remove for the neighbors and then remove the neighbors from him
	@Override
	public edge_data removeEdge(int src, int dest) {
		//check if Edges do not contains src and dest remove the edge between them.
		if(this.neighbors.containsKey(src) && this.neighbors.containsKey(dest)) {
			this.neighbors.get(src).remove(dest);
			//this.neighbors.get(dest).remove(src);
			edge_size--;
			mc++;
			return this.neighbors.get(src).get(dest);
		}
		return null;
	}

	@Override
	public int nodeSize() {
		return this.graph.size();
	}

	@Override
	public int edgeSize() {
		return edge_size;
	}

	@Override
	public int getMC() {
		return mc;
	}

	@Override
	public String toString() {
		return "DWGraph_DS [graph=" + graph + "]";
	}


}
