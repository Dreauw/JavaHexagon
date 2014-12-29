package view;

import java.awt.Color;
import java.awt.Graphics2D;

import model.Constants;
import model.Model;

public class HexagonalRenderer implements Renderer {
	private RowRenderer[] rowRenderers;
	
	public HexagonalRenderer(Model model) {
		rowRenderers = new RowRenderer[model.getRows().length];
		float angle = 360f / rowRenderers.length;
		for (int i = 0; i < rowRenderers.length; ++i) {
			rowRenderers[i] = new RowRenderer(model, model.getRows()[i], (int) Math.ceil(angle*i));
		}
	}
	
	@Override
	public void update(long delta) {
		for (RowRenderer rowRenderer : rowRenderers) {
			rowRenderer.rotate(Constants.ROTATION_SPEED * delta);
		}
	}

	@Override
	public void draw(Graphics2D g) {
		Color c = Constants.COLOR;
		for (int i = 0; i < rowRenderers.length; ++i) {
			rowRenderers[i].draw((Graphics2D) g, c, i%2 == 0);
		}
	}

}
