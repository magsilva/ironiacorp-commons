package com.ironiacorp.math.matrix.solver;

public abstract class AbstractMatrix<T extends Number> implements Matrix<T>
{
	private final MatrixFactory<T> factory;
	
	public AbstractMatrix(MatrixFactory<T> factory)
	{
		this.factory = factory;
	}
	
	public Matrix<T> multiply(Matrix<T> matrix)
	{
		if (getCols() != matrix.getRows()) {
			throw new UnsupportedOperationException("The matrixes are incompatible");
		}
		
		Matrix<T> result = factory.createMatrix(getCols(), matrix.getRows());
		long m = getCols();
		for (long i = 0; i < getRows(); i++) {
			for (long j = 0; j < getCols(); j++) {
				double sum = 0;
				for (long k = 0; k < m; k++) {
					T value1 = get(i,  k);
					T value2 = matrix.get(k,  j);
					sum += value1.doubleValue() * value2.doubleValue()	;
				}
				result.set(i,  j, (T) new Double(sum));
			}
		}
		
		return result;
	}

	public Matrix<T> multiply(T scalar)
	{
		Matrix<T> result = factory.createMatrix(getRows(), getCols());
		for (long i = 0; i < getRows(); i++) {
			for (long j = 0; j < getCols(); j++) {
				T value = get(i,  j);
				result.set(i,  j, (T) new Double(value.doubleValue() * scalar.doubleValue()));
			}
		}
		
		return result;
	}

	public Matrix<T> divide(T scalar)
	{
		Matrix<T> result = factory.createMatrix(getRows(), getCols());
		for (long i = 0; i < getRows(); i++) {
			for (long j = 0; j < getCols(); j++) {
				T value = get(i,  j);
				result.set(i,  j, (T) new Double(value.doubleValue() / scalar.doubleValue()));
			}
		}
		
		return result;
	}


	public Matrix<T> sum(Matrix<T> matrix)
	{
		if (getRows() != matrix.getRows()) {
			throw new UnsupportedOperationException("The matrixes are incompatible (different number of rows)");
		}
		if (getCols() != matrix.getCols()) {
			throw new UnsupportedOperationException("The matrixes are incompatible (different number of columns)");
		}
		
		Matrix<T> result = factory.createMatrix(getRows(), getCols());
		for (long i = 0; i < getRows(); i++) {
			for (long j = 0; j < getCols(); j++) {
				T value1 = get(i,  j);
				T value2 = matrix.get(i,  j);
				result.set(i,  j, (T) new Double(value1.doubleValue() + value2.doubleValue()));
			}
		}
		
		return result;
	}

	public Matrix<T> subtract(Matrix<T> matrix)
	{
		if (getRows() != matrix.getRows()) {
			throw new UnsupportedOperationException("The matrixes are incompatible (different number of rows)");
		}
		if (getCols() != matrix.getCols()) {
			throw new UnsupportedOperationException("The matrixes are incompatible (different number of columns)");
		}
		
		Matrix<T> result = factory.createMatrix(getRows(), getCols());
		for (long i = 0; i < getRows(); i++) {
			for (long j = 0; j < getCols(); j++) {
				T value1 = get(i,  j);
				T value2 = matrix.get(i,  j);
				result.set(i,  j, (T) new Double(value1.doubleValue() - value2.doubleValue()));
			}
		}
		
		return result;
	}
	
	public Matrix<T> transpose(Matrix<T> matrix)
	{
		Matrix<T> result = factory.createMatrix(getRows(), getCols());
		for (long i = 0; i < getRows(); i++) {
			for (long j = 0; j < getCols(); j++) {
				result.set(j, i, get(i, j));
			}
		}
		
		return result;
	}
	
	public Matrix<T> pow(int e)
	{
		if (! isSquared()) {
			throw new UnsupportedOperationException("Matrix must be a square");
		}
		
		Matrix<T> result;
		try {
			result = (Matrix<T>) clone();
		} catch (CloneNotSupportedException cnse) {
			throw new UnsupportedOperationException(cnse);
		}
		
		if (e == 0) {
			Integer identifyValue = new Integer(1);
			Integer otherValue = new Integer(0);
			for (long i = 0; i < getRows(); i++) {
				for (long j = 0; j < getCols(); j++) {
					if (i == j) {
						result.set(i, i, (T) identifyValue);
					} else {
						result.set(i, j, (T) otherValue);
					}
				}
			}
		} else {
			for (int i = 1; i < e; i++) {
				result = result.multiply(this);
			}
		}
		
		return result;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		Matrix<T> result = factory.createMatrix(getRows(), getCols());
		for (long i = 0; i < getRows(); i++) {
			for (long j = 0; j < getCols(); j++) {
				result.set(i,  j, get(i, j));
			}
		}
		
		return result;
	}
	
	public boolean isSquared()
	{
		return (getRows() != getCols());
	}
	
	public Matrix<T> invert()
	{
		if (! isSquared()) {
			throw new UnsupportedOperationException("Cannot invert a non-square matrix");
		}
		
		Matrix<T> result = factory.createMatrix(getRows(), getCols());
		
		return result;
	}
}
