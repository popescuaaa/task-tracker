import entities.Tracker

object Main {

  def main(args: Array[String]): Unit = {
    val tracker: Tracker = new Tracker()
    tracker.run()
  }

}
