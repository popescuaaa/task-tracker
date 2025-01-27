import tracker.v1.Tracker

object Main {

  def main(args: Array[String]): Unit = {
    val inputSource = () => scala.io.StdIn.readLine("tracker> ")
    val tracker = new Tracker(inputSource = inputSource)
    tracker.run()
  }

}
