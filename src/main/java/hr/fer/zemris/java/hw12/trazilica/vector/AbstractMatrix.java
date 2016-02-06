package hr.fer.zemris.java.hw12.trazilica.vector;

import hr.fer.zemris.linearna.IMatrix;
import hr.fer.zemris.linearna.IVector;
import hr.fer.zemris.linearna.IncompatibleOperandException;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * Razred implementira sucelje IMatrix
 * @author jelena Drzaic
 *
 */
public abstract class AbstractMatrix implements IMatrix {

	@Override
	public abstract int getRowsCount();

	@Override
	public abstract int getColsCount();

	@Override
	public abstract double get(int row, int col);

	@Override
	public abstract IMatrix set(int row, int col, double value);

	@Override
	public abstract IMatrix copy();

	@Override
	public abstract IMatrix newInstance(int rows, int cols);
	
	@Override
	public IMatrix add(IMatrix other) {
		if(this.getRowsCount() != other.getRowsCount() || 
				this.getColsCount() != other.getColsCount()) {
			throw new IncompatibleOperandException();
		}
		int rows = this.getRowsCount();
		int cols = this.getColsCount();
		for(int row = 0; row < rows; ++row) {
			for(int col = 0; col < cols; ++col) {
				this.set(row, col, this.get(row, col) + other.get(row, col));
			}
		}
		return this;
	}

	@Override
	public IMatrix nAdd(IMatrix other) {
		return this.copy().add(other);
	}

	@Override
	public IMatrix sub(IMatrix other) {
		if(this.getRowsCount() != other.getRowsCount() || 
				this.getColsCount() != other.getColsCount()) {
			throw new IncompatibleOperandException();
		}
		int rows = this.getRowsCount();
		int cols = this.getColsCount();
		for(int row = 0; row < rows; ++row) {
			for(int col = 0; col < cols; ++col) {
				this.set(row, col, this.get(row, col) - other.get(row, col));
			}
		}
		return this;
	}

	@Override
	public IMatrix nSub(IMatrix other) {
		return this.copy().sub(other);
	}

	@Override
	public IMatrix nMultiply(IMatrix other) {
		if(this.getColsCount() != other.getRowsCount()) {
			throw new IncompatibleOperandException();
		}
		
		int nRows = this.getRowsCount();
		int nCols = other.getColsCount();
		IMatrix newM = this.newInstance(nRows, nCols);
		for(int row = 0; row < nRows; ++row) {
			for(int col = 0; col < nCols; ++col) {
				newM.set(row, col, 0);
				double sum = 0.0;
				for(int k = 0, dim = this.getColsCount(); k < dim; ++k) {
					sum += this.get(row, k) * other.get(k, col);
				}
				newM.set(row, col, sum);
			}
		}
		return newM;
	}
	
	@Override
	public double determinant() {
		if(this.getColsCount() != this.getRowsCount()) {
			throw new IncompatibleOperandException();
		}
		
		return determinant2(this.toArray(), this.getColsCount());
	}
	
	/**
	 * Metoda rekutzivno racuna determinantu matrice.
	 * @param mat matrica ciju determinantu racunamo
	 * @param dim dimenzija matrice
	 * @return determinanta matrice
	 */
	private double determinant2(double[][] mat, int dim) {
		
		
		double det = 0.0;
        if(dim == 1){
            det = mat[0][0];
        }
        else if (dim == 2){
            det = mat[1][1] * mat[0][0] - mat[0][1] * mat[1][0];
        }
        else {
            det = 0;
            for(int j1 = 0; j1 < dim; ++j1) {
                double[][] m = new double[dim-1][];
                for(int k = 0; k < dim - 1; ++k){
                    m[k] = new double[dim - 1];
                }
                for(int i = 1; i < dim; ++i){
                    int j2 = 0;
                    for(int j = 0;j < dim; j++){
                        if(j == j1)
                            continue;
                        m[i - 1][j2] = mat[i][j];
                        ++j2;
                    }
                }
                det += Math.pow(-1.0,1.0+j1+1.0) * mat[0][j1] * determinant2(m, dim - 1);
            }
        }
        return det;
	}

	@Override
	public IMatrix nInvert() {
		double det = determinant();
		int dim = getRowsCount();
		if (Math.abs(det) < 1e-8) {
			throw new IncompatibleOperandException();
		}
		IMatrix inverse = newInstance(dim, dim);
		for (int i = 0; i < dim; ++i) {
			for (int j = 0; j < dim; ++j) {
				inverse.set(i, j, subMatrix(j, i, true).determinant() / det * 
						    ((i + j) % 2 == 0 ? 1 : -1));
			}
		}
		return inverse;
	}

	@Override
	public double[][] toArray() {
		int rows = this.getRowsCount();
		int cols = this.getColsCount();
		double[][] m = new double[rows][cols];
		for(int i = 0; i < rows; ++i) {
			for(int j = 0; j < cols; ++j) {
				m[i][j] = this.get(i, j);
			}
		}
		return m;
	}

	@Override
	public IMatrix nScalarMultiply(double value) {
		return this.copy().scalarMultiply(value);
	}

	@Override
	public IMatrix scalarMultiply(double value) {
		for(int row = 0, rows = this.getRowsCount(); row < rows; ++row) {
			for(int col = 0, cols = this.getColsCount(); col < cols; ++col) {
				this.set(row, col, this.get(row, col) * value);
			}
		}
		return this;
	}

	@Override
	public IMatrix makeIdentity() {
		this.scalarMultiply(0.0);
		for(int i = 0; i < (this.getRowsCount() < this.getColsCount() ? 
				this.getRowsCount() : this.getColsCount()); ++i) {
			this.set(i, i, 1.0);
		}
		return this;
	}
	
	@Override
	public IMatrix nTranspose(boolean liveView) {
		if(liveView) {
			return new MatrixTransposeView(this);
		}
		return new MatrixTransposeView(this.copy());
	}

	@Override
	public IMatrix subMatrix(int row, int col, boolean liveView) {
		if (liveView) {
			return new MatrixSubMatrixView(this, row, col);
		} else {
			return new MatrixSubMatrixView(this.copy(), row, col);
		}
	}

	@Override
	public IVector toVector(boolean liveView) {
		if(liveView) {
			return new VectorMatrixView(this);
		}else {
			return this.copy().toVector(true);
		}
	}
	
	/**
	 * Metoda vraca matricu u obliku stringa, s elementima zaokruzenima na 3 decimale.
	 */
	@Override
	public String toString() {
		return toString(3);
	}
	
	/**
	 * metoda vraca matricu u obliku stringa, s elementima zaokruzenim na n decimala.
	 * @param decimals broj decimala na koje zaokruzujemo elemente matrice. 
	 * @return string reprezentacija matrice.
	 */
	public String toString(int decimals) {
		StringBuilder sb = new StringBuilder();
		MathContext mc = new MathContext(decimals + 1, RoundingMode.HALF_UP);
		BigDecimal bd;
		for(int i = 0; i < getRowsCount(); ++i) {
			for(int j = 0; j < getColsCount(); ++j) {
				bd = new BigDecimal(this.get(i, j));
				sb.append(bd.round(mc).toString());
				if (j + 1 < getColsCount()) {
					sb.append(" ");
				}
			}
			if (i + 1 < getColsCount()) {
				sb.append("\n");
			}
		}
		return sb.toString();
	}
}
