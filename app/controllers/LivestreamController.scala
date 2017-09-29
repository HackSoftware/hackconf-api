package controllers

import javax.inject.{Inject, Singleton}

import actions.AuthenticatedAction
import models.Livestream
import play.api.libs.json.Json.toJson
import play.api.mvc.{AbstractController, ControllerComponents}
import repositories.LivestreamRepo

import scala.concurrent.ExecutionContext

@Singleton
class LivestreamController @Inject() (
  cc: ControllerComponents,
  livestreams: LivestreamRepo,
  authAction: AuthenticatedAction
)(implicit ec: ExecutionContext) extends AbstractController(cc) {

  def list = authAction.async {
    livestreams.list.map(x => Ok(toJson(x)))
  }

  def create = authAction.async(parse.json[Livestream]) { implicit req =>
    livestreams.create(req.body).map(x => Ok(toJson(x)))
  }

  def get(id: Long) = Action.async {
    livestreams.get(id).map(x => Ok(toJson(x)))
  }

  def update(id: Long) = authAction.async(parse.json[Livestream]) { implicit req =>
    livestreams.update(id, req.body).map(x => Ok(toJson(x)))
  }

  def delete(id: Long) = authAction.async {
    livestreams.delete(id).map(x => Ok(toJson(x)))
  }
}
