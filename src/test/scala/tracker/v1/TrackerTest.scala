package tracker.v1

import helpers.UUIDGenerator
import org.scalatest.BeforeAndAfterAll
import org.scalatest.funsuite.AnyFunSuite

import java.io.{ByteArrayOutputStream, PrintStream}

class TrackerTest extends AnyFunSuite with BeforeAndAfterAll {

  val inputs: Iterator[String] = Iterator(
    "add task1 description 1",
    "add task2 description",
    "add task3",
    "list",
    "close"
  )

  val finishInputs: Iterator[String] = Iterator(
    "add task1 description 1",
    "add task2 description",
    "add task3",
    "finish task2",
    "finish task3",
    "check",
    "close"
  )

  val deleteInputs: Iterator[String] = Iterator(
    "add task1 description 1",
    "add task2 description",
    "add task3",
    "delete task2",
    "delete task3",
    "list",
    "close"
  )

  override def beforeAll(): Unit = {}

  test("tracker task add test") {
    val mockInputSource: () => String =
      () => if (inputs.hasNext) inputs.next() else ""
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

  test("tracker task finish and check test") {
    val mockInputSource: () => String =
      () => if (finishInputs.hasNext) finishInputs.next() else ""
    val outputStream = new ByteArrayOutputStream()
    Console.withOut(new PrintStream(outputStream)) {
      val tracker = new Tracker(mockInputSource)
      tracker.run()
    }

    // Convert the captured output to a string
    val output = outputStream.toString

    // Assert the expected output
    assert(
      output.contains(
        "Adding task: Name = 'task1', Description = 'description', Deadline = '1'..."
      )
    )
    assert(
      output.contains(
        "Adding task: Name = 'task2', Description = 'description'..."
      )
    )
    assert(output.contains("Adding task: Name = 'task3'..."))
    assert(output.contains("[x] task2"))
    assert(output.contains("[x] task3"))
  }

  test("tracker task delete list test") {
    val mockInputSource: () => String =
      () => if (deleteInputs.hasNext) deleteInputs.next() else ""
    val outputStream = new ByteArrayOutputStream()
    Console.withOut(new PrintStream(outputStream)) {
      val tracker = new Tracker(mockInputSource)
      tracker.run()
    }

    // Convert the captured output to a string
    val output = outputStream.toString

    // Assert the expected output
    assert(
      output.contains(
        "Adding task: Name = 'task1', Description = 'description', Deadline = '1'..."
      )
    )
    assert(
      output.contains(
        "Adding task: Name = 'task2', Description = 'description'..."
      )
    )
    assert(output.contains("Adding task: Name = 'task3'..."))
    assert(output.contains("Deleting task task2..."))
    assert(output.contains("Deleting task task3..."))
    assert(output.contains("[ ] Task task1: description [1]"))
  }
}
