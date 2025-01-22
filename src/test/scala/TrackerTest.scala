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
    tracker.run()
    val trackerDb: Array[Task] = tracker.dumpStorage
    var expectedDb: Array[Task] = new Array[Task](0)

    expectedDb = expectedDb :+ new Task(
      name = "task1",
      description = "description",
      deadline = 1,
      uuid = UUIDGenerator.generate()
    )
    expectedDb = expectedDb :+ new Task(
      name = "task2",
      description = "description",
      deadline = null,
      uuid = UUIDGenerator.generate()
    )
    expectedDb = expectedDb :+ new Task(
      name = "task3",
      description = "",
      deadline = null,
      uuid = UUIDGenerator.generate()
    )

    assert(
      trackerDb
        .map(task => (task.name, task.state, task.description, task.deadline))
        .sameElements(
          expectedDb.map(task =>
            (task.name, task.state, task.description, task.deadline)
          )
        )
    )
  }

  test("example test - string concatenation works") {
    val greeting = "Hello" + " " + "World"
    assert(greeting == "Hello World")
  }
}
