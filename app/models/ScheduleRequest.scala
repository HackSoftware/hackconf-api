package models

import org.joda.time.LocalDate
import play.api.libs.json.{Json, Reads}

case class ScheduleRequest(dates: List[LocalDate])

object ScheduleRequest {
  implicit val dateFmt = Reads[LocalDate](_.validate[String].map(LocalDate.parse))
  implicit val fmt = Json.reads[ScheduleRequest]
}
