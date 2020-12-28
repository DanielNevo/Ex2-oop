package api;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;


public class DWGraph_Algo implements dw_graph_algorithms,Serializable {
	private directed_weighted_graph graph;
	
	public DWGraph_Algo() {

	}

	public DWGraph_Algo(DWGraph_DS d) {
		this.graph=d;
	}
	@Override
	public void init(directed_weighted_graph g) {
		this.graph= g;		
	}

	@Override
	public directed_weighted_graph getGraph() {
		return this.graph;
	}

	@Override
	public directed_weighted_graph copy() {
		directed_weighted_graph ga = new DWGraph_DS();
		//copy all the nodes in to the new graph
		for (node_data courent : this.graph.getV()) {
			ga.addNode(courent);
		}
		// copy all the edges in to the new graph
		for (node_data courent : graph.getV()) {
			for (edge_data runner : this.graph.getE(courent.getKey())) {
				if(this.graph.getEdge(courent.getKey(), runner.getDest())==null) {
					double t= runner.getWeight();
					ga.connect(courent.getKey(),runner.getDest(), t);
				}
			}
		}
		return ga;
	}

	private boolean dfs(node_data p) {

		int sum=0;
		if(this.graph.getNode(0)==null) {
			sum=1;
		}
		boolean[] visited=new boolean [this.graph.nodeSize()+sum];
		if(sum==1) {
			visited[0]=true;
		}
		return inner_dfs(p,visited,sum);
	}
	private boolean inner_dfs(node_data p,boolean[] visited,int sum) {
		visited[p.getKey()]=true;
		
		   if(this.graph.getE(p.getKey())==null) {
			   return false;
		   }
		
		for (edge_data node:this.graph.getE(p.getKey())) {
			
			if(visited[node.getDest()]==false) {
				visited[node.getDest()]=true;
				inner_dfs(this.graph.getNode(node.getDest()),visited,sum);
			}
		}
		if(sum==1) {
			for (int i = 1; i < visited.length; i++) {
				if(visited[i]==false) {
					return false;
				}
			}
		}else {
			
		for (int i = 0; i < visited.length; i++) {
			if(visited[i]==false) {
				return false;
			}
		}
		}
		return true;
	}
	private boolean bfs(node_data p) {
		int sum=0;
		if(this.graph.getNode(0)==null) {
			sum=1;
		}
		boolean[] visited=new boolean [this.graph.nodeSize()+sum];
		visited[p.getKey()]=true;
		Queue<node_data> q = new LinkedList<node_data>();
		q.add(p);		

		while (!q.isEmpty()) {
			node_data u = q.poll();
			 if(this.graph.getE(u.getKey())==null) {
				   return false;
			   }
			
			for (edge_data node:this.graph.getE(u.getKey())) {
				if(visited[node.getDest()]==false) {
					visited[node.getDest()]=true;
					q.add(this.graph.getNode(node.getDest()));
				}
			}
		}
		
		//check the visited
		if(sum==1) {
			for (int i = 1; i < visited.length; i++) {
				if(visited[i]==false) {
					return false;
				}
			}
		}
		else {
			for (int i = 0; i < visited.length; i++) {
				if(visited[i]==false) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public boolean isConnected() {
		if(this.graph.nodeSize()==0||this.graph.nodeSize()==1) {//if we dont have any age or if we have one node
			return true;
		}
		boolean flag_bfs=false;
		boolean flag_dfs=false;
		node_data p=null;
		for (node_data node:this.graph.getV()) {
			p=node;
	      break;
		}		
		flag_bfs=bfs(p);
		flag_dfs=dfs(p);
			if(flag_bfs==false||flag_dfs==false) {
				return false;
			}

		return true;
	}


	@Override
	public double shortestPathDist(int src, int dest) {

		node_data a ,b;
		a=this.graph.getNode(src);
		b=this.graph.getNode(dest);
		if((a==null)||(b==null)) {
			return -1;
		}
		// PriorityQueue the gives a priority to the lower Dis to be first
		//compare override function
		Queue<node_data> q = new PriorityQueue<node_data>(new Comparator<node_data>() {
			@Override
			public int compare(node_data a, node_data b) {
				// compare if the Dis is smaller he will get much more priority
				Node A= (Node) a;
				Node B= (Node) b;
				if (A.getDis() < B.getDis()) {
					return -1;
				}

				else if (A.getDis() > B.getDis()) {
					return 1;
				}
				return 0;
			}
		});
		node_data s = this.graph.getNode(src);
		HashMap<Integer, Double> d = new HashMap<>(); // save the dis of all the nodes
		HashMap<Integer, node_data> p = new HashMap<>();// save all the Parent of all the nodes
		initDijkstra(d,q);

		d.replace(s.getKey(), 0.0);
		p.put(s.getKey(), null);
		((Node)s).setTag(0);
		while (q.size() != 0) {
			// remove a vertex from queue
			s = q.poll();
			// Get all adjacent vertices of the dequeued vertex s
			node_data temp = s;
			if(this.graph.getE(temp.getKey())==null){
				continue;
			}
			//Running on all the edges of the current node
			for (edge_data courent : this.graph.getE(s.getKey())) {
				//Checking if the wight of the node + the edge wight from the node to is neighbor is smaller
				// If so replace the wight and put in the Hashmap d
				double dis = d.get(courent.getSrc()) + this.graph.getEdge(courent.getSrc(),courent.getDest()).getWeight();
				if(dis<d.get(courent.getDest())){
					((Node) this.graph.getNode(courent.getDest())).setDis(dis);
					d.put(courent.getDest(), dis);
					p.put(courent.getDest(), s);
					q.add(this.graph.getNode(courent.getDest()));
				}
			}

		}
		//Return the shortestPathDis
		return d.get(dest);
	}

	//init all the nodes tag and insert to the queue
	void initDijkstra(HashMap a ,Queue b){
		for (node_data curr : this.graph.getV()) {
			((Node)curr).setDis(Double.MAX_VALUE);
			a.put(curr.getKey(), Double.MAX_VALUE);
			b.add(curr);
		}
	}

	@Override
	public List<node_data> shortestPath(int src, int dest) {

		node_data a ,b;
		a=this.graph.getNode(src);
		b=this.graph.getNode(dest);
		if((a==null)||(b==null)) {
			return null;
		}
		// PriorityQueue the gives a priority to the lower Dis to be first
		//compare override function
		Queue<node_data> q = new PriorityQueue<node_data>(new Comparator<node_data>() {
			@Override
			public int compare(node_data a, node_data b) {
				// compare if the Dis is smaller he will get much more priority
				Node A= (Node) a;
				Node B= (Node) b;
				if (A.getDis() < B.getDis()) {
					return -1;
				}

				else if (A.getDis() > B.getDis()) {
					return 1;
				}
				return 0;
			}
		});
		node_data s = this.graph.getNode(src);
		HashMap<Integer, Double> d = new HashMap<>(); // save the dis of all the nodes
		HashMap<Integer, node_data> p = new HashMap<>();// save all the Parent of all the nodes
		initDijkstra(d,q);

		d.replace(s.getKey(), 0.0);
		p.put(s.getKey(), null);
		((Node)s).setTag(0);
		while (q.size() != 0) {
			// remove a vertex from queue
			s = q.poll();
			// Get all adjacent vertices of the dequeued vertex s
			node_data temp = s;
			if(this.graph.getE(temp.getKey())==null){
				continue;
			}
			//Running on all the edges of the current node
			for (edge_data courent : this.graph.getE(s.getKey())) {
				//Checking if the wight of the node + the edge wight from the node to is neighbor is smaller
				// If so replace the wight and put in the Hashmap d
				double dis = d.get(courent.getSrc()) + this.graph.getEdge(courent.getSrc(),courent.getDest()).getWeight();
				if(dis<d.get(courent.getDest())){
					((Node) this.graph.getNode(courent.getDest())).setDis(dis);
					d.put(courent.getDest(), dis);
					p.put(courent.getDest(), s);
					q.add(this.graph.getNode(courent.getDest()));
				}
			}

		}
		LinkedList<node_data> list = new LinkedList<node_data>();//the list to return
		//Add into new list the shortest Path between src to dest
		node_data courent =this.graph.getNode(dest);
		while(courent!=null){
			list.addFirst(this.graph.getNode(courent.getKey()));
			courent = p.get(courent.getKey());
		}
		//Return list of the shortestPath
		return list;


	}



@Override
	public boolean save(String file) throws JSONException {
		//graph
		JSONObject g = new JSONObject();

		JSONArray nodes = new JSONArray();
		JSONArray edges = new JSONArray();

		for (node_data current : getGraph().getV()) {

			JSONObject node = new JSONObject();
			if (current.getLocation() != null) {
				node.put("pos" ,current.getLocation().toString());
			}
			else {
				node.put("pos", "0.0,0.0,0.0");
				node.put("id", current.getKey());
				nodes.put(node);
			}
			for (edge_data ni : getGraph().getE(current.getKey())) {

				JSONObject edge = new JSONObject();
				edge.put("src", ni.getSrc());
				edge.put("w", ni.getWeight());
				edge.put("dest", ni.getDest());

				edges.put(edge);

			}
		}
		try {
			FileWriter fw = new FileWriter(file);

			g.put("Edges", edges);
			g.put("Nodes", nodes);
			fw.write(g.toString());
			fw.flush();
		} catch (IOException ex) {
			return false;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override

    public boolean load(String file) {
        directed_weighted_graph g = new DWGraph_DS();
        try {

            JSONObject obj_JsonObject = new JSONObject(file);
            JSONArray jsonArrayEdges = obj_JsonObject.getJSONArray("Edges"); // Array  vertexes
            JSONArray jsonArrayNodes = obj_JsonObject.getJSONArray("Nodes");// Array  edges
            for (int i = 0; i < jsonArrayNodes.length(); i++) { // put vertexes in position
                JSONObject JSON_Node = jsonArrayNodes.getJSONObject(i);
                String pos = JSON_Node.getString("pos");// to string String
                int id = JSON_Node.getInt("id"); //  node ID
                geo_location p = new Geo(pos); // give the location  x and y and z
                node_data n = new Node(p, id);
                g.addNode(n); // Add  vertex
            }
            for (int i = 0; i < jsonArrayEdges.length(); i++) { // add vertexes by edge
                JSONObject JSON_Edge = jsonArrayEdges.getJSONObject(i);
                int src = JSON_Edge.getInt("src"); //  src
                int dest = JSON_Edge.getInt("dest"); // dest
                double w = JSON_Edge.getDouble("w");
                g.connect(src, dest, w);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        this.init(g);
        return true;
    }
}
