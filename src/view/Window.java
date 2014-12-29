package view;

import javax.swing.JFrame;

import model.Constants;

public class Window extends JFrame{
	private static final long serialVersionUID = 1L;
	
	public Window() {
		super("JavaHexagon");
		
		add(new Screen(Constants.WIDTH, Constants.HEIGHT));
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	
	public static void main(String[] args) {
		new Window();
	}

}
