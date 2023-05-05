package sunaba
package service

import slick.jdbc.MySQLProfile.api._
import sunaba.model.{Parts, Part}
import sunaba.DB

trait PartService {
  val parts = TableQuery[Parts]

  def findPartById(id: String): Option[Part] = {
    val query = parts.filter(_.part_id === id).result.headOption
    DB.runSync(query)
  }
}
object PartService extends PartService
