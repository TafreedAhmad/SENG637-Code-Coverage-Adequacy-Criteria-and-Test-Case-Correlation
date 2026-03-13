**SENG 637 - Dependability and Reliability of Software Systems**

**Lab. Report #3 – Code Coverage, Adequacy Criteria and Test Case Correlation**

| Group: 8              |
|-----------------------|
| Student 1 **Mark**    |   
| Student 2 **Zoe**     |   
| Student 3 **Heena**   |   
| Student 4 **Tafreed** | 

(Note that some labs require individual reports while others require one report
for each group. Please see each lab document for details.)

# 1 Introduction

The primary objective of this laboratory assignment is to explore the concepts of white-box testing and test adequacy through the application of code coverage metrics. While the previous assignment focused on requirements-based (black-box) testing, this phase shifts the focus toward the internal structure of the software to ensure that the test suite comprehensively exercises the source code.

By utilizing the JFreeChart framework as the System Under Test (SUT), this lab aims to bridge the gap between functional testing and structural analysis. The transition to white-box testing allows for a more granular assessment of the test suite's effectiveness by measuring exactly which statements, branches, and conditions are executed during test runs.

### 1.1 Scope and Objectives
The core goals of this activity include:

- **Metric Instrumentation**: Utilizing industry-standard tools (primarily EclEmma) to measure Statement, Branch, and Condition coverage.

- **Test Suite Enhancement**: Designing and implementing new JUnit test cases to meet specific coverage targets: 90% for Statement, 70% for Branch, and 60% for Condition coverage.

- **Data-Flow Analysis**: Performing manual calculations of Definition-Use (DU) pairs to gain a deeper theoretical understanding of how data moves through the logic of a program.

- **Tool Evaluation**: Critically assessing the integration, usability, and reporting capabilities of various Java-based coverage utilities within the Eclipse IDE.

### 1.2 Methodology
The team followed a structured approach beginning with the instrumentation of the original test suite developed in Assignment #2. Upon identifying coverage gaps, we employed a logic-driven strategy to craft new test inputs that force execution through previously unvisited code paths, such as exception handlers and complex conditional blocks. This process highlights the trade-offs between testing based purely on user requirements versus testing based on the "hidden" internal logic of the developer's implementation.

# 2 Manual Data-Flow Coverage Calculations


### Method 1: DataUtilities.calculateColumnTotal(Values2D data, int column) 
#### i. Data Flow Graph (DFG)

![DataUtilities_DFG](./assets/DataUtilities_DFG.png)

#### ii. Def-Use Sets Per Statement

| Statement     | def(s)                  | use(s)               |
| ------------- | ------------------------|--------------------- |
| 1             |  {data, column}         | None                 |
| 2             |  None                   | {data}               |
| 3             |  {total, rowCount, r}   | {data}               |
| 4             |  None                   | {r, rowCount}        |
| 5             |  {n}                    | {data, r, column, n} |
| 6             |  {total}                | {total, n}           |
| 7             |  {r}                    | {r}                  |
| 8             |  None                   | {total}              |

#### iii. DU-Pairs per Variable
| Variables     | **Def-Use pairs**                                 |
| ------------- | ------------------------------------------------- |
| data          | (1, 2), (1, 3), (1, 5)                            |
| column        | (1, 5)                                            |
| total         | (3, 6), (3, 8), (6, 6), (6, 8)                    |
| rowCount      | (3, 4)                                            |
| r             | (3, 4), (3, 5), (3, 7), (7, 4), (7, 5), (7, 7)    |
| n             | (5, 5), (5, 6)                                    |

#### iv. Test Cases Def-Use Pairs Covered

### Method 2: Range class' contains(double)

#### i. Data Flow Graph (DFG)

![Range_DFG](./assets/Range_contains_DFG.png)

#### ii. Def-Use Sets Per Statement

| Node \# | statement | DEF  | p-use | c-use |
| :---- | :---- | :---- | :---- | :---- |
| START | public boolean contains(double value) | {value, lower, upper} | None | None |
| 1 | if (value \< this.lower) | None | {value, lower} | None |
| 2 | if (value \> this.upper) | None | {value, upper} | None |
| 3 | value \>= this.lower | None | {value, lower} | None |
| 4 | value \<= this.upper | None | {value, upper} | None |

#### iii. DU-Pairs per Variable

Variable: value

| DU-pair | USE | Path |
| :---- | :---- | :---- |
| DU1 | Node 1 | 1  |
| DU2 | Node 2 | 1 → 2  |
| DU3 | Node 3 | 1 → 2 → 3  |
| DU4 | Node 4 | 1 → 2 → 3 → 4  |

Variable: lower

| DU-pair | USE | Path |
| :---- | :---- | :---- |
| DU5 | Node 1 | 1  |
| DU6 | Node 3 | 1 → 2 → 3  |

Variable: upper

| DU-pair | USE | Path |
| :---- | :---- | :---- |
| DU7 | Node 2 | 1 → 2  |
| DU8 | Node 4 | 1 → 2 → 3 → 4  |

**iv. Test Cases Def-Use Pairs Coverage**

| Test case | path | DU pairs covered |
| :---- | :---- | :---- |
| testContains\_ValueBelowLower() | 1   | DU1, DU5 |
| testContains\_ValueAboveUpper() | 1 → 2  | DU1, DU2, DU5, DU7 |
| testContains\_ValueInside() | 1 → 2 → 3 → 4  | DU1, DU2, DU3, DU4, DU5, DU6, DU7, DU8 |
| testContains\_NaNValue() | 1 → 2 → 3  | DU1, DU2, DU3, DU5, DU6, DU7 |

# DU-pair coverage \= DU covered/Total DU \= 8/8 \= 100%

# 3 A detailed description of the testing strategy for the new unit test

Text…

# 4 High Level Description of Five Selected Test Cases 

Test cases that was designed using coverage information, and how they have increased code coverage:

1. **equals(Object)**: The test testEqualsObjectNotRange() verifies the behavior of the equals method when the compared object is not an instance of a Range. Here in this test, a Range object is compared with a String and the expected result is false. This test increases coverage by executing the branch in the equals method that confirms whether the object is an instance of Range. Without this test , the type checking branch can not be exercised.
2. **combineIgnoringNaN(Range, Range)**: The test testCombineIgnoringNaN_Range1Null() verifies the behaviour when the first range parameter is null and the method should return the second valid range. This test confirms that the input value is expected for null branch handling.The test increases coverage of conditional logic that validates method inputs.
3. **isNaNRange()**: The test testCombineIgnoringNaN_bothNaNBounds() creates two ranges where both upper and lower bound are NaN. When passed to combineIgnoringNaN the method internally calls isNaNRange() method to detect invalid ranges. This test ensures that the branch is executed where both ranges are considered NaN and increases coverage for both combineIgnoringNaN and the internal helper method is isNaNRange().
4. **shiftWithNoZeroCrossing(double, double)**: The test testShiftPositiveCrossZero() confirms that when shifting a positive range by a negative range delta that would normally cross zero. Since the zero crossing is disabled in this case, the value is clamped to zero. This test increases the branch coverage for conditional logic that restricts values from crossing zero when shifting ranges.
5. **expand(Range, double, double)**: The test testExpandBoundCross() evaluates the case where large negative margins cause calculated lower bound to exceed the upper bound. The implementation adjusts both bounds to midpoint when this occurs. This test improves coverage by executing the conditional branch that handles invalid expansion scenarios where the computed bounds cross.

# 5 Detailed Report of the Coverage Achieved of each Class and Method 

### **Range class:**

![DataUtilities_LineCoverage](./assets/Range_LineCoverage.png)
*Figure: Line Coverage*

![DataUtilities_LineCoverage](./assets/Range_BranchCoverage.png)
*Figure: Branch Coverage*

![DataUtilities_LineCoverage](./assets/Range_MethodCoverage.png)
*Figure: Method Coverage*

---


### **DataUtilities class:**

![DataUtilities_LineCoverage](./assets/calculateColumnTotal_coveredLines.png)
*Figure: Line Coverage*

![DataUtilities_BranchCoverage](./assets/calculateColumnTotal_coveredBranches.png)
*Figure: Branch Coverage*

![DataUtilities_MethodCoverage](./assets/calculateColumnTotal_coveredMethods.png)
*Figure: Method Coverage*

# 6 Pros and Cons of coverage tools used and Metrics Utilized

## **Coverage Tool \- EclEmma**

### **Pros of Using EclEmma**

* **Fast Feedback Loop:** EclEmma brings coverage analysis directly into the workbench. You can launch tests in "**Coverage mode**" just as easily as you would Run or Debug, with results appearing immediately after the test run.  
* **Intuitive Visual Highlighting:** It features **source code highlighting** that colors your lines: **Green** for fully covered, **Yellow** for partially covered (e.g., one branch of an if-statement), and **Red** for no coverage.  
* **Non-Invasive Architecture:** It does not require you to modify your source code or project configuration. It works by instrumenting **Java bytecode** on the fly as it runs.  
* **Comprehensive Metrics:** Beyond simple line coverage, it provides data on **instructions, branches, cyclomatic complexity**, methods, and classes.  
* **Session Management:** You can **merge multiple coverage sessions** (e.g., combining unit and integration test results) to see the total coverage across different test suites.  
* **Seamless Integration:** It supports all standard Eclipse launch types, including **JUnit**, **TestNG**, and local Java applications. It also allows for importing/exporting JaCoCo execution data (\*.exec) for use with CI/CD tools like SonarQube.

### **Cons of Using EclEmma**

* **Performance Impact:** Coverage runs can be significantly **slower than standard runs**, especially when profiling large test suites or enterprise-scale monorepos, as the tool must track every instruction.  
* **Limited Language Support:** EclEmma is strictly for **Java-based** languages (like Java and Scala). It does not support other languages within Eclipse, such as C++ or JavaScript.  
* **IDE Dependency:** While the underlying JaCoCo engine is flexible, the EclEmma plugin is specifically for **interactive, local developer use**. For automated builds, you must still configure separate integrations like the JaCoCo Maven or Gradle plugins.  
* **False Sense of Security:** Like all coverage tools, it measures **code execution**, not **test quality**. High coverage does not guarantee that the logic is correct or that the tests actually assert the right outcomes.  
* **Complex Scenarios:** Some users report discrepancies when working with **multi-module Maven projects**, where tests in one module cover classes in another; this often requires manual configuration to merge execution data correctly.
### **Metrics**

#### **1\. Line Coverage**

This measures the percentage of executable lines of code that have been run at least once by your test suite.

* **Pros:**  
  * **Extremely intuitive:** It is easy to visualize and map directly to your source code using tools like EclEmma.  
  * **Identifies "Dead Code":** Quickly highlights entire blocks of code that are never touched, helping you find unused logic.  
  * **Low Overhead:** It is computationally the simplest metric to track.  
* **Cons:**  
  * **Misleading Accuracy:** A single line of code can contain multiple logical operations (e.g., a ternary operator or complex boolean). Line coverage will mark the line "covered" even if only one part of the logic was tested.  
  * **False Sense of Security:** Achieving 100% line coverage does not mean all logic paths are tested; it only means every line was stepped into.

#### **2\. Branch Coverage**

This measures whether every possible path (true/false) in a control structure—like `if`, `switch`, or `while` statements—has been executed.

* **Pros:**  
  * **Higher Logical Rigor:** It forces you to test both the "success" and "failure" paths of every decision point.  
  * **Catches Edge Cases:** Often reveals missing requirements, such as what happens when a specific condition is *not* met.  
  * **More Granular:** It is more precise than line coverage for complex methods with many nested decisions.  
* **Cons:**  
  * **Increased Complexity:** Writing tests for every branch combination is significantly more time-consuming.  
  * **Path Explosion:** In methods with many independent `if` statements, the number of branches can grow quickly, making 100% coverage difficult to maintain.

#### **3\. Method Coverage**

This measures the percentage of methods (functions) in your codebase that have been called at least once during testing.

* **Pros:**  
  * **High-Level Overview:** Useful for large legacy systems to see which major features or modules are completely untested.  
  * **Low Effort for High Visibility:** It is very easy to achieve 100% method coverage, providing a quick "entry-level" health check.  
* **Cons:**  
  * **Lack of Depth:** A method is "covered" even if the test only enters the first line and then returns. It says nothing about the quality of the testing *inside* the function.  
  * **Vulnerable to "Liar" Tests:** A single integration test that triggers many methods can result in high method coverage while ignoring the internal logic of those methods.


# 7 A comparison on the advantages and disadvantages of requirements-based test generation and coverage-based test generation.

#### Requirements-based:
**Pros:** Assesses the software from the user's perspective, making it very effective at identifying missing functional requirements. Does not require understanding complex source code, enabling testers to focus on behaviour rather than implementation.
**Cons:** Incapable of uncovering hidden bugs.


#### Coverage-based :
**Pros:** It forces testers to examine every line of code and every conditional branch.
**Cons:** Getting 100% coverage is time-consuming and often leads to wasting time on infeasible paths



# 8 A discussion on how the team work/effort was divided and managed

<table>
  <tr>
    <th>Method</th>
    <th>Member</th>
  </tr>
  <tr>
    <td colspan="2" style="text-align:center"><strong>Range</strong></td>
  </tr>
  <tbody>
    <tr><td>getLowerBound()</td><td>Zoe</td></tr>
    <tr><td>getUpperBound()</td><td>Zoe</td></tr>
    <tr><td>getLength()</td><td>Zoe</td></tr>
    <tr><td>getCentralValue()</td><td>Zoe</td></tr>
    <tr><td>contains(double value)</td><td>Zoe</td></tr>
    <tr><td>intersects(double, double)</td><td>Zoe</td></tr>
    <tr><td>combineIgnoringNaN(Range, Range)</td><td>Zoe, Heena</td></tr>
    <tr><td>min(double, double)</td><td>Zoe</td></tr>
    <tr><td>max(double, double)</td><td>Zoe</td></tr>
    <tr><td>constrain(double)</td><td>Zoe</td></tr>
    <tr><td>isNaNRange()</td><td>Zoe, Heena</td></tr>
    <tr><td>toString()</td><td>Tafreed</td></tr>
    <tr><td>hashCode()</td><td>Tafreed</td></tr>
    <tr><td>equals(Range)</td><td>Heena</td></tr>
    <tr><td>shiftWithNoZeroCrossing()</td><td>Heena</td></tr>
    <tr><td>shift(Range, double)</td><td>Tafreed</td></tr>
    <tr><td>scale(Range, double)</td><td>Tafreed</td></tr>
    <tr><td>expandToInclude(Range, double)</td><td>Tafreed</td></tr>
    <tr><td>combine(Range, Range)</td><td>Tafreed</td></tr>
    <tr><td>expand(Range, double, double)</td><td>Heena</td></tr>
    <tr><td colspan="2" style="text-align:center"><strong>DataUtilities</strong></td></tr>
    <tr><td>equal(double[][], double[][])</td><td>Tafreed</td></tr>
    <tr><td>clone(double[][])</td><td>Tafreed</td></tr>
    <tr><td>calculateColumnTotal(Values2D, int)</td><td>Mark</td></tr>
    <tr><td>calculateColumnTotal(Values2D, int, int[])</td><td>Tafreed</td></tr>
    <tr><td>calculateRowTotal(Values2D, int)</td><td>Mark</td></tr>
    <tr><td>calculateRowTotal(Values2D, int, int[])</td><td>Tafreed</td></tr>
    <tr><td>createNumberArray(double[])</td><td>Mark</td></tr>
    <tr><td>createNumberArray2D(double[][])</td><td>Mark</td></tr>
    <tr><td>getCumulativePercentages(KeyedValues)</td><td>Mark</td></tr>
  </tbody>
</table>


# 9 Any difficulties encountered, challenges overcome, and lessons learned from performing the lab

Given we have access to the source code and in the source code, there is an infeasible path / Dead Code block / section specially in DataUtilities class. It is a bit confusing if we will fix that or leave it in. But we believe that as a unit tester coder, when you have access to the code, it is important to keep it as short and simple and also keep it clean. Unit test cases based on the source code are worked on by the developers that have the right to optimize the code and therefore we must optimize it whenever possible and alway avoid having clutters.

# 10 Comments/feedback on the lab itself

Text…
