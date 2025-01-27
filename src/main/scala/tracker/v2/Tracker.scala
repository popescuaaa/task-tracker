package tracker.v2

import tracker.commons.AbstractTracker

/** Tracker v2
  */
class Tracker(inputSource: () => String) extends AbstractTracker(inputSource) {

  private val db: SQLiteDatabase = new SQLiteDatabase()

  // Init database
  db.init()
  db.populate()

  override protected def handleCheckCommand(): Unit = ???

  override protected def handleListCommand(): Unit = {
    val results = db.getData
    results.foreach(entity => println(s" - [ ] ${entity} \n"))
  }

  override protected def handleFinishCommand(args: String): Unit = ???

  override protected def handleDeleteCommand(args: String): Unit = ???

  override protected def handleAddCommand(args: String): Unit = ???

}
