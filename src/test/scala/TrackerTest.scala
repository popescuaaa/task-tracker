import org.scalatest.funsuite.AnyFunSuite

class TrackerTest extends AnyFunSuite{
  test("example test - addition works") {
    val sum = 2 + 2
    assert(sum == 4)
  }

  test("example test - string concatenation works") {
    val greeting = "Hello" + " " + "World"
    assert(greeting == "Hello World")
  }
}
