package helpers
import java.util.UUID


object UUIDGenerator {
  def generate(): String = UUID.randomUUID().toString
}
