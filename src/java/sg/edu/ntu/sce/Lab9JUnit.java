package sg.edu.ntu.sce;

import junit.framework.TestCase;
import java.io.*;

public class Lab9JUnit extends TestCase {

	protected void setUp() throws Exception { 
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public final void testSaliency() {
		Saliency.main(new String[]{"donald_duck_in.bmp", "donald_duck_out.bmp"});
		boolean compare = Compare.compareImage("donald_duck_ref.bmp","donald_duck_out.bmp");
		assertEquals("Saliency works=",true, compare);
	}
	
}
