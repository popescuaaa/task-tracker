package tracker.v1

import helpers.UUIDGenerator

class Database {
  private var storage: Array[Task] = Array[Task]()

  def addTask(name: String, description: String, deadline: Integer): Unit = {
    val uuid: String = UUIDGenerator.generate()

    // Check for existing tasks with the same name
    if (storage.exists(_.name == name)) {
      println(
        "There is a task with the same name! The operation is not possible."
      )
    } else {
      storage = storage :+ new Task(
        name = name,
        description = description,
        deadline = deadline,
        uuid = uuid
      )
    }
  }

  def deleteTask(name: String): Unit = {
    // find the first one with the specified name
    storage.find(_.name == name).foreach(_.updateState(State.Deleted()))
  }

  def finishTask(name: String): Unit = {
    // find the first one with the specified name
    storage.find(_.name == name).foreach(_.updateState(State.Finished()))
  }

  def getStorage: Array[Task] = storage
}
