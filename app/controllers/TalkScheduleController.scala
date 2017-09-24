package controllers

import javax.inject.{Inject, Singleton}

import actions.RegisterTokenAction
import models.TalkSchedule
import play.api.libs.json.Json.toJson
import play.api.mvc.{AbstractController, ControllerComponents}
import repositories.TalkScheduleRepo

import scala.concurrent.ExecutionContext

@Singleton
class TalkScheduleController @Inject()(
  cc: ControllerComponents,
  registerTokenAction: RegisterTokenAction,
  talkSchedules: TalkScheduleRepo
)(implicit ec: ExecutionContext) extends AbstractController(cc) {

  def list = Action.async {
    talkSchedules.list.map(x => Ok(toJson(x)))
  }

  def create = registerTokenAction.async(parse.json[TalkSchedule]) { implicit req =>
    talkSchedules.create(req.body).map(x => Ok(toJson(x)))
  }

  def get(id: Long) = Action.async {
    talkSchedules.get(id).map(x => Ok(toJson(x)))
  }

  def update(id: Long) = registerTokenAction.async(parse.json[TalkSchedule]) { implicit req =>
    talkSchedules.update(id, req.body).map(x => Ok(toJson(x)))
  }

  def delete(id: Long) = registerTokenAction.async {
    talkSchedules.delete(id).map(x => Ok(toJson(x)))
  }
}
