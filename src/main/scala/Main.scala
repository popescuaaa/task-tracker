import scala.io.StdIn.readLine
import entities.Database
import entities.State

object Main {
  val database: Database = new Database()

  def main(args: Array[String]): Unit = {

    println("Welcome to the tracker! Type your commands. Type 'close' to exit.")

    var isRunning = true

    while (isRunning) {
      val input = readLine("tracker> ")

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
      case Array("add", args) => handleAddCommand(args)
      case Array("delete", args) => handleDeleteCommand(args)
      case Array("list") => handleListCommand()
      case Array("check") => handleCheckCommand()
      case Array("help") => displayHelp()
      case _ =>
        println("Invalid tracker command. Type 'tracker help' for usage.")
    }
  }

  private def handleCheckCommand() = ???

  private def handleDeleteCommand(args: String) = ???

  private def handleAddCommand(args: String): Unit = {
    val params = args.split("\\s+", 3)

    params match {
      case Array(taskName, description, deadline) =>
        println(s"Adding task: Name = '$taskName', Description = '$description', Deadline = '$deadline'...")
        // check if the deadline can be converted to int
        try {
          var converted = deadline.toInt
        } catch {
          case _: NumberFormatException =>
            println(s"The string $deadline cannot be converted to integer.")
            return
        }

        database.addTask(name = taskName, description = description, deadline = deadline.toInt)

      case Array(taskName, description) =>
        println(s"Adding task: Name = '$taskName', Description = '$description'...")
        database.addTask(name = taskName, description = description, deadline = null)

      case Array(taskName) =>
        println(s"Adding task: Name = '$taskName'...")
        database.addTask(name = taskName, description = "", deadline = null)

      case _ =>
        println("Invalid add command. Usage: tracker add <task name> [description] [deadline]")
    }
  }

  private def handleListCommand(): Unit = {
    println("Listing all tasks...")
    println(database.getStorage.filter(_.state == State.Active()).foreach(task => println(s"[ ] $task \n")))
  }

  private def displayHelp(): Unit = {
    println(
      """Available commands:
        |add <task name> [description] [deadline] - Add a new task
        |delete <task name> - Delete a task from the active ones.
        |list - List all active tasks
        |check - List all finished tasks
        |close - Exit the application
        |""".stripMargin)
  }
}
