package repositories

import javax.inject.{Inject, Singleton}

import models.TalkSchedule
import org.joda.time.{LocalDate, LocalTime}
import play.api.db.slick.DatabaseConfigProvider

import scala.concurrent.Future

trait TalkScheduleQ extends BaseQ with TalkQ {
  import profile.api._
  import com.github.tototoshi.slick.PostgresJodaSupport._

  class TalkScheduleTable(tag: Tag) extends Table[TalkSchedule](tag, "talk_schedules") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def talkId = column[Long]("talk_id")
    def talk = foreignKey("talk_fk", talkId, talkQ)(_.id)

    def startDate = column[LocalDate]("start_date")
    def startTime = column[LocalTime]("start_time")

    def endDate = column[LocalDate]("end_date")
    def endTime = column[LocalTime]("end_time")

    def delay = column[Int]("delay")

    def canceled = column[Boolean]("canceled")

    def * = (talkId, startDate, startTime, endDate, endTime, delay, canceled, id.?).mapTo[TalkSchedule]
  }

  val talkScheduleQ = TableQuery[TalkScheduleTable]
}

@Singleton
class TalkScheduleRepo @Inject() (
  protected val dbConfigProvider: DatabaseConfigProvider
) extends CrudRepo[TalkSchedule] with TalkScheduleQ {

  import profile.api._
  import com.github.tototoshi.slick.PostgresJodaSupport._

  override def list: Future[Seq[TalkSchedule]] = db.run {
    talkScheduleQ.result
  }

  override def create(entity: TalkSchedule): Future[Int] = db.run {
    talkScheduleQ += entity
  }

  override def get(id: Long): Future[Option[TalkSchedule]] = db.run {
    talkScheduleQ.filter(_.id === id).result.headOption
  }

  override def update(id: Long, entity: TalkSchedule): Future[Int] = db.run {
    talkScheduleQ.filter(_.id === id).update(entity)
  }

  override def delete(id: Long): Future[Int] = db.run {
    talkScheduleQ.filter(_.id === id).delete
  }
}
