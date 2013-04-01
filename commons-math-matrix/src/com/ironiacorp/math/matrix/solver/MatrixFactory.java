package com.ironiacorp.math.matrix.solver;

public interface MatrixFactory<T extends Number>
{
	Matrix<T> createMatrix(long rows, long columns);

	Matrix<T> createIdentityMatrix(long size);
}
