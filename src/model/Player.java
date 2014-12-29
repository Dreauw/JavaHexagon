package model;

public class Player {
	private float x, y;
	private int size;
	
	
	public Player(float x, float y, int size) {
		this.setX(x);
		this.setY(y);
		this.setSize(size);
	}


	public float getX() {
		return x;
	}


	public void setX(float x) {
		this.x = x;
	}


	public float getY() {
		return y;
	}


	public void setY(float y) {
		this.y = y;
	}


	public int getSize() {
		return size;
	}


	public void setSize(int size) {
		this.size = size;
	}
	
	

}
