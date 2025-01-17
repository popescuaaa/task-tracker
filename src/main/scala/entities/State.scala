package entities


sealed trait State

object State {
  case class Active() extends State
  case class Finished() extends State
  case class Deleted() extends State
}
