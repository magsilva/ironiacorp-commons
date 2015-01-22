
/**
 * Lazy approach to implement an algorithm for Dynamic Connectivity.
 * 
 * id[i] is parent of i
 * root of i is id[id[id[...id[i]...]]]
 *
 */
public class WeightedfQuickUnionUF implements DynamicConnectivity
{
	private int[] id;
	
	private int[] weights;
	
	public WeightedfQuickUnionUF(int n)
	{
		id = new int[n];
		weights = new int[n];
		for (int i = 0;i < id.length; i++) {
			id[i] = i;
			weights[i] = 1;
		}
	}
	
	private int root(int i)
	{
		while (i != id[i]) {
			id[i] = id[id[i]]; // Path compression (make every other node in path point to is grandparent)
			i = id[i];
		}
		return i;
	}
	
	@Override
	public boolean connected(int p, int q)
	{
		return root(p) == root(q);
	}
	
	@Override
	public void union(int p, int q)
	{
		int rootP, rootQ;
		rootP = root(p);
		rootQ = root(q);
		if (weights[rootP] < weights[rootQ]) {
			id[rootP] = rootQ;
			weights[rootQ] += weights[rootP];
		} else {
			id[rootQ] = rootP;
			weights[rootP] += weights[rootQ];
		}
	}

}
