package hr.fer.zemris.java.hw12.trazilica.vector;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import hr.fer.zemris.linearna.IMatrix;
import hr.fer.zemris.linearna.IVector;
import hr.fer.zemris.linearna.IncompatibleOperandException;
import hr.fer.zemris.linearna.UnmodifiableObjectException;


/**
 * Razred implementira sucelje IVector
 * @author jelena Drzaic
 *
 */
public abstract class AbstractVector implements IVector{

	@Override
	public IVector add(IVector other) throws IncompatibleOperandException {

		if(this.getDimension() != other.getDimension()) {
			throw new IncompatibleOperandException();
		}
		for(int i = this.getDimension() - 1; i >= 0; i--) {
			this.set(i, this.get(i) + other.get(i));
		}
		return this;
	}

	@Override
	public abstract IVector copy();

	@Override
	public IVector copyPart(int dimension) {
		IVector newV = this.newInstance(dimension);
		int dim = this.getDimension() < dimension ?
			this.getDimension() : dimension;
		for(int i = 0; i < dim; ++i) {
			newV.set(i,this.get(i));
		}
		if(dim < dimension) {
			for(int i = dim; i < dimension; ++i) {
				newV.set(i, 0.0);
			}
		}
		return newV;
	}

	@Override
	public double cosine(IVector other) throws IncompatibleOperandException {
		if(this.norm() == 0 || other.norm() == 0) {
			return 0.0;
		}
		return this.scalarProduct(other) / (this.norm() * other.norm());
	}

	@Override
	public abstract double get(int arg0);

	@Override
	public abstract int getDimension();
	
	@Override
	public IVector nAdd(IVector other) throws IncompatibleOperandException {
		return this.copy().add(other);
	}

	@Override
	public IVector nFromHomogeneus() {
		IVector newV = this.newInstance(this.getDimension() - 1);
		double coef = this.get(this.getDimension() - 1);
		if (Math.abs(coef) < 1e-10) {
			throw new IllegalArgumentException();
		}
		for(int i = 0, dim = newV.getDimension(); i < dim; ++i) {
			newV.set(i, this.get(i) / coef);
		}
		return newV;
	}

	@Override
	public IVector nNormalize() {
		return this.copy().normalize();
	}

	@Override
	public IVector nScalarMultiply(double alfa) {
		return this.copy().scalarMultiply(alfa);
	}

	@Override
	public IVector nSub(IVector other) throws IncompatibleOperandException {
		return this.copy().sub(other);
	}

	@Override
	public IVector nVectorProduct(IVector other)
			throws IncompatibleOperandException {
		if(this.getDimension() > 3 || other.getDimension() > 3) {
			throw new IncompatibleOperandException();
		}
		IVector tempThis = this.copyPart(3);
		IVector tempOther = other.copyPart(3);
		IVector newV = this.newInstance(3);
		newV.set(0, tempThis.get(1) * tempOther.get(2) - tempThis.get(2) * tempOther.get(1));
		newV.set(1, -tempThis.get(0) * tempOther.get(2) + tempThis.get(2) * tempOther.get(0));
		newV.set(2, tempThis.get(0) * tempOther.get(1) - tempThis.get(1) * tempOther.get(0));
		return newV;
	}

	@Override
	public abstract IVector newInstance(int arg0);

	@Override
	public double norm() {
		double norma = 0.0;
		for(int i = 0, dimension = this.getDimension(); i < dimension; ++i) {
			norma += this.get(i) * this.get(i);
		}
		norma = Math.sqrt(norma);
		return norma;
	}

	@Override
	public IVector normalize() {
		double norm = this.norm();
		if(norm == 0.0) {
			return this;
		}
		for(int i = 0, dimension = this.getDimension(); i < dimension; ++i) {
			this.set(i, this.get(i) / norm);
		}
		return this;
	}

	@Override
	public IVector scalarMultiply(double alfa) {
		for(int i = 0, dimension = this.getDimension(); i < dimension; ++i) {
			this.set(i, this.get(i) * alfa);
		}
		return this;
	}

	@Override
	public double scalarProduct(IVector other)
			throws IncompatibleOperandException {
		double scalProd = 0.0;
		if(this.getDimension() != other.getDimension()) {
			throw new IncompatibleOperandException();
		}
		for(int i = this.getDimension() - 1; i >= 0; i--) {
			scalProd += this.get(i) * other.get(i);
		}
		return scalProd;
	}

	@Override
	public abstract IVector set(int arg0, double arg1)
			throws UnmodifiableObjectException;

	@Override
	public IVector sub(IVector other) throws IncompatibleOperandException {
		if(this.getDimension() != other.getDimension()) {
			throw new IncompatibleOperandException();
		}
		for(int i = this.getDimension() - 1; i >= 0; i--) {
			this.set(i, this.get(i) - other.get(i));
		}
		return this;

	}

	@Override
	public double[] toArray() {
		int dimension = this.getDimension();
		double[] vector = new double[dimension];
		for(int i = 0; i < dimension; ++i) {
			vector[i] = this.get(i);
		}
		return vector;
	}
	
	/**
	 * Metoda vraca String reprezentaciju vektora s komponentama
	 * zaokruzenim na 3 decimale.
	 * @return string reprezentacija vektora
	 */
	public String toString() {
		return toString(3);
	}
	
	/**
	 * Metoda vraca String reprezentaciju vektora s komponentama
	 * zaokruzenim na n decimala.
	 * @param decimals broj decimala na koje zaokruzujemo
	 * @return string reprezentacija vektora
	 */
	public String toString(int decimals) {
		StringBuilder sb = new StringBuilder();
		MathContext mc = new MathContext(decimals + 1, RoundingMode.HALF_UP);
		BigDecimal bd;
		for(int i = 0, dim = this.getDimension(); i < dim; ++i) {
			bd = new BigDecimal(this.get(i));
			sb.append(bd.round(mc).toString());
			if (i + 1 < dim) {
				sb.append(" ");
			}
		}
		return sb.toString();
	}
	
	
	@Override
	public IMatrix toColumnMatrix(boolean live) {
		if(live) {
			return new MatrixVectorView(this, false);
		}else {
			return new MatrixVectorView(this.copy(), false);
		}
	}

	@Override
	public IMatrix toRowMatrix(boolean live) {
		if(live) {
			return new MatrixVectorView(this, true);
		}else {
			return new MatrixVectorView(this.copy(), true);
		}
	}
}
