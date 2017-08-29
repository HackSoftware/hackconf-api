package models

import org.joda.time.LocalDate
import play.api.libs.json.{JsObject, Json, Writes}

case class Schedule(talks: List[(TalkSchedule, Talk, Speaker)])

object Schedule {
  implicit val writes = Writes[Schedule](schedule =>
    Json.toJson(schedule.talks.map { case (ts, t, s) =>
      val talk = Json.toJsObject(t) - "speakerId"
      val schedule = Json.toJsObject(ts) - "talkId"
      val speaker = Json.toJsObject(s)

      JsObject(
        talk.fields :+ ("schedule" -> schedule) :+ ("speaker" -> speaker)
      )
    }
  ))
}

case class ScheduleForDate(date: LocalDate, schedule: Schedule)

object ScheduleForDate {
  implicit val writes = Writes[ScheduleForDate] (sfd =>
    JsObject(Map(sfd.date.toString("yyyy-MM-dd") -> Json.toJson(sfd.schedule)))
  )
}
