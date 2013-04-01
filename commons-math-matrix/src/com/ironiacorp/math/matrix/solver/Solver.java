package com.ironiacorp.math.matrix.solver;

public interface Solver<T extends Number>
{
	Matrix<T> getMatrixA();

	void setMatrixA(Matrix<T> matrix);

	Matrix<T> getMatrixB();

	void setMatrixB(Matrix<T> matrix);

	Matrix<T> solve();
}
