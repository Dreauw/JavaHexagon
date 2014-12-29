package model;

public class Matrix {
	
	private float[][] values;

	public Matrix(int nbColumns, int nbRows) {
		values = new float[nbColumns][nbRows];
	}
	
	public Matrix(float[][] values) {
		this.values = values;
	}
	
	public Matrix getIdentity() {
		float iden[][] = new float[getNbRows()][getNbColumns()];
		for (int i = 0; i < iden.length; ++i) {
			iden[i][i] = 1;
		}
		return new Matrix(iden);
	}

	public int getNbColumns() {
		return values[0].length;
	}

	public int getNbRows() {
		return values.length;
	}
	
	public Matrix multiplyBy(Matrix m) {
		if (getNbColumns() != m.getNbRows()) return null;

		float[][] res = new float[getNbRows()][m.getNbColumns()];
		for (int j = 0; j < res.length; ++j) {
			for (int i = 0; i < res[0].length; ++i) {
				for (int k = 0; k < getNbColumns(); ++k) {
					res[j][i] += values[j][k] * m.values[k][i];
				}
			}
		}
		return new Matrix(res);
	}
	
	public float get(int x, int y) {
		return values[y][x];
	}
}
