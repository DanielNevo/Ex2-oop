package api;

public class Edge implements edge_data {
  

	private int Src;
    private int Dest;
    private double Weight;
    private String Info;
    private int Tag;
    
    public Edge(int src,int dest,double w) {
    	this.Src=src;
    	this.Dest=dest;
    	this.Weight=w;
    }
    
	@Override
	public int getSrc() {
		return this.Src;
	}

	@Override
	public int getDest() {
		return this.Dest;
	}

	@Override
	public double getWeight() {
		return this.Weight;
	}
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
//	  @Override
		public String toString() {
			return "Edge [Src=" + Src + ", Dest=" + Dest + ", Weight=" + Weight + ", Info=" + Info + ", Tag=" + Tag + "]";
		}

}
