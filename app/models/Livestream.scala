package models

import play.api.libs.json.Json

case class Livestream(url: String, id: Option[Long] = None) extends DbModel

object Livestream {
  val tupled = (apply _).tupled
  implicit val fmt = Json.format[Livestream]
}
