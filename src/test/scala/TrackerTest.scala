import org.scalatest.funsuite.AnyFunSuite
import entities.{Database, Task, Tracker}
import helpers.UUIDGenerator

class TrackerTest extends AnyFunSuite {
  test("tracker task add") {
    val inputs = Iterator(
      "add task1 description 1",
      "add task2 description",
      "add task3",
      "list",
      "close"
    )
    val mockInputSource = () => if (inputs.hasNext) inputs.next() else ""
    val tracker = new Tracker(mockInputSource)
    val trackerDb: Array[Task] = tracker.dumpStorage
    val expectedDb: Array[Task] = new Array[Task](3)
    expectedDb :+ new Task(name = "task1", description = "description", deadline = 1, uuid = UUIDGenerator.generate())
    expectedDb :+ new Task(name = "task1",
      description = "description",
      deadline = 1,
      uuid = UUIDGenerator.generate()
    )


  }

  test("example test - string concatenation works") {
    val greeting = "Hello" + " " + "World"
    assert(greeting == "Hello World")
  }
}
