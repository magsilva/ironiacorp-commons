
/**
 * Eager approach to implement an algorithm for Dynamic Connectivity.
 */
public class QuickFindDC implements DynamicConnectivity
{
	private int[] id;
	
	public QuickFindDC(int n)
	{
		id = new int[n];
		for (int i = 0;i < id.length; i++) {
			id[i] = i;
		}
	}
	
	@Override
	public boolean connected(int p, int q)
	{
		return id[p] == id[q];
	}
	
	@Override
	public void union(int p, int q)
	{
		int idP, idQ;
		idP = id[p];
		idQ = id[q];
		
		// Just perform the union if needed...
		if (idP != idQ) {
			for (int i = 0; i < id.length; i++) {
				if (id[i] == idP) {
					id[i] = idQ;
				}
			}
		}
	}

	
	
}
