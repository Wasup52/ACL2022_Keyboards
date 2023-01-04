package com.keyboards.UI;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestUI {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testConstructeur() {
		UI u= new UI();
		assertNotNull(u.getWIDTH_OFFSET());
		System.out.println("type s.WIDTH_OFFSET: " + u.getWIDTH_OFFSET());
		assertNotNull(u.getHeartEmpty());
		System.out.println("type s.spriteArray : " + u.getHeartEmpty().getClass().getSimpleName());
		
		
	}

}
