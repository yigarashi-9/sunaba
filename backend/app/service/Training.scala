package sunaba
package service

import com.github.tototoshi.slick.MySQLJodaSupport._
import slick.jdbc.MySQLProfile.api._
import sunaba.model.{Trainings, Training}
import sunaba.DB
import org.joda.time.DateTime

trait TrainingService {
  val trainings = TableQuery[Trainings]

  def findTrainingById(id: String): Option[Training] = {
    val query = trainings.filter(_.training_id === id).result.headOption
    DB.runSync(query)
  }

  def findLastTrainings(since: DateTime): Seq[Training] = {
    val query =
      trainings.filter(_.created_at >= since).sortBy(_.created_at.desc).result
    DB.runSync(query)
  }

  def addTraining(eventID: String, count: Int, weight: Option[Double]) = {
    val trainingID = DB.generateUUID().toString()
    val createdAt = DateTime.now()
    val training = Training(trainingID, eventID, count, weight, createdAt)
    val query = trainings += training
    DB.runSync(query)
    training
  }
}
object TrainingService extends TrainingService
