package entities

/** The main app class
  * The task tracker has the input source decoupled from the logic.
  */
class Tracker(inputSource: () => String) {
  private val database: Database = new Database()

  def run(): Unit = {

    println("Welcome to the tracker! Type your commands. Type 'close' to exit.")

    var isRunning = true

    while (isRunning) {
      val input = inputSource()

      input.toLowerCase match {
        case "close" =>
          println("Exiting Task Tracker. Goodbye!")
          isRunning = false
        case command =>
          handleTrackerCommand(command)
      }
    }
  }

  private def handleTrackerCommand(commandLine: String): Unit = {

    val parts = commandLine.split("\\s+", 2) // Split into command and arguments

    parts match {
      case Array("add", args)    => handleAddCommand(args)
      case Array("delete", args) => handleDeleteCommand(args)
      case Array("finish", args) => handleFinishCommand(args)
      case Array("list")         => handleListCommand()
      case Array("check")        => handleCheckCommand()
      case Array("help")         => displayHelp()
      case _ =>
        println("Invalid tracker command. Type 'tracker help' for usage.")
    }
  }

  private def handleFinishCommand(args: String): Unit = {
    val taskName: String = args.split("\\s", 1)(0)
    database.finishTask(name = taskName)
  }

  private def handleCheckCommand(): Unit = {
    println("Checking the finished tasks...")
    println(
      database.getStorage
        .filter(_.state == State.Finished())
        .foreach(task => println(s"[x] ${task.name} \n"))
    )
  }

  private def handleDeleteCommand(args: String): Unit = {
    val taskName = args.split("\\s", 1)(0)
    println(s"Deleting task $taskName...")
    database.deleteTask(name = taskName)
  }

  private def handleAddCommand(args: String): Unit = {
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

  private def handleListCommand(): Unit = {
    println("Listing all tasks...")
    println(
      database.getStorage
        .filter(_.state == State.Active())
        .foreach(task => println(s"[ ] $task \n"))
    )
  }

  private def displayHelp(): Unit = {
    println("""Available commands:
        |add <task name> [description] [deadline] - Add a new task
        |delete <task name> - Delete a task from the active ones.
        |list - List all active tasks
        |check - List all finished tasks
        |close - Exit the application
        |""".stripMargin)
  }

  def dumpStorage: Array[Task] = database.getStorage
}
