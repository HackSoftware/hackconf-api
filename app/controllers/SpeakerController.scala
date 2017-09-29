package controllers

import javax.inject.{Inject, Singleton}

import actions.AuthenticatedAction
import models.Speaker
import play.api.libs.json.Json.toJson
import play.api.mvc.{AbstractController, ControllerComponents}
import repositories.SpeakerRepo

import scala.concurrent.ExecutionContext

@Singleton
class SpeakerController @Inject() (
  cc: ControllerComponents,
  authAction: AuthenticatedAction,
  speakers: SpeakerRepo
)(implicit ec: ExecutionContext) extends AbstractController(cc) {

  def list = Action.async {
    speakers.list.map(x => Ok(toJson(x)))
  }

  def create = authAction.async(parse.json[Speaker]) { implicit req =>
    speakers.create(req.body).map(x => Ok(toJson(x)))
  }

  def get(id: Long) = Action.async {
    speakers.get(id).map(x => Ok(toJson(x)))
  }

  def update(id: Long) = authAction.async(parse.json[Speaker]) { implicit req =>
    speakers.update(id, req.body).map(x => Ok(toJson(x)))
  }

  def delete(id: Long) = authAction.async {
    speakers.delete(id).map(x => Ok(toJson(x)))
  }
}
