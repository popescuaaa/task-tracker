import entities.Tracker

object Main {

  def main(args: Array[String]): Unit = {
    val tracker = new Tracker(() => scala.io.StdIn.readLine("tracker> "))
    tracker.run()
  }

}
