package models

import org.joda.time.{LocalDate, LocalTime}
import play.api.libs.json.{Format, JodaReads, JodaWrites, Json}

case class TalkSchedule(
  talkId: Long,
  startDate: LocalDate,
  startTime: LocalTime,
  endDate: LocalDate,
  endTime: LocalTime,
  delay: Int = 0,
  canceled: Boolean = false,
  id: Option[Long] = None
) extends DbModel

object TalkSchedule {
  implicit val localDateFmt = Format(
    JodaReads.DefaultJodaLocalDateReads,
    JodaWrites.jodaLocalDateWrites("yyyy-MM-dd")
  )
  implicit val localTimeFmt = Format(
    JodaReads.DefaultJodaLocalTimeReads,
    JodaWrites.jodaLocalTimeWrites("HH:mm")
  )

  val tupled = (apply _).tupled
  implicit val fmt = Json.using[Json.WithDefaultValues].format[TalkSchedule]
}
