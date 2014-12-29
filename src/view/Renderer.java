package view;

import java.awt.Graphics2D;

public interface Renderer {
	public  void update(long delta);
	public  void draw(Graphics2D g);
}
