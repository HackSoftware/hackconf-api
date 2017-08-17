package models

import play.api.libs.json.{JsObject, Json, Writes}

case class Schedule(
  talks: List[(TalkSchedule, Talk, Speaker)]
)

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


