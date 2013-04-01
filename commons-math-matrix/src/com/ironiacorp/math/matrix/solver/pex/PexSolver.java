package com.ironiacorp.math.matrix.solver.pex;

import com.ironiacorp.math.matrix.solver.Matrix;
import com.ironiacorp.math.matrix.solver.Solver;

public abstract class PexSolver<T extends Number> implements Solver<T>
{
	private Matrix<T> matrixA;

	private Matrix<T> matrixB;

	public PexSolver()
	{
	}

	/* (non-Javadoc)
	 * @see visualizer.util.solver.PexSolver.Solver#getMatrixA()
	 */
	@Override
	public Matrix<T> getMatrixA()
	{
		return matrixA;
	}
	
	/* (non-Javadoc)
	 * @see visualizer.util.solver.PexSolver.Solver#setMatrixA(visualizer.util.solver.Matrix)
	 */
	@Override
	public void setMatrixA(Matrix<T> matrix)
	{
	}

	/* (non-Javadoc)
	 * @see visualizer.util.solver.PexSolver.Solver#getMatrixB()
	 */
	@Override
	public Matrix<T> getMatrixB()
	{
		return matrixB;
	}
	
	/* (non-Javadoc)
	 * @see visualizer.util.solver.PexSolver.Solver#setMatrixB(visualizer.util.solver.Matrix)
	 */
	@Override
	public void setMatrixB(Matrix<T> matrix)
	{
	}
	
	/* (non-Javadoc)
	 * @see visualizer.util.solver.PexSolver.Solver#solve()
	 */
	@Override
	public Matrix<T> solve()
	{
		return null;
	}
}
