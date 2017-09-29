package controllers

import javax.inject.{Inject, Singleton}

import actions.AuthenticatedAction
import models.TalkSchedule
import play.api.libs.json.Json.toJson
import play.api.mvc.{AbstractController, ControllerComponents}
import repositories.TalkScheduleRepo

import scala.concurrent.ExecutionContext

@Singleton
class TalkScheduleController @Inject()(
  cc: ControllerComponents,
  authAction: AuthenticatedAction,
  talkSchedules: TalkScheduleRepo
)(implicit ec: ExecutionContext) extends AbstractController(cc) {

  def list = Action.async {
    talkSchedules.list.map(x => Ok(toJson(x)))
  }

  def create = authAction.async(parse.json[TalkSchedule]) { implicit req =>
    talkSchedules.create(req.body).map(x => Ok(toJson(x)))
  }

  def get(id: Long) = Action.async {
    talkSchedules.get(id).map(x => Ok(toJson(x)))
  }

  def update(id: Long) = authAction.async(parse.json[TalkSchedule]) { implicit req =>
    talkSchedules.update(id, req.body).map(x => Ok(toJson(x)))
  }

  def delete(id: Long) = authAction.async {
    talkSchedules.delete(id).map(x => Ok(toJson(x)))
  }
}
