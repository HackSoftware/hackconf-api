package controllers

import javax.inject.{Inject, Singleton}

import actions.RegisterTokenAction
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
    ).map {
      x => Ok(JsObject(Map("result" -> toJson(x))))
    }
  }
}
