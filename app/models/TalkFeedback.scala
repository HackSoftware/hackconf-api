package models

import play.api.libs.json.Json

case class TalkFeedback(
  talkId: Long,
  positive: Boolean,
  details: Option[String],
  id: Option[Long]
) extends DbModel

object TalkFeedback {
  val tupled = (apply _).tupled
  implicit val fmt = Json.format[TalkFeedback]
}
