package repositories

import javax.inject.{Inject, Singleton}

import models.{Schedule, Speaker, Talk, TalkSchedule}
import org.joda.time.LocalDate
import play.api.db.slick.DatabaseConfigProvider

import scala.concurrent.Future

@Singleton
class ScheduleRepo @Inject() (
  protected val dbConfigProvider: DatabaseConfigProvider
) extends TalkScheduleQ {

  import profile.api._
  import com.github.tototoshi.slick.PostgresJodaSupport._

  def get(year: Int, month: Int, day: Int): Future[Seq[((TalkSchedule, Talk), Speaker)]] = db.run {
    talkScheduleQ.filter(_.startDate === new LocalDate(year, month, day))
      .sortBy(_.startTime)
      .join(talkQ).on(_.talkId === _.id)
      .join(speakerQ).on(_._2.speakerId === _.id)
      .result
  }
}
