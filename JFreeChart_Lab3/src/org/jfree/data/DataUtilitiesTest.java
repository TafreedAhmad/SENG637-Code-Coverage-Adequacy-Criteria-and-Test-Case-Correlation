package org.jfree.data;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DataUtilitiesTest {

    private Mockery context;
    private Values2D values2D;

    @Before
    public void setUp() {
        context = new Mockery();
        values2D = context.mock(Values2D.class);
    }

    // --- calculateColumnTotal(Values2D data, int column) Tests ---

    @Test
    public void calculateColumnTotalWithPositiveValues() {
        context.checking(new Expectations() {{
            oneOf(values2D).getRowCount(); will(returnValue(3));
            oneOf(values2D).getValue(0, 0); will(returnValue(10.0));
            oneOf(values2D).getValue(1, 0); will(returnValue(20.0));
            oneOf(values2D).getValue(2, 0); will(returnValue(30.0));
        }});

        double result = DataUtilities.calculateColumnTotal(values2D, 0);
        assertEquals(60.0, result, 0.000000001d);
    }

    @Test
    public void calculateColumnTotalWithNegativeValues() {
        context.checking(new Expectations() {{
            oneOf(values2D).getRowCount(); will(returnValue(2));
            oneOf(values2D).getValue(0, 0); will(returnValue(-15.0));
            oneOf(values2D).getValue(1, 0); will(returnValue(-25.0));
        }});

        double result = DataUtilities.calculateColumnTotal(values2D, 0);
        assertEquals(-40.0, result, 0.000000001d);
    }

    @Test
    public void calculateColumnTotalWithNullValues() {
        context.checking(new Expectations() {{
            oneOf(values2D).getRowCount(); will(returnValue(3));
            oneOf(values2D).getValue(0, 2); will(returnValue(10.0));
            oneOf(values2D).getValue(1, 2); will(returnValue(null));
            oneOf(values2D).getValue(2, 2); will(returnValue(20.0));
        }});

        double result = DataUtilities.calculateColumnTotal(values2D, 2);
        assertEquals(30.0, result, 0.000000001d);
    }

    @Test
    public void calculateColumnTotalWithAllNullValues() {
        context.checking(new Expectations() {{
            oneOf(values2D).getRowCount(); will(returnValue(2));
            oneOf(values2D).getValue(0, 0); will(returnValue(null));
            oneOf(values2D).getValue(1, 0); will(returnValue(null));
        }});

        double result = DataUtilities.calculateColumnTotal(values2D, 0);
        assertEquals(0.0, result, 0.000000001d);
    }

    @Test
    public void calculateColumnTotalWithEmptyTable() {
        context.checking(new Expectations() {{
            oneOf(values2D).getRowCount(); will(returnValue(0));
        }});

        double result = DataUtilities.calculateColumnTotal(values2D, 0);
        assertEquals(0.0, result, 0.000000001d);
    }

    @Test
    public void calculateColumnTotalWithZeroValues() {
        context.checking(new Expectations() {{
            oneOf(values2D).getRowCount(); will(returnValue(3));
            oneOf(values2D).getValue(0, 0); will(returnValue(0.0));
            oneOf(values2D).getValue(1, 0); will(returnValue(0.0));
            oneOf(values2D).getValue(2, 0); will(returnValue(0.0));
        }});

        double result = DataUtilities.calculateColumnTotal(values2D, 0);
        assertEquals(0.0, result, 0.000000001d);
    }

    @Test
    public void calculateColumnTotalWithLargeValues() {
        context.checking(new Expectations() {{
            oneOf(values2D).getRowCount(); will(returnValue(2));
            oneOf(values2D).getValue(0, 0); will(returnValue(1000000.5));
            oneOf(values2D).getValue(1, 0); will(returnValue(2000000.5));
        }});

        double result = DataUtilities.calculateColumnTotal(values2D, 0);
        assertEquals(3000001.0, result, 0.000000001d);
    }

    @Test
    public void calculateColumnTotalWithDecimalValues() {
        context.checking(new Expectations() {{
            oneOf(values2D).getRowCount(); will(returnValue(2));
            oneOf(values2D).getValue(0, 0); will(returnValue(0.1));
            oneOf(values2D).getValue(1, 0); will(returnValue(0.2));
        }});

        double result = DataUtilities.calculateColumnTotal(values2D, 0);
        assertEquals(0.3, result, 0.000000001d);
    }

    @Test
    public void calculateColumnTotalWithDifferentColumns() {
        context.checking(new Expectations() {{
            oneOf(values2D).getRowCount(); will(returnValue(2));
            oneOf(values2D).getValue(0, 5); will(returnValue(7.5));
            oneOf(values2D).getValue(1, 5); will(returnValue(2.5));
        }});

        double result = DataUtilities.calculateColumnTotal(values2D, 5);
        assertEquals(10.0, result, 0.000000001d);
    }

    @Test(expected = IllegalArgumentException.class)
    public void calculateColumnTotalThrowsExceptionForNullData() {
        
        DataUtilities.calculateColumnTotal(null, 0);
        fail("Expected IllegalArgumentException for null data");
    }

    @Test
    public void calculateColumnTotalWithSingleRow() {
        context.checking(new Expectations() {{
            oneOf(values2D).getRowCount(); will(returnValue(1));
            oneOf(values2D).getValue(0, 0); will(returnValue(42.0));
        }});

        double result = DataUtilities.calculateColumnTotal(values2D, 0);
        assertEquals(42.0, result, 0.000000001d);
    }

    // --- calculateRowTotal(Values2D data, int row) Tests ---

    @Test
    public void calculateRowTotalWithPositiveValues() {
        context.checking(new Expectations() {{
            oneOf(values2D).getColumnCount(); will(returnValue(3));
            oneOf(values2D).getValue(0, 0); will(returnValue(10.0));
            oneOf(values2D).getValue(0, 1); will(returnValue(20.0));
            oneOf(values2D).getValue(0, 2); will(returnValue(30.0));
        }});

        double result = DataUtilities.calculateRowTotal(values2D, 0);
        assertEquals(60.0, result, 0.000000001d);
    }

    @Test
    public void calculateRowTotalWithNegativeValues() {
        context.checking(new Expectations() {{
            oneOf(values2D).getColumnCount(); will(returnValue(2));
            oneOf(values2D).getValue(1, 0); will(returnValue(-15.0));
            oneOf(values2D).getValue(1, 1); will(returnValue(-25.0));
        }});

        double result = DataUtilities.calculateRowTotal(values2D, 1);
        assertEquals(-40.0, result, 0.000000001d);
    }

    @Test
    public void calculateRowTotalWithNullValues() {
        context.checking(new Expectations() {{
            oneOf(values2D).getColumnCount(); will(returnValue(3));
            oneOf(values2D).getValue(0, 0); will(returnValue(10.0));
            oneOf(values2D).getValue(0, 1); will(returnValue(null));
            oneOf(values2D).getValue(0, 2); will(returnValue(20.0));
        }});

        double result = DataUtilities.calculateRowTotal(values2D, 0);
        assertEquals(30.0, result, 0.000000001d);
    }

    @Test
    public void calculateRowTotalWithAllNullValues() {
        context.checking(new Expectations() {{
            oneOf(values2D).getColumnCount(); will(returnValue(2));
            oneOf(values2D).getValue(1, 0); will(returnValue(null));
            oneOf(values2D).getValue(1, 1); will(returnValue(null));
        }});

        double result = DataUtilities.calculateRowTotal(values2D, 1);
        assertEquals(0.0, result, 0.000000001d);
    }

    @Test
    public void calculateRowTotalWithEmptyRow() {
        context.checking(new Expectations() {{
            oneOf(values2D).getColumnCount(); will(returnValue(0));
        }});

        double result = DataUtilities.calculateRowTotal(values2D, 0);
        assertEquals(0.0, result, 0.000000001d);
    }

    @Test
    public void calculateRowTotalWithZeroValues() {
        context.checking(new Expectations() {{
            oneOf(values2D).getColumnCount(); will(returnValue(3));
            oneOf(values2D).getValue(0, 0); will(returnValue(0.0));
            oneOf(values2D).getValue(0, 1); will(returnValue(0.0));
            oneOf(values2D).getValue(0, 2); will(returnValue(0.0));
        }});

        double result = DataUtilities.calculateRowTotal(values2D, 0);
        assertEquals(0.0, result, 0.000000001d);
    }

    @Test
    public void calculateRowTotalWithLargeValues() {
        context.checking(new Expectations() {{
            oneOf(values2D).getColumnCount(); will(returnValue(2));
            oneOf(values2D).getValue(1, 0); will(returnValue(1000000.5));
            oneOf(values2D).getValue(1, 1); will(returnValue(2000000.5));
        }});

        double result = DataUtilities.calculateRowTotal(values2D, 1);
        assertEquals(3000001.0, result, 0.000000001d);
    }

    @Test
    public void calculateRowTotalWithDecimalValues() {
        context.checking(new Expectations() {{
            oneOf(values2D).getColumnCount(); will(returnValue(2));
            oneOf(values2D).getValue(0, 0); will(returnValue(0.1));
            oneOf(values2D).getValue(0, 1); will(returnValue(0.2));
        }});

        double result = DataUtilities.calculateRowTotal(values2D, 0);
        assertEquals(0.3, result, 0.000000001d);
    }

    @Test
    public void calculateRowTotalWithDifferentRows() {
        context.checking(new Expectations() {{
            oneOf(values2D).getColumnCount(); will(returnValue(2));
            oneOf(values2D).getValue(5, 0); will(returnValue(15.0));
            oneOf(values2D).getValue(5, 1); will(returnValue(25.0));
        }});

        double result = DataUtilities.calculateRowTotal(values2D, 5);
        assertEquals(40.0, result, 0.000000001d);
    }

    // --- getCumulativePercentages(KeyedValues data) Tests ---

    @Test
    public void getCumulativePercentagesWithBasicValues() {
        KeyedValues keyedValues = context.mock(KeyedValues.class);
        context.checking(new Expectations() {{
            atLeast(1).of(keyedValues).getItemCount(); will(returnValue(3));
            allowing(keyedValues).getKey(0); will(returnValue("A"));
            allowing(keyedValues).getKey(1); will(returnValue("B"));
            allowing(keyedValues).getKey(2); will(returnValue("C"));
            allowing(keyedValues).getValue(0); will(returnValue(10.0));
            allowing(keyedValues).getValue(1); will(returnValue(20.0));
            allowing(keyedValues).getValue(2); will(returnValue(30.0));
        }});

        KeyedValues result = DataUtilities.getCumulativePercentages(keyedValues);

        // Total = 10 + 20 + 30 = 60
        // Cumulative: 10/60, 30/60, 60/60
        assertEquals(0.16666666666666666, result.getValue(0).doubleValue(), 0.0000001d);
        assertEquals(0.5, result.getValue(1).doubleValue(), 0.0000001d);
        assertEquals(1.0, result.getValue(2).doubleValue(), 0.0000001d);
    }

    @Test
    public void getCumulativePercentagesWithZeroValues() {
        KeyedValues keyedValues = context.mock(KeyedValues.class);
        context.checking(new Expectations() {{
            atLeast(1).of(keyedValues).getItemCount(); will(returnValue(3));
            allowing(keyedValues).getKey(0); will(returnValue("A"));
            allowing(keyedValues).getKey(1); will(returnValue("B"));
            allowing(keyedValues).getKey(2); will(returnValue("C"));
            allowing(keyedValues).getValue(0); will(returnValue(0.0));
            allowing(keyedValues).getValue(1); will(returnValue(0.0));
            allowing(keyedValues).getValue(2); will(returnValue(0.0));
        }});

        KeyedValues result = DataUtilities.getCumulativePercentages(keyedValues);

        // Total = 0 + 0 + 0 = 0
        // Cumulative: 0/0 (undefined), 0/0 (undefined), 0/0 (undefined)
        // However, should handle gracefully without error
        assertTrue(Double.isNaN(result.getValue(0).doubleValue()));
        assertTrue(Double.isNaN(result.getValue(1).doubleValue()));
        assertTrue(Double.isNaN(result.getValue(2).doubleValue()));
    }

    @Test
    public void getCumulativePercentagesWithNullValues() {
        KeyedValues keyedValues = context.mock(KeyedValues.class);
        context.checking(new Expectations() {{
            atLeast(1).of(keyedValues).getItemCount(); will(returnValue(3));
            allowing(keyedValues).getKey(0); will(returnValue("A"));
            allowing(keyedValues).getKey(1); will(returnValue("B"));
            allowing(keyedValues).getKey(2); will(returnValue("C"));
            allowing(keyedValues).getValue(0); will(returnValue(10.0));
            allowing(keyedValues).getValue(1); will(returnValue(null));
            allowing(keyedValues).getValue(2); will(returnValue(30.0));
        }});

        KeyedValues result = DataUtilities.getCumulativePercentages(keyedValues);

        // Total = 10 + 0 + 30 = 40
        // Cumulative: 10/40, 10/40, 40/40
        assertEquals(0.25, result.getValue(0).doubleValue(), 0.0000001d);
        assertEquals(0.25, result.getValue(1).doubleValue(), 0.0000001d);
        assertEquals(1.0, result.getValue(2).doubleValue(), 0.0000001d);
    }

    @Test
    public void getCumulativePercentagesWithEmptyData() {
        KeyedValues keyedValues = context.mock(KeyedValues.class);
        context.checking(new Expectations() {{
            atLeast(1).of(keyedValues).getItemCount(); will(returnValue(0));
        }});

        KeyedValues result = DataUtilities.getCumulativePercentages(keyedValues);

        // No items, so cumulative percentages should also be empty
        assertEquals(0, result.getItemCount());
    }

    @Test
    public void getCumulativePercentagesWithSingleItem() {
        KeyedValues keyedValues = context.mock(KeyedValues.class);
        context.checking(new Expectations() {{
            atLeast(1).of(keyedValues).getItemCount(); will(returnValue(1));
            allowing(keyedValues).getKey(0); will(returnValue("Only"));
            allowing(keyedValues).getValue(0); will(returnValue(50.0));
        }});

        KeyedValues result = DataUtilities.getCumulativePercentages(keyedValues);

        // Total = 50
        // Cumulative: 50/50 = 1.0
        assertEquals(1.0, result.getValue(0).doubleValue(), 0.0000001d);
    }

    @Test
    public void getCumulativePercentagesWithComplexCalculation() {
        KeyedValues keyedValues = context.mock(KeyedValues.class);
        context.checking(new Expectations() {{
            atLeast(1).of(keyedValues).getItemCount(); will(returnValue(5));
            allowing(keyedValues).getKey(0); will(returnValue("Q1"));
            allowing(keyedValues).getKey(1); will(returnValue("Q2"));
            allowing(keyedValues).getKey(2); will(returnValue("Q3"));
            allowing(keyedValues).getKey(3); will(returnValue("Q4"));
            allowing(keyedValues).getKey(4); will(returnValue("Q5"));
            allowing(keyedValues).getValue(0); will(returnValue(12.5));
            allowing(keyedValues).getValue(1); will(returnValue(37.5));
            allowing(keyedValues).getValue(2); will(returnValue(25.0));
            allowing(keyedValues).getValue(3); will(returnValue(15.0));
            allowing(keyedValues).getValue(4); will(returnValue(10.0));
        }});

        KeyedValues result = DataUtilities.getCumulativePercentages(keyedValues);

        // Total = 12.5 + 37.5 + 25.0 + 15.0 + 10.0 = 100.0
        assertEquals(0.125, result.getValue(0).doubleValue(), 0.0000001d);  // 12.5/100 = 0.125
        assertEquals(0.5, result.getValue(1).doubleValue(), 0.0000001d);    // 50.0/100 = 0.5
        assertEquals(0.75, result.getValue(2).doubleValue(), 0.0000001d);   // 75.0/100 = 0.75
        assertEquals(0.9, result.getValue(3).doubleValue(), 0.0000001d);    // 90.0/100 = 0.9
        assertEquals(1.0, result.getValue(4).doubleValue(), 0.0000001d);    // 100.0/100 = 1.0
    }

    // --- createNumberArray(double[] data) Tests ---

    @Test
    public void createNumberArrayWithPositiveValues() {
        double[] input = {1.0, 2.5, 3.14, 4.0};
        Number[] result = DataUtilities.createNumberArray(input);

        assertEquals(4, result.length);
        assertEquals(1.0, result[0].doubleValue(), 0.0000001d);
        assertEquals(2.5, result[1].doubleValue(), 0.0000001d);
        assertEquals(3.14, result[2].doubleValue(), 0.0000001d);
        assertEquals(4.0, result[3].doubleValue(), 0.0000001d);
    }

    @Test
    public void createNumberArrayWithNegativeValues() {
        double[] input = {-1.0, -2.5, -3.14, -4.0};
        Number[] result = DataUtilities.createNumberArray(input);

        assertEquals(4, result.length);
        assertEquals(-1.0, result[0].doubleValue(), 0.0000001d);
        assertEquals(-2.5, result[1].doubleValue(), 0.0000001d);
        assertEquals(-3.14, result[2].doubleValue(), 0.0000001d);
        assertEquals(-4.0, result[3].doubleValue(), 0.0000001d);
    }

    @Test
    public void createNumberArrayWithMixedValues() {
        double[] input = {-1.5, 0.0, 2.5, -3.14, 100.0};
        Number[] result = DataUtilities.createNumberArray(input);

        assertEquals(5, result.length);
        assertEquals(-1.5, result[0].doubleValue(), 0.0000001d);
        assertEquals(0.0, result[1].doubleValue(), 0.0000001d);
        assertEquals(2.5, result[2].doubleValue(), 0.0000001d);
        assertEquals(-3.14, result[3].doubleValue(), 0.0000001d);
        assertEquals(100.0, result[4].doubleValue(), 0.0000001d);
    }

    @Test
    public void createNumberArrayWithZeroValues() {
        double[] input = {0.0, 0.0, 0.0};
        Number[] result = DataUtilities.createNumberArray(input);

        assertEquals(3, result.length);
        assertEquals(0.0, result[0].doubleValue(), 0.0000001d);
        assertEquals(0.0, result[1].doubleValue(), 0.0000001d);
        assertEquals(0.0, result[2].doubleValue(), 0.0000001d);
    }

    @Test
    public void createNumberArrayWithSpecialValues() {
        double[] input = {Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY};
        Number[] result = DataUtilities.createNumberArray(input);

        assertEquals(3, result.length);
        assertTrue(Double.isNaN(result[0].doubleValue()));
        assertEquals(Double.POSITIVE_INFINITY, result[1].doubleValue(), 0.0000001d);
        assertEquals(Double.NEGATIVE_INFINITY, result[2].doubleValue(), 0.0000001d);
    }

    @Test
    public void createNumberArrayWithEmptyArray() {
        double[] input = {};
        Number[] result = DataUtilities.createNumberArray(input);

        assertEquals(0, result.length);
        assertNotNull(result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createNumberArrayThrowsExceptionForNullData() {
    	DataUtilities.createNumberArray(null);
        fail("Expected IllegalArgumentException for null data");
    }

    @Test
    public void createNumberArrayReturnsCorrectType() {
        double[] input = {1.0, 2.0, 3.0};
        Number[] result = DataUtilities.createNumberArray(input);

        assertNotNull(result);
        assertEquals(3, result.length);

        // Verify each element is a Number (specifically Double)
        for (int i = 0; i < result.length; i++) {
            assertNotNull(result[i]);
            assertTrue(result[i] instanceof Number);
            assertTrue(result[i] instanceof Double);
        }
    }

    @Test
    public void createNumberArrayHandlesPrecisionBoundary() {
        double[] input = {Double.MAX_VALUE, Double.MIN_VALUE, Double.MIN_NORMAL};
        Number[] result = DataUtilities.createNumberArray(input);

        assertEquals(3, result.length);
        assertEquals(Double.MAX_VALUE, result[0].doubleValue(), 0.0000001d);
        assertEquals(Double.MIN_VALUE, result[1].doubleValue(), 0.0000001d);
        assertEquals(Double.MIN_NORMAL, result[2].doubleValue(), 0.0000001d);
    }

    @Test
    public void createNumberArrayCreatesIndependentCopy() {
        double[] input = {1.0, 2.0, 3.0};
        Number[] result = DataUtilities.createNumberArray(input);

        // Modify original array
        input[0] = 999.0;

        // Result should be unchanged
        assertEquals(1.0, result[0].doubleValue(), 0.0000001d);
        assertEquals(2.0, result[1].doubleValue(), 0.0000001d);
        assertEquals(3.0, result[2].doubleValue(), 0.0000001d);
    }

    // --- createNumberArray2D(double[][] data) Tests ---

    @Test
    public void createNumberArray2DWithBasicValues() {
        double[][] input = {
            {1.0, 2.0, 3.0},
            {4.0, 5.0, 6.0}
        };
        Number[][] result = DataUtilities.createNumberArray2D(input);

        assertEquals(2, result.length);
        assertEquals(3, result[0].length);
        assertEquals(3, result[1].length);

        // Check first row
        assertEquals(1.0, result[0][0].doubleValue(), 0.0000001d);
        assertEquals(2.0, result[0][1].doubleValue(), 0.0000001d);
        assertEquals(3.0, result[0][2].doubleValue(), 0.0000001d);

        // Check second row
        assertEquals(4.0, result[1][0].doubleValue(), 0.0000001d);
        assertEquals(5.0, result[1][1].doubleValue(), 0.0000001d);
        assertEquals(6.0, result[1][2].doubleValue(), 0.0000001d);
    }

    @Test
    public void createNumberArray2DWithMixedValues() {
        double[][] input = {
            {-1.5, 0.0, 2.5},
            {100.0, -50.0, 25.5}
        };
        Number[][] result = DataUtilities.createNumberArray2D(input);

        assertEquals(2, result.length);
        assertEquals(3, result[0].length);
        assertEquals(3, result[1].length);

        // Verify all values
        assertEquals(-1.5, result[0][0].doubleValue(), 0.0000001d);
        assertEquals(0.0, result[0][1].doubleValue(), 0.0000001d);
        assertEquals(2.5, result[0][2].doubleValue(), 0.0000001d);
        assertEquals(100.0, result[1][0].doubleValue(), 0.0000001d);
        assertEquals(-50.0, result[1][1].doubleValue(), 0.0000001d);
        assertEquals(25.5, result[1][2].doubleValue(), 0.0000001d);
    }

    @Test
    public void createNumberArray2DWithDifferentRowLengths() {
        double[][] input = {
            {1.0, 2.0},
            {3.0, 4.0, 5.0, 6.0},
            {7.0}
        };
        Number[][] result = DataUtilities.createNumberArray2D(input);

        assertEquals(3, result.length);
        assertEquals(2, result[0].length);
        assertEquals(4, result[1].length);
        assertEquals(1, result[2].length);

        // Verify values
        assertEquals(1.0, result[0][0].doubleValue(), 0.0000001d);
        assertEquals(2.0, result[0][1].doubleValue(), 0.0000001d);
        assertEquals(3.0, result[1][0].doubleValue(), 0.0000001d);
        assertEquals(4.0, result[1][1].doubleValue(), 0.0000001d);
        assertEquals(5.0, result[1][2].doubleValue(), 0.0000001d);
        assertEquals(6.0, result[1][3].doubleValue(), 0.0000001d);
        assertEquals(7.0, result[2][0].doubleValue(), 0.0000001d);
    }

    @Test
    public void createNumberArray2DWithEmptyArray() {
        double[][] input = {};
        Number[][] result = DataUtilities.createNumberArray2D(input);

        assertEquals(0, result.length);
        assertNotNull(result);
    }

    @Test
    public void createNumberArray2DWithSpecialValues() {
        double[][] input = {
            {Double.NaN, Double.POSITIVE_INFINITY},
            {Double.NEGATIVE_INFINITY, 0.0}
        };
        Number[][] result = DataUtilities.createNumberArray2D(input);

        assertEquals(2, result.length);
        assertEquals(2, result[0].length);
        assertEquals(2, result[1].length);

        assertTrue(Double.isNaN(result[0][0].doubleValue()));
        assertEquals(Double.POSITIVE_INFINITY, result[0][1].doubleValue(), 0.0000001d);
        assertEquals(Double.NEGATIVE_INFINITY, result[1][0].doubleValue(), 0.0000001d);
        assertEquals(0.0, result[1][1].doubleValue(), 0.0000001d);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createNumberArray2DThrowsExceptionForNullData() {
        
        DataUtilities.createNumberArray2D(null);
        fail("Expected IllegalArgumentException for null data");
    }

    @Test
    public void createNumberArray2DReturnsCorrectType() {
        double[][] input = {{1.0, 2.0}, {3.0, 4.0}};
        Number[][] result = DataUtilities.createNumberArray2D(input);

        assertNotNull(result);
        assertEquals(2, result.length);

        // Verify each row is a Number array
        for (int i = 0; i < result.length; i++) {
            assertNotNull(result[i]);
            assertTrue(result[i] instanceof Number[]);
            for (int j = 0; j < result[i].length; j++) {
                assertNotNull(result[i][j]);
                assertTrue(result[i][j] instanceof Number);
                assertTrue(result[i][j] instanceof Double);
            }
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void createNumberArray2DWithNullInnerArrays() {
        double[][] input = {null, {1.0, 2.0}, null};
        
        DataUtilities.createNumberArray2D(input);
        fail("Expected IllegalArgumentException for null inner array");        
    }

    @Test
    public void createNumberArray2DCreatesIndependentCopy() {
        double[][] input = {{1.0, 2.0}, {3.0, 4.0}};
        Number[][] result = DataUtilities.createNumberArray2D(input);

        // Modify original array
        input[0][0] = 999.0;
        input[1] = new double[]{777.0, 888.0};

        // Result should be unchanged
        assertEquals(1.0, result[0][0].doubleValue(), 0.0000001d);
        assertEquals(2.0, result[0][1].doubleValue(), 0.0000001d);
        assertEquals(3.0, result[1][0].doubleValue(), 0.0000001d);
        assertEquals(4.0, result[1][1].doubleValue(), 0.0000001d);
    }

    @Test
    public void createNumberArray2DWithExtremeBoundaryValues() {
        double[][] input = {
            {Double.MAX_VALUE, Double.MIN_VALUE},
            {Double.MIN_NORMAL, -Double.MAX_VALUE}
        };
        Number[][] result = DataUtilities.createNumberArray2D(input);

        assertEquals(2, result.length);
        assertEquals(2, result[0].length);
        assertEquals(2, result[1].length);

        assertEquals(Double.MAX_VALUE, result[0][0].doubleValue(), 0.0000001d);
        assertEquals(Double.MIN_VALUE, result[0][1].doubleValue(), 0.0000001d);
        assertEquals(Double.MIN_NORMAL, result[1][0].doubleValue(), 0.0000001d);
        assertEquals(-Double.MAX_VALUE, result[1][1].doubleValue(), 0.0000001d);
    }

    // --- calculateColumnTotal(Values2D data, int column, int[] validRows) Tests ---

    @Test
    public void calculateColumnTotal_WithValidRows_ShouldSumOnlyValidRows() {
        context.checking(new Expectations() {{
            oneOf(values2D).getRowCount(); will(returnValue(3));
            oneOf(values2D).getValue(0, 0); will(returnValue(5.0));
            oneOf(values2D).getValue(2, 0); will(returnValue(2.0));
        }});
        int[] validRows = {0, 2};
        double result = DataUtilities.calculateColumnTotal(values2D, 0, validRows);
        assertEquals(7.0, result, 0.000000001d);
    }

    @Test
    public void calculateColumnTotal_WithEmptyValidRows_ShouldReturnZero() {
        context.checking(new Expectations() {{
            oneOf(values2D).getRowCount(); will(returnValue(2));
        }});
        int[] validRows = {};
        double result = DataUtilities.calculateColumnTotal(values2D, 0, validRows);
        assertEquals(0.0, result, 0.000000001d);
    }

    @Test
    public void calculateColumnTotal_WithRowOutOfRange_ShouldIgnoreIt() {
        context.checking(new Expectations() {{
            oneOf(values2D).getRowCount(); will(returnValue(2));
            oneOf(values2D).getValue(0, 0); will(returnValue(4.0));
        }});
        int[] validRows = {0, 5};
        double result = DataUtilities.calculateColumnTotal(values2D, 0, validRows);
        assertEquals(4.0, result, 0.000000001d);
    }

    @Test
    public void calculateColumnTotal_WithNullValueInValidRows_ShouldSkipNull() {
        context.checking(new Expectations() {{
            oneOf(values2D).getRowCount(); will(returnValue(2));
            oneOf(values2D).getValue(0, 0); will(returnValue(null));
            oneOf(values2D).getValue(1, 0); will(returnValue(3.0));
        }});
        int[] validRows = {0, 1};
        double result = DataUtilities.calculateColumnTotal(values2D, 0, validRows);
        assertEquals(3.0, result, 0.000000001d);
    }

    @Test
    public void calculateColumnTotal_WithAllValidRows_ShouldSumAll() {
        context.checking(new Expectations() {{
            oneOf(values2D).getRowCount(); will(returnValue(3));
            oneOf(values2D).getValue(0, 0); will(returnValue(1.0));
            oneOf(values2D).getValue(1, 0); will(returnValue(2.0));
            oneOf(values2D).getValue(2, 0); will(returnValue(3.0));
        }});
        int[] validRows = {0, 1, 2};
        double result = DataUtilities.calculateColumnTotal(values2D, 0, validRows);
        assertEquals(6.0, result, 0.000000001d);
    }

    @Test(expected = IllegalArgumentException.class)
    public void calculateColumnTotal_WithValidRowsNullData_ShouldThrowException() {
        
        DataUtilities.calculateColumnTotal(null, 0, new int[]{0});
        fail("Expected IllegalArgumentException for null data");
    }

    // --- calculateRowTotal(Values2D data, int row, int[] validCols) Tests ---

    @Test
    public void calculateRowTotal_WithValidCols_ShouldSumOnlyValidCols() {
        context.checking(new Expectations() {{
            oneOf(values2D).getColumnCount(); will(returnValue(3));
            oneOf(values2D).getValue(0, 0); will(returnValue(5.0));
            oneOf(values2D).getValue(0, 2); will(returnValue(2.0));
        }});
        int[] validCols = {0, 2};
        double result = DataUtilities.calculateRowTotal(values2D, 0, validCols);
        assertEquals(7.0, result, 0.000000001d);
    }

    @Test
    public void calculateRowTotal_WithEmptyValidCols_ShouldReturnZero() {
        context.checking(new Expectations() {{
            oneOf(values2D).getColumnCount(); will(returnValue(2));
        }});
        int[] validCols = {};
        double result = DataUtilities.calculateRowTotal(values2D, 0, validCols);
        assertEquals(0.0, result, 0.000000001d);
    }

    @Test
    public void calculateRowTotal_WithColOutOfRange_ShouldIgnoreIt() {
        context.checking(new Expectations() {{
            oneOf(values2D).getColumnCount(); will(returnValue(2));
            oneOf(values2D).getValue(0, 0); will(returnValue(4.0));
        }});
        int[] validCols = {0, 5};
        double result = DataUtilities.calculateRowTotal(values2D, 0, validCols);
        assertEquals(4.0, result, 0.000000001d);
    }

    @Test
    public void calculateRowTotal_WithNullValueInValidCols_ShouldSkipNull() {
        context.checking(new Expectations() {{
            oneOf(values2D).getColumnCount(); will(returnValue(2));
            oneOf(values2D).getValue(0, 0); will(returnValue(null));
            oneOf(values2D).getValue(0, 1); will(returnValue(3.0));
        }});
        int[] validCols = {0, 1};
        double result = DataUtilities.calculateRowTotal(values2D, 0, validCols);
        assertEquals(3.0, result, 0.000000001d);
    }

    @Test
    public void calculateRowTotal_WithAllValidCols_ShouldSumAll() {
        context.checking(new Expectations() {{
            oneOf(values2D).getColumnCount(); will(returnValue(3));
            oneOf(values2D).getValue(0, 0); will(returnValue(1.0));
            oneOf(values2D).getValue(0, 1); will(returnValue(2.0));
            oneOf(values2D).getValue(0, 2); will(returnValue(3.0));
        }});
        int[] validCols = {0, 1, 2};
        double result = DataUtilities.calculateRowTotal(values2D, 0, validCols);
        assertEquals(6.0, result, 0.000000001d);
    }

    @Test(expected = IllegalArgumentException.class)
    public void calculateRowTotal_WithValidColsNullData_ShouldThrowException() {

        DataUtilities.calculateRowTotal(null, 0, new int[]{0});
        fail("Expected IllegalArgumentException for null data");        
    }

    // --- equal(double[][] a, double[][] b) Tests ---

    @Test
    public void equal_TwoEqualArrays_ShouldReturnTrue() {
        double[][] a = {{1.0, 2.0}, {3.0, 4.0}};
        double[][] b = {{1.0, 2.0}, {3.0, 4.0}};
        assertTrue(DataUtilities.equal(a, b));
    }

    @Test
    public void equal_TwoDifferentArrays_ShouldReturnFalse() {
        double[][] a = {{1.0, 2.0}};
        double[][] b = {{3.0, 4.0}};
        assertFalse(DataUtilities.equal(a, b));
    }

    @Test
    public void equal_BothNull_ShouldReturnTrue() {
        assertTrue(DataUtilities.equal(null, null));
    }

    @Test
    public void equal_FirstNull_ShouldReturnFalse() {
        double[][] b = {{1.0, 2.0}};
        assertFalse(DataUtilities.equal(null, b));
    }

    @Test
    public void equal_SecondNull_ShouldReturnFalse() {
        double[][] a = {{1.0, 2.0}};
        assertFalse(DataUtilities.equal(a, null));
    }

    @Test
    public void equal_DifferentLengths_ShouldReturnFalse() {
        double[][] a = {{1.0, 2.0}, {3.0, 4.0}};
        double[][] b = {{1.0, 2.0}};
        assertFalse(DataUtilities.equal(a, b));
    }

    // --- clone(double[][] source) Tests ---

    @Test
    public void clone_ValidArray_ShouldReturnEqualArray() {
        double[][] source = {{1.0, 2.0}, {3.0, 4.0}};
        double[][] result = DataUtilities.clone(source);
        assertTrue(DataUtilities.equal(source, result));
    }

    @Test
    public void clone_ValidArray_ShouldReturnDifferentObject() {
        double[][] source = {{1.0, 2.0}, {3.0, 4.0}};
        double[][] result = DataUtilities.clone(source);
        assertNotSame(source, result);
    }

    @Test
    public void clone_ArrayWithNullRow_ShouldHandleNull() {
        double[][] source = new double[2][];
        source[0] = new double[]{1.0, 2.0};
        source[1] = null;
        double[][] result = DataUtilities.clone(source);
        assertNull(result[1]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void clone_NullData_ShouldThrowException() {
        
        DataUtilities.clone(null);
        fail("Expected IllegalArgumentException for null data");
    }

    @Test
    public void clone_EmptyArray_ShouldReturnEmptyArray() {
        double[][] source = {};
        double[][] result = DataUtilities.clone(source);
        assertEquals(0, result.length);
    }

}
