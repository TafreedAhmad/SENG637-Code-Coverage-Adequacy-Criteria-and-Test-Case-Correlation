package org.jfree.data;

import static org.junit.Assert.*; import org.jfree.data.Range; import org.junit.*;

public class RangeTest {
   
    @BeforeClass public static void setUpBeforeClass() throws Exception {
    }

    private Range positiveRange;
 
    
    @Before
    public void setUp() throws Exception {
        // define range
        positiveRange = new Range(2.0, 5.0);
    }
    
    //testing public double getLowerBound() on positiveRange(2.0, 5.0)
    @Test
    public void testGetLowerBound_PositiveRange() {
        // LB of positiveRange
        assertEquals("The lower bound of range (2.0, 5.0) should be 2.0", 
                     2.0, positiveRange.getLowerBound(), 0.000000001d);
    }
    
    
    //testing public double getUpperBound() on positiveRange(2.0, 5.0)
    @Test
    public void testGetUpperBound_PositiveRange() {
    	// UB of positiveRange
        assertEquals("The upper bound of range (2.0, 5.0) should be 5.0",
                5.0, positiveRange.getUpperBound(), 0.000000001d);
    }

    //Range is immutable, the constructor reports error itself when creating an invalid Range(10, 2)
    //the constructor makes sure that lower <= upper is true, so the statement if(lower > upper) will never become true in these three get methods
    
    @Test(expected = IllegalArgumentException.class)
    public void testGetUpperBound_InvalidRangeExcepion() {
        // lower > upper 
        Range invalidRange = new Range(10.0, 2.0); 
        invalidRange.getUpperBound();
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testGetLowerBound_InvalidRangeExcepion() {
        // lower > upper 
        Range invalidRange = new Range(10.0, 2.0); 
        invalidRange.getLowerBound();
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testGetLength_InvalidRangeExcepion() {
        // lower > upper 
        Range invalidRange = new Range(10.0, 2.0); 
        invalidRange.getLength();
    }
    
    //testing getLength() 
    @Test
    public void testGetLength_PositiveRange() {
    	// length of positiveRange
        assertEquals("The length of range (2.0, 5.0) should be 3.0",
                3.0, positiveRange.getLength(), 0.000000001d);
    }
   
    
    // testing getCentralValue() on positive range (2.0, 5.0)
    @Test
    public void testGetCentralValue_PositiveRange() {
    	//central value of positiveRange
        assertEquals("The central value of 2.0 and 5.0 should be 3.5",
                3.5, positiveRange.getCentralValue(), 0.000000001d);
    }
    
    
    //testing public boolean contains(double value) on positive range (2.0, 5.0)
    //the last return statement will stay yellow since its an infeasible path caused by code redundancy. 
    //When we use NaN value to test the final statement, the first condition will return false, 
    //making it impossible to cover the second branch condition in the && expression
    //if we make the first condition value >= lower true and the second condition value <= upper false, the second condition will never be met because
    //there is already a statement if(value > upper) above, the system will end there, so that we can never reach the last statement
    
    @Test
    public void testContains_ValueBelowLower() {
        assertFalse("1.0 is below 2.0, should return false", positiveRange.contains(1.0));
    }

    @Test
    public void testContains_ValueAboveUpper() {
        assertFalse("6.0 is above 5, should return false", positiveRange.contains(6.0));
    }

    @Test
    public void testContains_ValueInside() {
        assertTrue("3.0 is inside 2-5, should return true", positiveRange.contains(3.0));
    }

    @Test
    public void testContains_NaNValue() {
        // execute last line
        assertFalse("NaN should not be contained in any range", positiveRange.contains(Double.NaN));
    }
    
    @Test
    public void testIntersects_LowerOverlap() {
        // b0 <= lower, b1 > lower
        assertTrue("Range(1,3) should intersect with (2,5)", positiveRange.intersects(1.0, 3.0));
    }

    @Test
    public void testIntersects_NoOverlapLower() {
        // b0 <= lower, b1 <= lower
        assertFalse("Range(0,1) should not intersect with (2,5)", positiveRange.intersects(0.0, 1.0));
    }

    @Test
    public void testIntersects_UpperOverlap() {
        // b0 > lower, b0 < upper, b1 >= b0
        assertTrue("Range(3,7) should intersect with (2,5)", positiveRange.intersects(3.0, 7.0));
    }

    @Test
    public void testIntersects_NoOverlapUpper() {
        // b0 > lower, b0 >= upper
        assertFalse("Range(7,8) should not intersect with (2,5)", positiveRange.intersects(7.0, 8.0));
    }

    @Test
    public void testIntersects_B0EqualsUpper() {
        // b0 == upper
        assertFalse("Range(5,12) should not intersect with (2,5)", positiveRange.intersects(5.0, 12.0));
    }
   
    @Test
    public void testIntersects_invalidRange(){
        // b0 > upper, b1 < b0
        assertFalse("Should be false when b1 < b0", positiveRange.intersects(3.0, 1.0));
    }
    
    @Test
    public void testIntersects_RangeObject_True() {
        Range baseRange = new Range(5.0, 10.0);
        Range testRange = new Range(8.0, 12.0); // intersects
        assertTrue("Range(8,12) should intersect with (5,10)", baseRange.intersects(testRange));
    }

    @Test
    public void testIntersects_RangeObject_False() {
        Range baseRange = new Range(5.0, 10.0);
        Range testRange = new Range(11.0, 15.0); // no intersect
        assertFalse("Range(11,15) should not intersect with (5,10)", baseRange.intersects(testRange));
    }
    
    @Test
    public void testCombineIgnoringNaN_FirstRangeNaN() {
        // if: d1 is NaN
        Range r1 = new Range(Double.NaN, Double.NaN);
        Range r2 = new Range(5.0, 10.0);
        Range result = Range.combineIgnoringNaN(r1, r2);
        
        assertEquals("Should return lower bound of r2", 5.0, result.getLowerBound(), .000000001d);
        assertEquals("Should return upper bound of r2", 10.0, result.getUpperBound(), .000000001d);
    }

    @Test
    public void testCombineIgnoringNaN_SecondRangeNaN() {
        // if: d2 is NaN
        Range r1 = new Range(5.0, 10.0);
        Range r2 = new Range(Double.NaN, Double.NaN);
        Range result = Range.combineIgnoringNaN(r1, r2);
        
        assertEquals("Should return lower bound of r1", 5.0, result.getLowerBound(), .000000001d);
    }

    @Test
    public void testCombineIgnoringNaN_BothNormal() {
        // Math.min/max
        Range r1 = new Range(2.0, 8.0);
        Range r2 = new Range(5.0, 10.0);
        Range result = Range.combineIgnoringNaN(r1, r2);
        
        assertEquals("New lower should be 2.0", 2.0, result.getLowerBound(), .000000001d);
        assertEquals("New upper should be 10.0", 10.0, result.getUpperBound(), .000000001d);
    }
    @Test
    public void testConstrain_ValueInside() {
        Range range = new Range(5.0, 10.0);
        // !contains is false
        assertEquals("Constraining 7.0 (inside 5-10) should return 7.0", 
                     7.0, range.constrain(7.0), .000000001d);
    }

    @Test
    public void testConstrain_ValueAboveUpper() {
        Range range = new Range(5.0, 10.0);
        // !contains is true, value > upper is true
        assertEquals("Constraining 12.0 (above 5-10) should return 10.0", 
                     10.0, range.constrain(12.0), .000000001d);
    }

    @Test
    public void testConstrain_ValueBelowLower() {
        Range range = new Range(5.0, 10.0);
        // !contains is true, value > upper is false, value < lower is true
        assertEquals("Constraining 2.0 (below 5-10) should return 5.0", 
                     5.0, range.constrain(2.0), .000000001d);
    }

    @Test
    public void testConstrain_ValueAtBoundary() {
        Range range = new Range(5.0, 10.0);
        // on the edge
        assertEquals("Constraining 5.0 should return 5.0", 
                     5.0, range.constrain(5.0), .000000001d);
    }
    
    @Test
    public void testConstrain_NaN() {
        Range range = new Range(5.0, 10.0);
        //!contains(NaN) is true
        // NaN > upper is false 
        // NaN < lower is false
        double result = range.constrain(Double.NaN);
        assertTrue("Constraining NaN should return NaN", Double.isNaN(result));
    }
    
    @After
    public void tearDown() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }
    
    // T

    @Test
    public void combine_TwoValidRanges_ShouldReturnCombined() {
        Range r1 = new Range(1.0, 5.0);
        Range r2 = new Range(3.0, 8.0);
        Range result = Range.combine(r1, r2);
        assertEquals(1.0, result.getLowerBound(), 1e-9);
        assertEquals(8.0, result.getUpperBound(), 1e-9);
    }

    @Test
    public void combine_FirstNull_ShouldReturnSecond() {
        Range r2 = new Range(3.0, 8.0);
        Range result = Range.combine(null, r2);
        assertEquals(r2, result);
    }

    @Test
    public void combine_SecondNull_ShouldReturnFirst() {
        Range r1 = new Range(1.0, 5.0);
        Range result = Range.combine(r1, null);
        assertEquals(r1, result);
    }

    @Test
    public void combine_BothNull_ShouldReturnNull() {
        assertNull(Range.combine(null, null));
    }



    @Test
    public void combineIgnoringNaN_TwoValidRanges_ShouldReturnCombined() {
        Range r1 = new Range(1.0, 5.0);
        Range r2 = new Range(3.0, 8.0);
        Range result = Range.combineIgnoringNaN(r1, r2);
        assertEquals(1.0, result.getLowerBound(), 1e-9);
        assertEquals(8.0, result.getUpperBound(), 1e-9);
    }

    @Test
    public void combineIgnoringNaN_FirstNull_ShouldReturnSecond() {
        Range r2 = new Range(3.0, 8.0);
        Range result = Range.combineIgnoringNaN(null, r2);
        assertEquals(r2, result);
    }

    @Test
    public void combineIgnoringNaN_SecondNull_ShouldReturnFirst() {
        Range r1 = new Range(1.0, 5.0);
        Range result = Range.combineIgnoringNaN(r1, null);
        assertEquals(r1, result);
    }

    @Test
    public void combineIgnoringNaN_BothNull_ShouldReturnNull() {
        assertNull(Range.combineIgnoringNaN(null, null));
    }

    @Test
    public void combineIgnoringNaN_NaNRange_ShouldIgnoreNaN() {
        Range r1 = new Range(Double.NaN, Double.NaN);
        Range r2 = new Range(1.0, 5.0);
        Range result = Range.combineIgnoringNaN(r1, r2);
        assertEquals(r2, result);
    }



    @Test
    public void expand_ValidRange_ShouldExpandCorrectly() {
        Range r = new Range(2.0, 6.0);
        Range result = Range.expand(r, 0.25, 0.5);
        assertEquals(1.0, result.getLowerBound(), 1e-9);
        assertEquals(8.0, result.getUpperBound(), 1e-9);
    }

    @Test
    public void expand_ZeroMargins_ShouldReturnSameRange() {
        Range r = new Range(2.0, 6.0);
        Range result = Range.expand(r, 0.0, 0.0);
        assertEquals(2.0, result.getLowerBound(), 1e-9);
        assertEquals(6.0, result.getUpperBound(), 1e-9);
    }



    @Test
    public void expandToInclude_ValueAboveRange_ShouldExpandUpper() {
        Range r = new Range(1.0, 5.0);
        Range result = Range.expandToInclude(r, 10.0);
        assertEquals(10.0, result.getUpperBound(), 1e-9);
    }

    @Test
    public void expandToInclude_ValueBelowRange_ShouldExpandLower() {
        Range r = new Range(1.0, 5.0);
        Range result = Range.expandToInclude(r, -5.0);
        assertEquals(-5.0, result.getLowerBound(), 1e-9);
    }

    @Test
    public void expandToInclude_ValueInsideRange_ShouldReturnSame() {
        Range r = new Range(1.0, 5.0);
        Range result = Range.expandToInclude(r, 3.0);
        assertEquals(1.0, result.getLowerBound(), 1e-9);
        assertEquals(5.0, result.getUpperBound(), 1e-9);
    }

    @Test
    public void expandToInclude_NullRange_ShouldReturnPointRange() {
        Range result = Range.expandToInclude(null, 3.0);
        assertEquals(3.0, result.getLowerBound(), 1e-9);
        assertEquals(3.0, result.getUpperBound(), 1e-9);
    }

  

    @Test
    public void scale_ValidFactor_ShouldScaleCorrectly() {
        Range r = new Range(1.0, 5.0);
        Range result = Range.scale(r, 2.0);
        assertEquals(2.0, result.getLowerBound(), 1e-9);
        assertEquals(10.0, result.getUpperBound(), 1e-9);
    }

    @Test(expected = IllegalArgumentException.class)
    public void scale_NegativeFactor_ShouldThrowException() {
        Range r = new Range(1.0, 5.0);
        Range.scale(r, -1.0);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testScale_NullBase() {
        Range.scale(null, 1.5); 
    }
    
    @Test
    public void scale_ZeroFactor_ShouldReturnZeroRange() {
        Range r = new Range(1.0, 5.0);
        Range result = Range.scale(r, 0.0);
        assertEquals(0.0, result.getLowerBound(), 1e-9);
        assertEquals(0.0, result.getUpperBound(), 1e-9);
    }



    @Test
    public void shift_PositiveDelta_ShouldShiftRight() {
        Range r = new Range(1.0, 5.0);
        Range result = Range.shift(r, 2.0);
        assertEquals(3.0, result.getLowerBound(), 1e-9);
        assertEquals(7.0, result.getUpperBound(), 1e-9);
    }

    @Test
    public void shift_NegativeDelta_ShouldShiftLeft() {
        Range r = new Range(2.0, 6.0);
        Range result = Range.shift(r, -1.0);
        assertEquals(1.0, result.getLowerBound(), 1e-9);
        assertEquals(5.0, result.getUpperBound(), 1e-9);
    }


    @Test
    public void shift_AllowZeroCrossing_ShouldAllowNegative() {
        Range r = new Range(1.0, 5.0);
        Range result = Range.shift(r, -3.0, true);
        assertEquals(-2.0, result.getLowerBound(), 1e-9);
    }

    @Test
    public void shift_NoZeroCrossing_ShouldClampAtZero() {
        Range r = new Range(1.0, 5.0);
        Range result = Range.shift(r, -3.0, false);
        assertEquals(0.0, result.getLowerBound(), 1e-9);
    }


    @Test
    public void equals_SameRange_ShouldReturnTrue() {
        Range r1 = new Range(1.0, 5.0);
        Range r2 = new Range(1.0, 5.0);
        assertTrue(r1.equals(r2));
    }

    @Test
    public void equals_DifferentRange_ShouldReturnFalse() {
        Range r1 = new Range(1.0, 5.0);
        Range r2 = new Range(2.0, 6.0);
        assertFalse(r1.equals(r2));
    }

    @Test
    public void equals_Null_ShouldReturnFalse() {
        Range r1 = new Range(1.0, 5.0);
        assertFalse(r1.equals(null));
    }

    @Test
    public void equals_DifferentObject_ShouldReturnFalse() {
        Range r1 = new Range(1.0, 5.0);
        assertFalse(r1.equals("not a range"));
    }


    @Test
    public void hashCode_SameRanges_ShouldReturnSameHash() {
        Range r1 = new Range(1.0, 5.0);
        Range r2 = new Range(1.0, 5.0);
        assertEquals(r1.hashCode(), r2.hashCode());
    }

    @Test
    public void hashCode_DifferentRanges_ShouldReturnDifferentHash() {
        Range r1 = new Range(1.0, 5.0);
        Range r2 = new Range(2.0, 6.0);
        assertFalse(r1.hashCode() == r2.hashCode());
    }



    @Test
    public void isNaNRange_NaNBounds_ShouldReturnTrue() {
        Range r = new Range(Double.NaN, Double.NaN);
        assertTrue(r.isNaNRange());
    }

    @Test
    public void isNaNRange_ValidBounds_ShouldReturnFalse() {
        Range r = new Range(1.0, 5.0);
        assertFalse(r.isNaNRange());
    }


    @Test
    public void toString_ValidRange_ShouldReturnCorrectString() {
        Range r = new Range(1.0, 5.0);
        assertEquals("Range[1.0,5.0]", r.toString());
    }


    @Test
    public void constrain_ValueInsideRange_ShouldReturnValue() {
        Range r = new Range(1.0, 5.0);
        assertEquals(3.0, r.constrain(3.0), 1e-9);
    }

    @Test
    public void constrain_ValueAboveRange_ShouldReturnUpperBound() {
        Range r = new Range(1.0, 5.0);
        assertEquals(5.0, r.constrain(10.0), 1e-9);
    }

    @Test
    public void constrain_ValueBelowRange_ShouldReturnLowerBound() {
        Range r = new Range(1.0, 5.0);
        assertEquals(1.0, r.constrain(-5.0), 1e-9);
    }
    
    

    
}