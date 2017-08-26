package models

import play.api.libs.json.Json

case class Speaker(
  firstName: String,
  lastName: String,
  bio: String,
  youtubeId: String,
  id: Option[Long] = None
) extends DbModel

object Speaker {
  val tupled = (apply _).tupled
  implicit val fmt = Json.format[Speaker]
}
