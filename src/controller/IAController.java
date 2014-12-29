package controller;

import model.Constants;
import model.Model;

public class IAController extends Controller {

	public IAController(Model model) {
		super(model);
	}
	
	@SuppressWarnings("unused")
	private void bestRowIA(long delta) {
		// Find the best row to be in (with the farest obstacle)
		int bestRow = 0;
		int actualRow = 0;
		for (int i = 0; i < getModel().getRows().length; ++i) {
			if (getModel().getRows()[i].contain(getModel().getPlayer().getX())) actualRow = i;
			
			if (getModel().getRows()[i].getObstacles().isEmpty()) {
				bestRow = i;
				break;
			}
			int challengerY = getModel().getRows()[i].getObstacles().getFirst().getYPoints()[2];
			int championY = getModel().getRows()[bestRow].getObstacles().getFirst().getYPoints()[2];
			if (challengerY < championY) bestRow = i;
		}
		
		// Go to the best row if we're not already in
		if (actualRow == bestRow) return;
		
		getModel().movePlayer((actualRow - bestRow < 0 ? Constants.PLAYER_SPEED : -Constants.PLAYER_SPEED)*delta, 0);
	}
	
	
	private void safestNeighbourIA(long delta) {
		// Find the row we're in
		int dir = 0, newRow = 0, actualRow = 0;
		int[] heights = new int[3];
		for (int i = 0; i < getModel().getRows().length; ++i) {
			if (getModel().getRows()[i].contain(getModel().getPlayer().getX())) newRow = actualRow = i;
		}
		
		// Find the safest neighbour (the one with the farest obstacle)
		for (int i = -1; i <= 1; ++i) {
			heights[i+1] = -1;
			int rowId = (actualRow + i) % getModel().getRows().length;
			
			if (rowId < 0) rowId = getModel().getRows().length - 1;
			
			if (getModel().getRows()[rowId].getObstacles().isEmpty()) {
				newRow = i;
				dir = i;
				break;
			}
			
			int challengerY = getModel().getRows()[rowId].getObstacles().getFirst().getYPoints()[2];
			heights[i+1] = challengerY;
			if (getModel().getRows()[newRow].getObstacles().isEmpty()) {
				dir = 0;
				break;
			}
			int championY = getModel().getRows()[newRow].getObstacles().getFirst().getYPoints()[2];
			
			if (challengerY < championY) {
				newRow = rowId;
				dir = i;
			}
		}
		/*
		// If there's obstacle in each neighbour
		if (heights[0] >= 20 && heights[1] >= 20 && heights[2] >= 20) {
			// Check if it's a trap
			if (Math.abs(heights[1] - heights[0]) < 25 && Math.abs(heights[2] - heights[1]) < 25) {
				System.out.println(Math.random() + "trap");
				dir = 1;
			}
		}*/
		
		
		getModel().movePlayer(dir*Constants.PLAYER_SPEED*delta, 0);
	}

	@Override
	public void update(long delta) {
		safestNeighbourIA(delta);
	}

}
