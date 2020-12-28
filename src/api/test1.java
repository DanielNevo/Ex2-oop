package api;

//import java.util.Arrays;
import java.util.HashMap;

import org.json.JSONObject;

import com.google.gson.Gson;




public class test1 {

	public static void main(String[] args) {
//		DWGraph_DS g=new DWGraph_DS();
////		 System.out.println(w);
//		 Node a=new  Node();
//		 Node b=new  Node();
//		 Node c=new  Node();
//			g.addNode(a);
//			//System.out.println(g.toString());
//			g.addNode(b);
//		//	System.out.println(g.toString());
//		g.addNode(c);
//	//	System.out.println(g.toString());
//		g.connect(a.getKey(), b.getKey(), 5.6);
//			//System.out.println(g.toString());
//			edge_data temp= g.getEdge(a.getKey(),b.getKey());
		//	System.out.println(temp.getWeight());
		//	System.out.println(a);
//		HashMap<Integer,String>hey=new HashMap<Integer,String>();
//		HashMap<Integer,HashMap<Integer,String>>hey2=new HashMap<Integer,HashMap<Integer,String>>();
//		hey.put(0, "�����");
//		hey2.put(0, hey);
////		System.out.println( hey2.get(2));
////		System.out.println(hey2.clone());
////		System.out.println(hey.values());
		DWGraph_DS w=new DWGraph_DS();
	    Node n=new Node(0);
	    Node f=new Node(1);
	    Node z=new Node(2);
	    Node v=new Node(3);
//	    Node k=new Node(4);
//	    Node m=new Node(5);
//	    Node y=new Node(6);
		w.addNode(n);//0
		w.addNode(f);//1
		w.addNode(z);//2
		w.addNode(v);//3

		w.connect(0,1,3);

		w.connect(1,2,2);

		w.connect(0,3,0);

		System.out.println(w.edgeSize());
		w.removeEdge(0,3);
		w.removeEdge(1,2);
		w.removeEdge(0,1);
		System.out.println(w.edgeSize());




//		w.addNode(v);
//		w.addNode(k);
//		w.addNode(m);
//		w.addNode(y);
//		//------
//	//	isconnected
//		w.connect(0,1,2);
//		w.connect(0,2,2);
//		w.connect(1,2,2);
//		w.connect(2,0,2);
//		w.connect(2,3,2);
//		
//	//	------
//		//dijxtra
//		w.connect(0,1,3);
//		w.connect(0,3,10);
//		w.connect(0,2,2);
//		w.connect(1,6,2);
//		w.connect(6,3,1);
//    	w.connect(2,4,12);
//		w.connect(2,5,4);
//		w.connect(5,4,4);
//		w.connect(5,3,8);
//	///	--------
////		w.connect(1,2,2);
////		w.connect(1,3,4);
////		w.connect(2,3,1);
////		w.connect(2,4,7);
////		w.connect(3,5,3);
////		w.connect(5,4,2);
////		w.connect(4,6,1);
////		w.connect(5,6,5);
//		
//		
//	//	System.out.println(w.neighbors.clone());
//	//	w.addNode(z);
//	 //   w.addNode(v);
//	//	w.addNode(k);
//		//w.addNode(m);
//		//System.out.println(w.getNode(2));
//		
//		//w.addNode(f);
////		System.out.println(w.getNode(n.getKey()));
//	//	w.addNode(f);
//	//	System.out.println(w.graph.clone());
////	System.out.println(w.getEdge(2,3));
// 
////	w.connect(0,1,2.2);
////	w.connect(1,0,2.3);
////	w.connect(0,2,3.3);
//	//w.connect(1, 3, 2);
//
////	w.connect(3,2, 2.1);
//	//w.connect(3,2,2.2);
////	w.connect(3,1,2);
//	
////	System.out.println(w.nodeSize());
////	System.out.println(w.neighbors.clone());
//	
//	//System.out.println(w.neighbors.clone());
//	
	
//	DWGraph_Algo r=new DWGraph_Algo(w);

//	String jeson=gson.toJson(g);
//	System.out.println(jeson);
  // System.out.println( r.shortestPathDist(0,3));
   
//    r.loadfromString("A0.json");
//	//System.out.println(r.isConnected());
//	// System.out.println(r.shortestPath(0,3));
//     //System.out.println(r.shortestPath(1,6));
// //    System.out.println(r.isConnected());
// //    System.out.println(r.getGraph().getNode(0));
////	
////	System.out.println(r.shortestPath(0,3));
//		
	}
	

}
