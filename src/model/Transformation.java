package model;

public class Transformation {
	
	public static Matrix getTranslationMatrix(float x, float y) {
		return new Matrix(new float[][]{{1, 0, x},
										{0, 1, y},
										{0, 0, 1}});
	}
	
	public static Matrix getRotationMatrix(float angle) {
		angle = (float) Math.toRadians(angle);
		float cos = (float) Math.cos(angle);
		float sin = (float) Math.sin(angle);
		return new Matrix(new float[][]{{cos, -sin, 0},
										{sin, cos,  0},
										{0,   0,    1}});
	}
	
	public static Matrix getShearXMatrix(float k) {
		return new Matrix(new float[][]{{1, k, 0},
										{0, 1, 0},
										{0, 0, 1}});
	}
	
	public static Matrix getShearYMatrix(float k) {
		return new Matrix(new float[][]{{1, 0, 0},
										{k, 1, 0},
										{0, 0, 1}});
	}
}
