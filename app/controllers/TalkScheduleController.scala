package controllers

import javax.inject.{Inject, Singleton}

import models.TalkSchedule
import play.api.libs.json.Json.toJson
import play.api.mvc.{AbstractController, ControllerComponents}
import repositories.TalkScheduleRepository

import scala.concurrent.ExecutionContext

@Singleton
class TalkScheduleController @Inject()(
  cc: ControllerComponents,
  talkSchedules: TalkScheduleRepository
)(implicit ec: ExecutionContext) extends AbstractController(cc) {

  def list = Action.async {
    talkSchedules.list.map(x => Ok(toJson(x)))
  }

  def create = Action.async(parse.json[TalkSchedule]) { implicit req =>
    talkSchedules.create(req.body).map(x => Ok(toJson(x)))
  }

  def get(id: Long) = Action.async {
    talkSchedules.get(id).map(x => Ok(toJson(x)))
  }

  def update(id: Long) = Action.async(parse.json[TalkSchedule]) { implicit req =>
    talkSchedules.update(id, req.body).map(x => Ok(toJson(x)))
  }

  def delete(id: Long) = Action.async {
    talkSchedules.delete(id).map(x => Ok(toJson(x)))
  }
}
