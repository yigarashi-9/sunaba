package sunaba
package service

import slick.jdbc.MySQLProfile.api._
import sunaba.model.{Events, Event}
import sunaba.DB

trait EventService {
  val events = TableQuery[Events]

  def findEventById(id: String): Option[Event] = {
    val query = events.filter(_.event_id === id).result.headOption
    DB.runSync(query)
  }
}
object EventService extends EventService
