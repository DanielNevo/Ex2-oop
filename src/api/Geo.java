package api;

public class Geo implements geo_location{
    private double x;
    private double y;
    private double z;
	private String name;
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Geo(String pos) {
		String [] temp=pos.split(",");
		this.x=Double.parseDouble(temp[0]);
		this.y=Double.parseDouble(temp[1]);
		this.z=Double.parseDouble(temp[2]);
		
	}
	public Geo(double x,double y,double z) {
		this.x=x;
		this.y=y;
		this.z=z;
	}

	


	@Override
	public String toString() {
		return "[x=" + x + ", y=" + y + ", z=" + z + "]}";
	}

	@Override
	public double x() {
		return this.x;
	}

	@Override
	public double y() {
	  return this.y;
	}

	@Override
	public double z() {
		return this.z;
	}

	@Override
	public double distance(geo_location g) {
		double d=0;
		d=Math.sqrt((this.x-g.x())*(this.x-g.x())+(this.y-g.y())*(this.y-g.y()));
		return d;
	}

}
