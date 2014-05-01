package worms.model.points;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PointsTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		AP12 = new Points(12,SortPoints.AP);
		AP15 = new Points(15,SortPoints.AP);
	}
	private  Points AP12;
	private  Points AP15;


	@After
	public void tearDown() throws Exception {
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testConstructor_IllegalCase(){
		Points p = new Points(-1,SortPoints.AP);
	}

	@Test
	public void testCompareTo(){		
		assertTrue(AP12.isComparableTo(AP15));
	}
	@Test 
	public void testSubstract1(){
		assertEquals(0,AP12.substract(AP15).getNumeral());
	}
	@Test
	public void testSubstract2(){
		assertEquals(3,AP15.substract(AP12).getNumeral());
	}
	@Test
	public void testAdd(){
		assertEquals(27,AP12.add(AP15).getNumeral());
	}
	@Test
	public void testAdd_otherIsNull(){
		Points p = null;
		Points sum = AP12.add(p);
	}
	@Test 
	public void testEnoughPointsLeft_TrueCase(){
		assertTrue(AP15.enoughPointsLeft(AP12));
	}
	@Test 
	public void testEnoughPointsLeft_FalseCase(){
		assertFalse(AP12.enoughPointsLeft(AP15));
	}
	@Test
	public void testIsGreaterThan_TrueCase(){
		assertTrue(AP15.isGreaterThan(AP12));
	}
	@Test
	public void testIsGreaterThan_FalseCase(){
		assertFalse(AP12.isGreaterThan(AP15));
	}
	@Test
	public void testIsSmallerThan_TrueCase(){
		assertTrue(AP12.isSmallerThan(AP15));
	}
	@Test
	public void testIsSmallerThan_FalseCase(){
		assertFalse(AP15.isSmallerThan(AP12));
	}

	@Test
	public void testIsEqualTo_FalseCase(){
		assertFalse(AP15.isEqualTo(AP12));
	}
	
	
}
