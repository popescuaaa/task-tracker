package tracker.v2
import slick.jdbc.HsqldbProfile.api._

/** @param tag: ???
  *
  * Structure: taskId, UUID, name, description, state, duration
  */
class Task(tag: Tag)
    extends Table[(Int, String, String, String, String, Float)](tag, "tasks") {

  def taskId = column[Int]("taskId", O.AutoInc)
  def name = column[String]("name", O.Unique)

  def uuid = column[String]("uuid", O.PrimaryKey)

  def description = column[String]("description")
  def state = column[String]("state")

  def duration = column[Float]("duration")
  def * = (
    taskId,
    uuid,
    name,
    description,
    state,
    duration
  )
}
