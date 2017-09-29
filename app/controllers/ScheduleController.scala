package controllers

import javax.inject.{Inject, Singleton}

import models.{Schedule, ScheduleForDate, ScheduleRequest}
import play.api.libs.json.JsObject
import play.api.libs.json.Json.toJson
import play.api.mvc.{AbstractController, ControllerComponents}
import repositories.ScheduleRepo

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ScheduleController @Inject() (
  cc: ControllerComponents,
  schedule: ScheduleRepo
)(implicit ec: ExecutionContext) extends AbstractController(cc) {

  def get = Action.async(parse.json[ScheduleRequest]) { implicit req =>
    Future.sequence(
      req.body.dates.map { date =>
        schedule.get(date).map { x => ScheduleForDate(date, Schedule(x)) }
      }
    ).map { daySchedules =>
      Ok(JsObject(Map("result" ->
        JsObject(daySchedules.map { case ScheduleForDate(date, dateSch) =>
          date.toString("yyyy-MM-dd") -> toJson(dateSch.copy(talks = dateSch.talks.sortBy {
            case (sch, talk, sp) => sch.startTime.toDateTimeToday.getMillis
          }))
        })
      )))
    }
  }
}
