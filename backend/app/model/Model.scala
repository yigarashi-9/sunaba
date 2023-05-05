package sunaba
package model

import com.github.tototoshi.slick.MySQLJodaSupport._
import org.joda.time.DateTime
import sangria.relay.Node
import slick.jdbc.MySQLProfile.api._

// TODO: モデルからスキーマを生成したほうが良いかも
// TODO: モデルとテーブルとのマッピングは別ファイルの方が良いかも

case class Part(
    partID: String,
    name: String
) extends Node {
  def id: String = partID
}

class Parts(tag: Tag) extends Table[Part](tag, "part") {
  def part_id = column[String]("part_id", O.PrimaryKey)
  def name = column[String]("name")

  def * = (
    part_id,
    name
  ) <> (Part.tupled, Part.unapply)
}

case class Event(
    eventID: String,
    partID: String,
    name: String
) extends Node {
  def id: String = eventID
}

class Events(tag: Tag) extends Table[Event](tag, "event") {
  def event_id = column[String]("event_id", O.PrimaryKey)
  def part_id = column[String]("part_id")
  def name = column[String]("name")

  def * = (
    event_id,
    part_id,
    name,
  ) <> (Event.tupled, Event.unapply)
}

case class Training(
    trainingID: String,
    eventID: String,
    count: Int,
    weight: Option[Double],
    createdAt: DateTime
) extends Node {
  def id: String = trainingID
}

class Trainings(tag: Tag) extends Table[Training](tag, "training") {
  def training_id = column[String]("training_id", O.PrimaryKey)
  def event_id = column[String]("event_id")
  def count = column[Int]("count")
  def weight = column[Option[Double]]("weight")
  def created_at = column[DateTime]("created_at")

  def * = (
    training_id,
    event_id,
    count,
    weight,
    created_at
  ) <> (Training.tupled, Training.unapply)
}
