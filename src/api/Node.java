package api;

import java.util.HashMap;

public class Node implements node_data {

	private  double Dis;
	static int counter=0;
	private int key;
	private double Weight;
	private String Info;
	private geo_location location;
	private int Tag;
	HashMap<node_data,Integer>neighbors;//this for the neighbors of the vertix

	//constrctor
	public Node(geo_location p,int id) {
		this.location=p;
		this.key=id;
	}
	
	public Node() {
		 neighbors=new HashMap<node_data,Integer>();
		 this.key=counter;
		 counter++;
		}
	//constrctor with key
	public Node(int key) {
	 neighbors=new HashMap<node_data,Integer>();
	 this.key=key;
	}
	
	@Override
	public int getKey() {
		return key;
	}

	@Override
	public geo_location getLocation() {
		return this.location;
	}

	@Override
	public void setLocation(geo_location p) {
		this.location=p;
	}

	@Override
	public double getWeight() {
	return	this.Weight;
	}

	@Override
	public void setWeight(double w) {
		this.Weight=w;
	}

	@Override
	public String getInfo() {
        return this.Info;
	}

	@Override
	public void setInfo(String s) {
		this.Info=s;
		
	}

	@Override
	public int getTag() {
		return this.Tag;
	}

	@Override
	public void setTag(int t) {
	  this.Tag=t;
		
	}
	public void setDis(double dis) {
		this.Dis=dis;
	}
	public double getDis() {
		return this.Dis;
	}


	@Override
	public String toString() {
		return "Node{["+location + ", Tag=" + Tag+ "]}";		
	}
}
