package repositories

import javax.inject.{Inject, Singleton}

import models.{Speaker, Talk, TalkSchedule}
import org.joda.time.LocalDate
import play.api.db.slick.DatabaseConfigProvider

import scala.concurrent.{Future, ExecutionContext}

@Singleton
class ScheduleRepo @Inject() (
  protected val dbConfigProvider: DatabaseConfigProvider
)(implicit ec: ExecutionContext) extends TalkScheduleQ {

  import profile.api._
  import com.github.tototoshi.slick.PostgresJodaSupport._

  def get(date: LocalDate): Future[List[(TalkSchedule, Talk, Speaker)]] = db.run {
    talkScheduleQ.filter(_.startDate === date)
      .sortBy(_.startTime)
      .join(talkQ).on(_.talkId === _.id)
      .join(speakerQ).on(_._2.speakerId === _.id)
      .result
    }.map (item =>
      item.toList.map{ case ((x,y),z) => (x,y,z) }
    )
}
