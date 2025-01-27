package tracker.v2
import helpers.UUIDGenerator
import slick.jdbc.HsqldbProfile.api._
import scala.concurrent.Await
import scala.concurrent.duration.DurationInt

/** Interface for a simple SQLite slick layer
  */
class SQLiteDatabase {
  val db: Database = Database.forConfig(path = "sqlite")
  val tasks = TableQuery[Task]
  var taskIndex: Int = 0

  def init() = {
    println("Creating the database...")
    db.run(
      DBIO.seq(
        tasks.schema.create
      )
    )
  }

  def populate(): Unit = {
    println("Populating the database with some fake tasks...")
    db.run(
      DBIO.seq(
        tasks += (taskIndex, UUIDGenerator
          .generate(), "Buy milk", "A simple task from the groceries list", "active", 0.25f),
        tasks += (taskIndex + 1, UUIDGenerator
          .generate(), "Buy bread", "A simple task from the groceries list", "active", 0.25f),
        tasks += (taskIndex + 2, UUIDGenerator
          .generate(), "Buy bacon", "A simple task from the groceries list", "finished", 0.25f),
        tasks += (taskIndex + 3, UUIDGenerator
          .generate(), "Test some retaining models from HuggingFace", "A simple task from the groceries list", "active", 2.5f)
      )
    )

    taskIndex = taskIndex + 3
  }

  def addTask(
      taskName: String,
      taskDescription: String,
      taskDuration: Float
  ): Unit = {
    db.run(
      DBIO.seq(
        tasks += (taskIndex + 1, UUIDGenerator
          .generate(), taskName, taskDescription, "active", taskDuration)
      )
    )

    taskIndex = taskIndex + 1
  }
  def getData: Array[TableQuery.Extract[Task]] =
    Await.result(db.run(tasks.subquery.result), 10.second).toArray

  def delete(taskName: String): Int = {
    val deleteAction = tasks.filter(_.name.toString == taskName).delete
    val rowsAffected = Await.result(db.run(deleteAction), 10.seconds)
    rowsAffected // Returns the number of rows deleted
  }

  def finish(taskName: String): Int = {
    val finishAction = tasks.filter(_.name.toString == taskName).delete
    val rowsAffected = Await.result(db.run(finishAction), 10.seconds)
    rowsAffected // Returns the number of rows deleted
  }

}
