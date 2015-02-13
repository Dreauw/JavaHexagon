package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

import controller.Controller;
import controller.IAController;
import controller.KeysController;
import model.Constants;
import model.Model;

public class Screen extends JPanel {
	private static final long serialVersionUID = 1L;
	private Model model;
	private Controller controller;
	private Renderer renderer;
	
	
	public Screen(int width, int height) {
		super();
		
		width -= width%Constants.NB_ROW;
		
		model = new Model(width, height);
		
		renderer = new HexagonalRenderer(model);
		
		setFocusable(true);
		setPreferredSize(new Dimension(width, height));
		
		final KeysController c = new KeysController(model);
		
		addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {}
			
			@Override
			public void keyReleased(KeyEvent e) {
				c.onKeyReleased(e);
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				c.onKeyPressed(e);
			}
		});
		
		controller = Constants.PLAYED_BY_IA ? new IAController(model) : c;
		
		updateLoop();
	}
	
	
	public void updateLoop() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				long lastUpdate = System.currentTimeMillis();
				while (true) {
					long current = System.currentTimeMillis();
					long delta = current - lastUpdate;
					lastUpdate = current;
					update(delta);
					repaint();
					Toolkit.getDefaultToolkit().sync();
					try {
						long waitTime = (long) ((1f/Constants.FPS)*1000 - delta);
						if (waitTime > 0) Thread.sleep(waitTime);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
		
		setBackground(Constants.COLOR.darker().darker().darker().darker());
	}
	
	@Override
	public synchronized void paintComponent(Graphics g) {
		super.paintComponent(g);
		renderer.draw((Graphics2D) g);
	}
	
	public synchronized void update(long delta) {
		controller.update(delta);
		model.update(delta);
		renderer.update(delta);
	}

}
