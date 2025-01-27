package tracker.commons

abstract class AbstractTracker(inputSource: () => String) {

  // Abstract method to be implemented by specific trackers
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

  protected def handleCheckCommand(): Unit

  protected def handleListCommand(): Unit

  protected def handleFinishCommand(args: String): Unit

  protected def handleDeleteCommand(args: String): Unit

  protected def handleAddCommand(args: String): Unit

  // Common `run` method, shared by all implementations
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

  private def displayHelp(): Unit = {
    println("""Available commands:
              |add <task name> [description] [deadline] - Add a new task
              |delete <task name> - Delete a task from the active ones.
              |list - List all active tasks
              |check - List all finished tasks
              |close - Exit the application
              |""".stripMargin)
  }
}
