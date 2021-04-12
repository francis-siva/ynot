package com.code_studio.app;

/*import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;*/

//import static org.junit.jupiter.api.Assertions.assertEquals;
//import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.jupiter.api.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {
	/*
	@Test
	void checkTestToolVersion() {
		System.out.println( "Hello Test JUnit5.7" );
	}*/
	

    @Test
    void addition() {
        assertEquals(2, 1+ 1);
    }
}