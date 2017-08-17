package controllers

import javax.inject.{Inject, Singleton}

import models.Schedule
import play.api.libs.json.Json.toJson
import play.api.mvc.{AbstractController, ControllerComponents}
import repositories.ScheduleRepository

import scala.concurrent.ExecutionContext

@Singleton
class ScheduleController @Inject() (
  cc: ControllerComponents,
  schedule: ScheduleRepository
)(implicit ec: ExecutionContext) extends AbstractController(cc) {

  def index(year: Int, month: Int, day: Int) = Action.async {
    schedule.get(year, month, day).map { x =>
      val items = x.map {
        case ((talkSchedule, talk), speaker) => (talkSchedule, talk, speaker)
      }.toList
      Schedule(items)
    }.map(x => Ok(toJson(x)))
  }
}
