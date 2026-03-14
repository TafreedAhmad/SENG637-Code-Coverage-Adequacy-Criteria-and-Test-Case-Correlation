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
    
    
//test contains(double value) on positive range (2.0, 5.0)

    //the last return statement will stay yellow since its an infeasible path caused by code redundancy. 
    //if we make the first condition value >= lower true and the second condition value <= upper false, we will not reach the last statement because
    //there is already a statement if(value > upper) above, the system will end there, so that we can never make it T&&F
    @Test
    public void testContains_ValueBelowLower() {
    	//below
        assertFalse("1.0 is below 2.0, should return false", positiveRange.contains(1.0));
    }

    @Test
    public void testContains_ValueAboveUpper() {
    	//above
        assertFalse("6.0 is above 5, should return false", positiveRange.contains(6.0));
    }

    @Test
    public void testContains_ValueInside() {
    	//inside
        assertTrue("3.0 is inside 2-5, should return true", positiveRange.contains(3.0));
    }

    @Test
    public void testContains_NaNValue() {
        // execute last statement F&F
        assertFalse("NaN should not be contained in any range", positiveRange.contains(Double.NaN));
    }
    
    //test intersects(double, double)
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
    
//combineIgnoringNaN(Range, Range)

    //Case: Both ranges contain valid numeric bounds.
    //combineIgnoringNaN() should return  a new range with the minimum lower bound and maximum upper bound.
    @Test
    public void combineIgnoringNaN_TwoValidRanges_ShouldReturnCombined() {
      Range r1 = new Range(1.0, 5.0);
      Range r2 = new Range(3.0, 8.0);
      Range result = Range.combineIgnoringNaN(r1, r2);
      assertEquals(1.0, result.getLowerBound(), 1e-9);
      assertEquals(8.0, result.getUpperBound(), 1e-9);
    }

    //range1 is null and range2 is valid.
    //the method should return range2.
    @Test
    public void combineIgnoringNaN_FirstNull_ShouldReturnSecond() {	
    	Range r2 = new Range(3.0, 8.0);
    	Range result = Range.combineIgnoringNaN(null, r2);
    	assertEquals(r2, result);
    }

    //range2 is null and range1 is valid.
    //the method should return range1.
    @Test
    public void combineIgnoringNaN_SecondNull_ShouldReturnFirst() {
    	Range r1 = new Range(1.0, 5.0);
    	Range result = Range.combineIgnoringNaN(r1, null);
    	assertEquals(r1, result);
    }

    //both ranges are null and the method should return null.
    @Test
    public void combineIgnoringNaN_BothNull_ShouldReturnNull() {
    	assertNull(Range.combineIgnoringNaN(null, null));
    }

    //range1 contains NaNbounds and range2 is valid.
    //this triggers the internal helper method isNaNRange().
    // NaN range should be ignored and range2 returned. 
    @Test
    public void combineIgnoringNaN_NaNRange_ShouldIgnoreNaN() {
    	Range r1 = new Range(Double.NaN, Double.NaN);
    	Range r2 = new Range(1.0, 5.0);
    	Range result = Range.combineIgnoringNaN(r1, r2);
    	assertEquals(r2, result);
    }
  
    //Case: Range1 is null and range 2 contains NaN bounds
    //This triggers the internal method isNaNRange()
    //This method should return null if either range is a NaN range, so the result should be null
	@Test
	public void testCombineIgnoringNaN_Range1NullRange2NaN() { 
	    Range r2 = new Range(Double.NaN, Double.NaN);
	    Range result = Range.combineIgnoringNaN(null, r2);
	    assertNull(result);
	}
	
	//case: range2 is null and range 1 contains NaN bounds.
	//This also triggers the internal method isNaNRange().
	// This method detects NaN range and returns null.
	@Test
	public void testCombineIgnoringNaN_Range2NullRange1NaN() {
	    Range r1 = new Range(Double.NaN, Double.NaN);
	    Range result = Range.combineIgnoringNaN(r1, null);
	    assertNull(result);
	}
	
 	//Case: Both ranges contains NaN bounds.
	//this triggers isNaNRange() for both inputs.	
	//The method should return null.
	@Test
	public void testCombineIgnoringNaN_BothNaNBounds() {  // triggering isNanRange()
	    Range r1 = new Range(Double.NaN, Double.NaN);
	    Range r2 = new Range(Double.NaN, Double.NaN);
	    Range result = Range.combineIgnoringNaN(r1, r2);
	    assertNull(result);
	}
	
	//Case: one range has NaN lower bound and the upper bounds are valid.
	//This checks the condition where a range is not considered a full NaN range because both bounds are not NaN.
	//the result should still be created with NaN lower bound and the maximum upper bound. 
	@Test
	public void testCombineIgnoringNaN_LowerBoundNaNOnly() {
	    // make a range with NaN lower and double upper  
	    Range r1 = new Range(Double.NaN, 10.0);
	    Range r2 = new Range(Double.NaN, 5.0);
	    Range result = Range.combineIgnoringNaN(r1, r2);
	    
	    // if (T && F) is False
	    assertNotNull(result);
	    assertTrue(Double.isNaN(result.getLowerBound()));
	    assertEquals(10.0, result.getUpperBound(), 1e-9);
	}
  	
//test min(double, double) and max(double, double)
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
    
    // test constrain(double)
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
    
 // Test combine(Range, Range)
    // combine() returns a new range spanning both input ranges.
    // If either range is null, the other range is returned.
    // If both are null, null is returned.

    // Case: both ranges are valid - result should span from min lower to max upper
    @Test
    public void combine_TwoValidRanges_ShouldReturnCombined() {
        Range r1 = new Range(1.0, 5.0);
        Range r2 = new Range(3.0, 8.0);
        Range result = Range.combine(r1, r2);
        assertEquals(1.0, result.getLowerBound(), 1e-9);
        assertEquals(8.0, result.getUpperBound(), 1e-9);
    }

    // Case: range1 is null - should return range2 unchanged
    @Test
    public void combine_FirstNull_ShouldReturnSecond() {
        Range r2 = new Range(3.0, 8.0);
        Range result = Range.combine(null, r2);
        assertEquals(r2, result);
    }

    // Case: range2 is null - should return range1 unchanged
    @Test
    public void combine_SecondNull_ShouldReturnFirst() {
        Range r1 = new Range(1.0, 5.0);
        Range result = Range.combine(r1, null);
        assertEquals(r1, result);
    }

    // Case: both ranges are null - should return null
    @Test
    public void combine_BothNull_ShouldReturnNull() {
        assertNull(Range.combine(null, null));
    }

//expand(Range, double, double)	
    //case: expanding a valid range with positive margins.
    //range length = 20-10 = 10
    //lower bound expands by 10*0.1 = 1, new lower bound = 10-1 = 9
    //upper bound expands by 10*0.1 = 1, new upper bound = 20+1 = 21
    // new range should be (9, 21)
	@Test
	public void testExpandNormalMargins() {
	    Range r = new Range(10.0, 20.0);
	    Range result = Range.expand(r, 0.1, 0.1);
	    assertEquals(9.0, result.getLowerBound(), 0.0001);
	    assertEquals(21.0, result.getUpperBound(), 0.0001);
	}
	
	// case: using large negative margins which shrink the range to a single point.
	// the calculated lower bound will be greater than the upper bound.	
	// expand() adjusts both bounds to the midpoint resulting in a range where lower == upper.
	@Test
	public void testExpandNegativeScale() {
	    Range r = new Range(10.0, 20.0);
	    Range result = Range.expand(r, -0.6, -0.6);
	    assertEquals(result.getLowerBound(), result.getUpperBound(), 0.0001);
	}
	
	//Case: input range is null.
	// expand() internally checks parameters using Parameters.nullNotPermitted() and throws IllegalArgumentException if the range is null.
	@Test(expected = IllegalArgumentException.class)
	public void testExpandNullRange() {
	    Range.expand(null, 0.1, 0.1);
	}


// test expandToInclude(Range, double)
    // expandToInclude() returns a new range that includes the given value.
    // If the value is above the upper bound, the upper bound expands.
    // If the value is below the lower bound, the lower bound expands.
    // If the value is inside the range, the range is unchanged.
    // If the range is null, a new point range at the value is created.

    // Case: value is above upper bound - upper bound should expand to include value
    @Test
    public void expandToInclude_ValueAboveRange_ShouldExpandUpper() {
        Range r = new Range(1.0, 5.0);
        Range result = Range.expandToInclude(r, 10.0);
        assertEquals(10.0, result.getUpperBound(), 1e-9);
    }

    // Case: value is below lower bound - lower bound should expand to include value
    @Test
    public void expandToInclude_ValueBelowRange_ShouldExpandLower() {
        Range r = new Range(1.0, 5.0);
        Range result = Range.expandToInclude(r, -5.0);
        assertEquals(-5.0, result.getLowerBound(), 1e-9);
    }

    // Case: value is already inside range - range should remain unchanged
    @Test
    public void expandToInclude_ValueInsideRange_ShouldReturnSame() {
        Range r = new Range(1.0, 5.0);
        Range result = Range.expandToInclude(r, 3.0);
        assertEquals(1.0, result.getLowerBound(), 1e-9);
        assertEquals(5.0, result.getUpperBound(), 1e-9);
    }

    // Case: range is null - should create a new point range at the given value
    @Test
    public void expandToInclude_NullRange_ShouldReturnPointRange() {
        Range result = Range.expandToInclude(null, 3.0);
        assertEquals(3.0, result.getLowerBound(), 1e-9);
        assertEquals(3.0, result.getUpperBound(), 1e-9);
    }


// test scale(Range, double)
    // scale() multiplies both bounds by the given factor.
    // factor must be >= 0, otherwise IllegalArgumentException is thrown.
    // If the base range is null, IllegalArgumentException is thrown.

    // Case: valid positive factor - both bounds should be multiplied by factor
    @Test
    public void scale_ValidFactor_ShouldScaleCorrectly() {
        Range r = new Range(1.0, 5.0);
        Range result = Range.scale(r, 2.0);
        assertEquals(2.0, result.getLowerBound(), 1e-9);
        assertEquals(10.0, result.getUpperBound(), 1e-9);
    }
 
    
    

 // test shift(Range, double)
    // shift() moves both bounds by the given delta value.
    // The two-argument version does not allow zero crossing by default.
    // The three-argument version allows zero crossing if allowZeroCrossing is true.
    // If zero crossing is not allowed, bounds are clamped at zero.

    // Case: positive delta - both bounds shift right by delta amount
    @Test
    public void shift_PositiveDelta_ShouldShiftRight() {
        Range r = new Range(1.0, 5.0);
        Range result = Range.shift(r, 2.0);
        assertEquals(3.0, result.getLowerBound(), 1e-9);
        assertEquals(7.0, result.getUpperBound(), 1e-9);
    }

     // Case: negative delta - both bounds shift left by delta amount
    @Test
    public void shift_NegativeDelta_ShouldShiftLeft() {
        Range r = new Range(2.0, 6.0);
        Range result = Range.shift(r, -1.0);
        assertEquals(1.0, result.getLowerBound(), 1e-9);
        assertEquals(5.0, result.getUpperBound(), 1e-9);
    }

    // Case: zero crossing allowed - bounds can go negative even from positive range
    @Test
    public void shift_AllowZeroCrossing_ShouldAllowNegative() {
        Range r = new Range(1.0, 5.0);
        Range result = Range.shift(r, -3.0, true);
        assertEquals(-2.0, result.getLowerBound(), 1e-9);
    }

     // Case: zero crossing not allowed - lower bound is clamped at 0 instead of going negative
    @Test
    public void shift_NoZeroCrossing_ShouldClampAtZero() {
        Range r = new Range(1.0, 5.0);
        Range result = Range.shift(r, -3.0, false);
        assertEquals(0.0, result.getLowerBound(), 1e-9);
    }
    
//shiftWithNoZeroCrossing()
    // case: both bounds are positive and the shift delta is positive.
    //since zero crossing is not allowed, the bounds simply shift by +2.
    // expected result: [5,10] -> [7,12]
	@Test
	public void testShiftPositiveNoZeroCrossing() {
	    Range r = new Range(5.0, 10.0);
	    Range result = Range.shift(r, 2.0, false);
	    assertEquals(7.0, result.getLowerBound(), 0.0001);
	    assertEquals(12.0, result.getUpperBound(), 0.0001);
	}
	
	//case: positive range shifted with large negative delta.
	//lower bound would normally become negative(1-5 = -4)
	//but since zero crossing is not allowed, the lower bound is clamped at 0.
	// expected result: [1,3] -> [0, -2] but clamped to [0,0]
	@Test
	public void testShiftPositiveCrossZero() {
	    Range r = new Range(1.0, 3.0);
	    Range result = Range.shift(r, -5.0, false);
	    assertEquals(0.0, result.getLowerBound(), 0.0001);
	}
	

	//case: both bounds are negative and the shift delta is negative.
	//since zero crossing is not allowed and bounds stay negative, they simply shift by the delta.
	// expected result: [-10, -5] -> [-12, -7]
	@Test
	public void testShiftNegativeNoZeroCrossing() {
	    Range r = new Range(-10.0, -5.0);
	    Range result = Range.shift(r, -2.0, false);
	    assertEquals(-12.0, result.getLowerBound(), 0.0001);
	    assertEquals(-7.0, result.getUpperBound(), 0.0001);
	}
	

	//case: negative range shifted with large positive delta.
	//upper bound would normally become positive(-1 + 5 = 4)
	//but since zero crossing is not allowed, the upper bound is limited to 0.
	// expected result: upper bound become zero as [-3, -1] -> [2, 4] but clamped to [0,0]
	@Test
	public void testShiftNegativeCrossZero() {
	    Range r = new Range(-3.0, -1.0);
	    Range result = Range.shift(r, 5.0, false);
	    assertEquals(0.0, result.getUpperBound(), 0.0001);
	}
	
	//case: range where both bounds are exactly zero.
	// the shift operation simply adds the delta to both bounds.
	// expected result: [0,0] -> [5,5]
	@Test
	public void testShiftZeroValue() {
	    Range r = new Range(0.0, 0.0);
	    Range result = Range.shift(r, 5.0, false);
	    assertEquals(5.0, result.getLowerBound(), 0.0001);
	    assertEquals(5.0, result.getUpperBound(), 0.0001);
	}

//test equals(Range)
  	// case: both ranges objects have identical lower and upper bounds.
    @Test
    public void equals_SameRange_ShouldReturnTrue() {
        Range r1 = new Range(1.0, 5.0);
        Range r2 = new Range(1.0, 5.0);
        assertTrue(r1.equals(r2));
    }

    //Case: two range objects have different lower and upper bounds.
    @Test
    public void equals_DifferentRange_ShouldReturnFalse() {
        Range r1 = new Range(1.0, 5.0);
        Range r2 = new Range(2.0, 6.0);
        assertFalse(r1.equals(r2));
    }


    //case: comparing a range object with an object of different type (e.g., String).
    @Test
    public void equals_DifferentObject_ShouldReturnFalse() {
        Range r1 = new Range(1.0, 5.0);
        assertFalse(r1.equals("not a range"));
    }
    
    //case: two range objects have the same lower bound but different upper bounds.
    @Test
    public void testEquals_SameLowerDifferentUpper() {
        Range r1 = new Range(5.0, 10.0);
        Range r2 = new Range(5.0, 15.0); 
        assertFalse("Ranges with different upper bounds should not be equal", r1.equals(r2));
    }
    
//test hashCode()
    @Test
    //same ranges should return same hashcode
    public void hashCode_SameRanges_ShouldReturnSameHash() {
        Range r1 = new Range(1.0, 5.0);
        Range r2 = new Range(1.0, 5.0);
        assertEquals(r1.hashCode(), r2.hashCode());
    }
    

//test isNaNRange()
    @Test
    public void testIsNaNRange_OnlyLowerIsNaN() {
        // A True B False -> return False
        Range range = new Range(Double.NaN, 10.0);
        assertFalse("Should be false if only lower is NaN", range.isNaNRange());
    }

    @Test
    public void testIsNaNRange_OnlyUpperIsNaN() {
        // A False B False -> return False
        Range range = new Range(1.0, 2.0);
        assertFalse("Should be false if none is NaN", range.isNaNRange());
    }

    @Test
    public void testIsNaNRange_BothAreNaN() {
        // A True, B True -> return True
        Range range = new Range(Double.NaN, Double.NaN);
        assertTrue("Should be true if both bounds are NaN", range.isNaNRange());
    }

  //test toString()
    @Test
    public void toString_ValidRange_ShouldReturnCorrectString() {
        Range r = new Range(1.0, 5.0);
        assertEquals("Range[1.0,5.0]", r.toString());
    }

    
    @After
    public void tearDown() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    
}