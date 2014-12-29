package controller;

import model.Model;

public abstract class Controller {
	private Model model;
	
	public Controller(Model model) {
		this.model = model;
	}
	
	protected Model getModel() {
		return model;
	}
	
	abstract public void update(long delta);
}
