package model;

import java.util.Iterator;
import java.util.LinkedList;


public class Row {
	private final static int OBSTACLE_HEIGHT = 20;
	private LinkedList<Shape> obstacles;
	private int x, y, width, height;
	private long timer;
	private float previousInc;
	
	
	public Row(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		obstacles = new LinkedList<Shape>();
		timer = (long) (Constants.MIN_OBS_RELOAD_TIME + Math.random() * (Constants.MAX_OBS_RELOAD_TIME - Constants.MIN_OBS_RELOAD_TIME));
	}
	
	public synchronized void update(long delta) {
		timer -= delta;
		if (timer < 0) {
			timer = (long) (Constants.MIN_OBS_RELOAD_TIME + Math.random() * (Constants.MAX_OBS_RELOAD_TIME - Constants.MIN_OBS_RELOAD_TIME));
			addObstacle();
		}
		float inc = (Constants.OBSTACLE_SPEED * delta) + previousInc;
		int rx = 0;
		if (inc > 1) {
			rx = (int) inc;
			previousInc = inc - rx; 
		} else {
			previousInc = inc;
		}
		
		// Make the obstacle "fall"
		Iterator<Shape> it = obstacles.iterator();
		while (it.hasNext()) {
			Shape obstacle = it.next();
			for (int i = 0; i < obstacle.getYPoints().length; ++i) {
				obstacle.getYPoints()[i] += rx;
				// Like that, the obstacle is flattened when he reach the end of the row
				obstacle.getYPoints()[i] = Math.min(obstacle.getYPoints()[i], getLineY());
			}
			
			if (obstacle.getYPoints()[0] >= getLineY()) {
				it.remove();
			}
		}
	}
	
	/**
	 * Add an obstacle out of the screen
	 */
	public synchronized void addObstacle() {
		int[] xPoints = new int[]{x, x + width, x + width, x};
		int[] yPoints = new int[]{y - OBSTACLE_HEIGHT, y - OBSTACLE_HEIGHT, y, y};
		obstacles.add(new Shape(xPoints, yPoints));
	}
	
	public synchronized LinkedList<Shape> getObstacles() {
		return obstacles;
	}
	
	/**
	 * Check if the point is in the row
	 * @param x2
	 * @return
	 */
	public boolean contain(float x2) {
		int xx = (int)x2;
		return xx >= x && xx < x + width;
	}

	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	/**
	 * Get the y position of the line that indicate the end of the row
	 * @return
	 */
	public int getLineY() {
		return (getY() + getHeight()) - 40;
	}

}
