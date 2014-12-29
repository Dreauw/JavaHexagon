package model;

public class Shape {
	private int[] xPoints;
	private int[] yPoints;
	
	
	public Shape(int[] xPoints, int[] yPoints) {
		this.xPoints = xPoints;
		this.yPoints = yPoints;
	}
	
	
	public int[] getXPoints() {
		return xPoints;
	}
	
	public int[] getYPoints() {
		return yPoints;
	}

}
