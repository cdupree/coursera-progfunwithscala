package funsets

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
 * This class is a test suite for the methods in object FunSets. To run
 * the test suite, you can either:
 *  - run the "test" command in the SBT console
 *  - right-click the file in eclipse and chose "Run As" - "JUnit Test"
 */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {


  /**
   * Link to the scaladoc - very clear and detailed tutorial of FunSuite
   *
   * http://doc.scalatest.org/1.9.1/index.html#org.scalatest.FunSuite
   *
   * Operators
   *  - test
   *  - ignore
   *  - pending
   */

  /**
   * Tests are written using the "test" operator and the "assert" method.
   */
  test("string take") {
    val message = "hello, world"
    assert(message.take(5) == "hello")
  }

  /**
   * For ScalaTest tests, there exists a special equality operator "===" that
   * can be used inside "assert". If the assertion fails, the two values will
   * be printed in the error message. Otherwise, when using "==", the test
   * error message will only say "assertion failed", without showing the values.
   *
   * Try it out! Change the values so that the assertion fails, and look at the
   * error message.
   */
  test("adding ints") {
    assert(1 + 2 === 3)
  }

  
  import FunSets._

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }
  
  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   * 
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   * 
   *   val s1 = singletonSet(1)
   * 
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   * 
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   * 
   */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
  }

  /**
   * This test is currently disabled (by using "ignore") because the method
   * "singletonSet" is not yet implemented and the test would fail.
   * 
   * Once you finish your implementation of "singletonSet", exchange the
   * function "ignore" by "test".
   */
  test("singletonSet(1) contains 1") {
    
    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3". 
     */
    new TestSets {
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton")
    }
  }

  test("union contains all elements") {
    new TestSets {
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
    }
  }
  
  test("intersection contains only common elements") {
    new TestSets {
      val i = intersect(s1, s2)
      assert(!contains(i,1))
      assert(!contains(i,2))
      val u = union(s1,s2)
      val i2 = intersect(u,s1)
      assert(contains(i2,1))
    }
  }
  
  test("difference contains only elements in first, and not in second") {
    new TestSets {
      val u = union(s1,s2)
      val d = diff(u,s1)
      assert(!contains(d,1))
      assert(contains(d,2))
    }
  }
  
   test("filter returns only odds") {
    new TestSets {
      val u = union(s1,s2)
      val u2 = union(u,s3)
      assert(contains(u2,1))
      assert(contains(u2,2))
      assert(contains(u2,3))
      val f = filter(u2, (x: Int) => x % 2 != 0)
      assert(contains(f,1))
      assert(contains(f,3))
      assert(!contains(f,2))
    }
  }
   
   test("forall: set contains only contains odds") {
    new TestSets {
      val u = union(s1,s3)
      val u2 = union(u,s2)
      assert(!forall(u2, (x: Int) => x % 2 != 0)) // contains 2
      assert(forall(u, (x: Int) => x % 2 != 0))
    }
  }
   
   test("exists: set contains an even") {
    new TestSets {
      val u = union(s1,s3)
      val u2 = union(u,s2)
      assert(exists(u2, (x: Int) => x % 2 == 0)) // contains 2
      assert(!exists(u, (x: Int) => x % 2 == 0))
    }
  }
   
   test("map: set is multipled by 2") {
    new TestSets {
      val u = union(s1,s3)
      val m = map(u, (x: Int) => 2 * x)
      assert(contains(m,2))
      assert(contains(m,6))
      assert(!contains(m,1))
      assert(!contains(m,3))
    }
  }
   
    test("sets are just boolenas"){
	   def evens: Set = x => x % 2 == 0
	   //assert(true)
	   assert(contains(evens,42))
	   assert(!contains(evens,41))
	   assert(forall(evens, (x: Int) => x%2 == 0))
   }
   
  
  
}
