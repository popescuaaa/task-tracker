import entities.State
import slick.jdbc.SQLiteProfile.api._
import scala.concurrent.Await
import scala.concurrent.duration.DurationInt

class Tasks(tag: Tag) extends Table[(Int, String, String)](tag, "tasks") {
  def taskId = column[Int]("taskId", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name", O.Unique)
  def state = column[String]("state")

  def * = (taskId, name, state)
}

object SQLitePlayground {
  val db: Database = Database.forConfig(path = "sqlite")
  val tasks = TableQuery[Tasks]

  def main(args: Array[String]): Unit = {
    db.run(
      DBIO.seq(
        tasks.schema.create,
        tasks += (0, "Buy milk", "active"),
        tasks += (1, "Buy bread", "active"),
        tasks += (2, "Finish homework", "active")
      )
    )

    // Alter state of an element
    val updateAction = tasks
      .filter(_.taskId === 1) // Locate the row where taskId = 1
      .map(_.state) // Select the column to update
      .update("finished") // Set the new value for the column

    db.run(updateAction)

    // When you take data from db just await it
    var results = Await.result(db.run(tasks.subquery.result), 10.second)
    results.foreach(println)
  }
}
