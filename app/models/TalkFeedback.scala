package models

import play.api.libs.json._

sealed trait Feedback {
  override def toString: String = this match {
    case Terrible => "terrible"
    case Bad => "bad"
    case Okay => "okay"
    case Good => "good"
    case Great => "great"
  }
}
object Feedback {
  def apply(s: String): Feedback = s match {
    case "terrible" => Terrible
    case "bad" => Bad
    case "okay" => Okay
    case "good" => Good
    case "great" => Great
  }

  implicit val fmt = Format(
    Reads[Feedback](_.validate[String].map(Feedback(_))),
    Writes[Feedback](fb => JsString(fb.toString))
  )
}
case object Terrible extends Feedback
case object Bad extends Feedback
case object Okay extends Feedback
case object Good extends Feedback
case object Great extends Feedback

case class TalkFeedback(
  talkId: Long,
  feedback: Feedback,
  details: Option[String],
  id: Option[Long]
) extends DbModel

object TalkFeedback {
  val tupled = (apply _).tupled
  implicit val fmt = Json.format[TalkFeedback]
}
