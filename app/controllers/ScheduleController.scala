package controllers

import javax.inject.{Inject, Singleton}

import models.{Schedule, ScheduleRequest}
import play.api.libs.json.{JsArray, JsObject}
import play.api.libs.json.Json.toJson
import play.api.mvc.{AbstractController, ControllerComponents}
import repositories.ScheduleRepo

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ScheduleController @Inject() (
  cc: ControllerComponents,
  schedule: ScheduleRepo
)(implicit ec: ExecutionContext) extends AbstractController(cc) {

  //TODO: clean this mess up
  def get = Action.async(parse.json[ScheduleRequest]) { implicit req =>
    Future.sequence(
      // Don't ask...
      req.body.dates.map { date =>
        schedule.get(date.getYear, date.getMonthOfYear, date.getDayOfMonth).map { x =>
          val items = x.map {
            case ((talkSchedule, talk), speaker) => (talkSchedule, talk, speaker)
          }.toList

          date -> Schedule(items)
        }
      }
      // Don't ask...
    ).map(_.toMap).map {
      x => Ok(
        JsObject(
          Map(
            "result" -> JsObject(x.map { case (d, s) => d.toString("yyyy-MM-dd") -> toJson(s)})
          )
        )
      )
    }
  }

}
