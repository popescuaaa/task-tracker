package tracker.v1

import tracker.commons.AbstractTracker

/** The main app class
  * The task tracker has the input source decoupled from the logic.
  */
class Tracker(inputSource: () => String) extends AbstractTracker(inputSource) {
  private val database: Database = new Database()

  override protected def handleFinishCommand(args: String): Unit = {
    val taskName: String = args.split("\\s", 1)(0)
    database.finishTask(name = taskName)
  }

  override protected def handleCheckCommand(): Unit = {
    println("Checking the finished tasks...")
    println(
      database.getStorage
        .filter(_.state == State.Finished())
        .foreach(task => println(s"[x] ${task.name} \n"))
    )
  }

  override protected def handleDeleteCommand(args: String): Unit = {
    val taskName = args.split("\\s", 1)(0)
    println(s"Deleting task $taskName...")
    database.deleteTask(name = taskName)
  }

  override protected def handleAddCommand(args: String): Unit = {
    val params = args.split("\\s+", 3)

    params match {
      case Array(taskName, description, deadline) =>
        println(
          s"Adding task: Name = '$taskName', Description = '$description', Deadline = '$deadline'..."
        )
        // check if the deadline can be converted to int
        try {
          var converted = deadline.toInt
        } catch {
          case _: NumberFormatException =>
            println(s"The string $deadline cannot be converted to integer.")
            return
        }

        database.addTask(
          name = taskName,
          description = description,
          deadline = deadline.toInt
        )

      case Array(taskName, description) =>
        println(
          s"Adding task: Name = '$taskName', Description = '$description'..."
        )
        database.addTask(
          name = taskName,
          description = description,
          deadline = null
        )

      case Array(taskName) =>
        println(s"Adding task: Name = '$taskName'...")
        database.addTask(name = taskName, description = "", deadline = null)

      case _ =>
        println(
          "Invalid add command. Usage: tracker add <task name> [description] [deadline]"
        )
    }
  }

  override protected def handleListCommand(): Unit = {
    println("Listing all tasks...")
    println(
      database.getStorage
        .filter(_.state == State.Active())
        .foreach(task => println(s"[ ] $task \n"))
    )
  }

  def dumpStorage: Array[Task] = database.getStorage
}
