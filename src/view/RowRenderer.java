package view;

import java.awt.Color;
import java.awt.Graphics2D;

import model.Constants;
import model.Matrix;
import model.Model;
import model.Player;
import model.Row;
import model.Shape;
import model.Transformation;

public class RowRenderer {
	private Model model;
	private Row row;
	private Matrix transformation;
	private float apoToRadius;
	
	public RowRenderer(Model model, Row row, int angle) {
		this.model = model;
		this.row = row;
		this.transformation = new Matrix(3, 3).getIdentity();
		this.apoToRadius = (float) (2 * Math.tan(Math.toRadians(180f/model.getRows().length)));
		// Translate the row to the center of the screen (center : (width/2, height))
		transformation = transformation.multiplyBy(Transformation.getTranslationMatrix(-row.getX() + model.getWidth()/2 - row.getWidth()/2, -row.getY() + row.getHeight()/2 - row.getHeight()));
		shear(Constants.SHEAR_X, Constants.SHEAR_Y);
		rotate(angle);
	}
	
	/**
	 * Apply a rotation matrix to the row
	 * @param l Angle of the rotation in degrees
	 */
	public void rotate(float l) {
		transformation = transformation.multiplyBy(Transformation.getTranslationMatrix(row.getX() + row.getWidth()/2, row.getY() + row.getHeight()));
		transformation = transformation.multiplyBy(Transformation.getRotationMatrix(l));
		transformation = transformation.multiplyBy(Transformation.getTranslationMatrix(-row.getX() - row.getWidth()/2, -row.getY() - row.getHeight()));
	}
	
	/**
	 * Apply shearing to the row
	 * @param shx shear in the x axis
	 * @param shy shear in the y axis
	 */
	public void shear(float shx, float shy) {
		transformation = transformation.multiplyBy(Transformation.getTranslationMatrix(row.getX() + row.getWidth()/2, row.getY() + row.getHeight()));
		transformation = transformation.multiplyBy(Transformation.getShearXMatrix(shx));
		transformation = transformation.multiplyBy(Transformation.getShearYMatrix(shy));
		transformation = transformation.multiplyBy(Transformation.getTranslationMatrix(-row.getX() - row.getWidth()/2, -row.getY() - row.getHeight()));
	}
	
	private int[][] applyTransformation(int[] xPoints, int[] yPoints) {
		int[] xpts = new int[xPoints.length];
		int[] ypts = new int[yPoints.length];
		for (int i = 0; i < xPoints.length; ++i) {
			Matrix pts = new Matrix(new float[][]{{xPoints[i]}, {yPoints[i]}, {1}});
			pts = transformation.multiplyBy(pts);
			xpts[i] = (int) pts.get(0, 0);
			ypts[i] = (int) pts.get(0, 1);
		}
		return new int[][]{xpts, ypts};
	}
	
	private int[] applyTransformation(int x, int y) {
		Matrix pts = new Matrix(new float[][]{{x}, {y}, {1}});
		pts = transformation.multiplyBy(pts);
		return new int[]{(int) pts.get(0, 0), (int) pts.get(0, 1)};
	}
	
	/**
	 * Calculate the radius of the hexagon given the apothem
	 * @param apothem
	 * @return
	 */
	private int getRadiusFromApothem(int apothem) {
		return (int) Math.ceil(apothem * apoToRadius);
	}
	
	public void draw(Graphics2D g, Color c, boolean hasBackground) {
		int rad = getRadiusFromApothem((row.getY() + row.getHeight()) - row.getLineY());
		//if (row.contain(model.getPlayer().getX())) g.setColor(Color.RED);
		
		if (hasBackground) {
			int width = getRadiusFromApothem(row.getHeight() - row.getY());
			int bx = row.getX() + row.getWidth()/2;
			int[] xPoints = new int[]{bx - width/2, bx + width/2, bx + rad/2, bx - rad/2};
			int[] yPoints = new int[]{row.getY(), row.getY(), row.getLineY(), row.getLineY()};
			int[][] pts = applyTransformation(xPoints, yPoints);
			g.setColor(c.darker().darker().darker());
			g.fillPolygon(pts[0], pts[1], pts[0].length);
		}
		
		g.setColor(c);
		
		// Apply the transformation matrix to0 the points
		for (Shape shape : row.getObstacles()) {
			int[] xPoints = shape.getXPoints().clone();
			int[] yPoints = shape.getYPoints().clone();
			// Resize the obstacle horizontally depending of the distance from the center
			float width = getRadiusFromApothem(Math.abs(row.getY() + row.getHeight() - yPoints[0]));
			xPoints[0] = (int) (row.getX() + row.getWidth() / 2 - width/2);
			xPoints[1] = (int) (row.getX() + row.getWidth() / 2 + width/2);
			width = getRadiusFromApothem(Math.abs(row.getY() + row.getHeight() - yPoints[2]));
			xPoints[2] = (int) (row.getX() + row.getWidth() / 2 + width/2);
			xPoints[3] = (int) (row.getX() + row.getWidth() / 2 - width/2);
			int[][] pts = applyTransformation(xPoints, yPoints);
			g.fillPolygon(pts[0], pts[1], pts[0].length);
		}
		
		// Draw the line corresponding to "the end" of the row
		int[] p1 = applyTransformation(row.getX() + row.getWidth()/2 - rad/2, row.getLineY());
		int[] p2 = applyTransformation(row.getX() + row.getWidth()/2 + rad/2, row.getLineY());
		g.drawLine(p1[0], p1[1], p2[0], p2[1]);
		
		// Draw the player if he is contained in this row
		Player p = model.getPlayer();
		if (row.contain(p.getX())) {
			// position of the player for the width of the row -> position for the radius of the hexagon
			int posx = (int) ((float)(p.getX())/row.getWidth() * rad);
			// Apply the transformation of the row to the player
			int[] pos = applyTransformation((row.getX() + (row.getWidth() - rad)/2 + posx%rad), (int)p.getY());
			g.drawLine(pos[0], pos[1], pos[0], pos[1]);
			g.drawRect(pos[0] - p.getSize()/2, pos[1] - p.getSize()/2, p.getSize(), p.getSize());
		}
	}

}
