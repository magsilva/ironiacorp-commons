package com.ironiacorp.math.matrix.solver;

public interface Matrix<T extends Number> extends Cloneable
{
	T get(long rows, long cols);

	void set(long rows, long cols, T value);

	long getCols();

	long getRows();
	
	MatrixType getType();
	
	boolean isSquared();
	
	Matrix<T> multiply(Matrix<T> matrix);
	
	Matrix<T> multiply(T scalar);
	
	Matrix<T> divide(T scalar);
	
	Matrix<T> sum(Matrix<T> matrix);

	Matrix<T> subtract(Matrix<T> matrix);
	
	Matrix<T> transpose(Matrix<T> matrix);
	
	Matrix<T> pow(int e);
	
	Matrix<T> invert();

	Object getRealImplementation();
}
