package sunaba
package model

import java.sql.Timestamp
import org.joda.time.DateTime
import slick.jdbc.MySQLProfile.api._

object SlickHelper {
  implicit val jodatimeColumnType = MappedColumnType.base[DateTime, Timestamp](
    { jodatime => new Timestamp(jodatime.getMillis()) },
    { sqltime => new DateTime(sqltime.getTime) }
  )
}
