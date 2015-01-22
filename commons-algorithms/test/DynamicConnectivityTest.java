import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public abstract class DynamicConnectivityTest
{
	private DynamicConnectivity dc;
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

	/*	
	public static void main(String[] args)
	{
		StreamParser stdin = new StreamParser(System.in);
		int n = stdin.readInt();
		DynamicConnectivity dc = new DynamicConnectivity(n);
		while (! stdin.isEOF()) {
			int p = stdin.readInt();
			int q = stdin.readInt();
			if (! dc.connected(p, q)) {
				dc.union(p, q);
			}
		}
	}
	*/
}
