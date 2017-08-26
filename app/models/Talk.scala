package models

import play.api.libs.json.Json

case class Talk(
  title: String,
  description: String,
  speakerId: Long,
  id: Option[Long] = None
) extends DbModel

object Talk {
  val tupled = (apply _).tupled
  implicit val fmt = Json.format[Talk]
}
