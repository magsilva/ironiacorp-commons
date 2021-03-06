
/**
 * Lazy approach to implement an algorithm for Dynamic Connectivity.
 * 
 * id[i] is parent of i
 * root of i is id[id[id[...id[i]...]]]
 *
 */
public class QuickUnionUF implements DynamicConnectivity
{
	private int[] id;
	
	public QuickUnionUF(int n)
	{
		id = new int[n];
		for (int i = 0;i < id.length; i++) {
			id[i] = i;
		}
	}
	
	private int root(int i)
	{
		while (i != id[i]) {
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
		id[rootP] = rootQ;
	}

}
