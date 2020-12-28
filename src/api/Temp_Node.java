package api;

public class Temp_Node {
  private Temp_Node prev;
  private int key;
  private double Tag;
  private boolean visit;
  
  public Temp_Node(int key) {
	 this.key=key;
  }
 

 public Temp_Node addnode(node_data k) {
	 if(k.getKey()==0) {
		 Temp_Node n=new Temp_Node(0);
		 n.setKey(0);
		 return n;
	 }
	 Temp_Node z=new Temp_Node(k);
	 z.setKey(k.getKey());
	 return z;
 }
  
public Temp_Node getPrev() {
	return prev;
}


public void setPrev(Temp_Node prev) {
	this.prev = prev;
}

public boolean isVisit() {
	return visit;
}

public void setVisit(boolean visit) {
	this.visit = visit;
}

public Temp_Node(node_data k) {
	  this.key=k.getKey();
	  this.prev=null;
  }
  
  public int getKey() {
	return key;
}

public void setKey(int key) {
	this.key = key;
}

public double getTag() {
	return Tag;
}

public void setTag(double tag) {
	this.Tag = tag;
}


}
