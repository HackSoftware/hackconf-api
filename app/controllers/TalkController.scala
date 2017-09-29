package controllers

import javax.inject.{Inject, Singleton}

import actions.AuthenticatedAction
import models.Talk
import play.api.libs.json.Json.toJson
import play.api.mvc.{AbstractController, ControllerComponents}
import repositories.TalkRepo

import scala.concurrent.ExecutionContext

@Singleton
class TalkController @Inject() (
  cc: ControllerComponents,
  authAction: AuthenticatedAction,
  talks: TalkRepo
)(implicit ec: ExecutionContext) extends AbstractController(cc) {

  def list = Action.async {
    talks.list.map(x => Ok(toJson(x)))
  }

  def create = authAction.async(parse.json[Talk]) { implicit req =>
    talks.create(req.body).map(x => Ok(toJson(x)))
  }

  def get(id: Long) = Action.async {
    talks.get(id).map(x => Ok(toJson(x)))
  }

  def update(id: Long) = authAction.async(parse.json[Talk]) { implicit req =>
    talks.update(id, req.body).map(x => Ok(toJson(x)))
  }

  def delete(id: Long) = authAction.async {
    talks.delete(id).map(x => Ok(toJson(x)))
  }
}
