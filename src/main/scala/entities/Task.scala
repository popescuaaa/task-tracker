package entities
import helpers.UUIDGenerator

class Task(val name: String, val description: String, val deadline: Integer, val uuid: String) {
  var state: State = State.Active()
  def updateState(updateState: State): Unit = {
    state = updateState
  }
  override def toString: String = s"Task $name: $description [$deadline]"
}
