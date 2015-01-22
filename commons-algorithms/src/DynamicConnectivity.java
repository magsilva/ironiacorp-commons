
/**
 * Evaluate the dynamic connectivity between objects.
 * 
 * For now, every object is just an integer.
 */
public interface DynamicConnectivity
{
	/**
	 * Check whether two objects are connected. This is a equivalence relation:
	 *  * reflexive (p is connected to p)
	 *  * symmetric (if p is connected to q, then q is connected to p)
	 *  * transitive (if p is connected to q, and q is connected to r, then p
	 *    is connected to r).
	 * 
	 * @param p Object 1
	 * @param q Object 2
	 * 
	 * @return True if connected, False otherwise
	 */
	boolean connected(int p, int q);
	
	/**
	 * Connect two objects
	 * 
	 * @param p Object to be connected to
	 * @param q Object that will be connected
	 */
	void union(int p, int q);
	
	/**
	 * Get the component identifier for object p.
	 * 
	 * @param p
	 * @return
	 */
	// int find(int p);
	
	/**
	 * Count the number of connected components.
	 * 
	 */
	// int count();
	
	/**
	 * Maximal set of objects that are connected.
	 *
	 * @return
	 */
	//int[] connectedComponent();
}
