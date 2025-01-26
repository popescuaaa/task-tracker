import slick.jdbc.SQLiteProfile.api._

import scala.concurrent.Await
import scala.concurrent.duration.DurationInt

class Tasks(tag: Tag) extends Table[(Int, String)](tag, "tasks") {
  def taskId = column[Int]("taskId", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name", O.Unique)

  def * = (taskId, name)
}

object SQLitePlayground {
  val db: Database = Database.forConfig(path = "sqlite")
  val tasks = TableQuery[Tasks]

  def main(args: Array[String]): Unit = {
    db.run(
      DBIO.seq(
        tasks.schema.create,
        tasks += (0, "Buy milk")
      )
    )

    // When you take data from db just await it
    val results = Await.result(db.run(tasks.subquery.result), 10.second)
    results.foreach(println)
  }
}
