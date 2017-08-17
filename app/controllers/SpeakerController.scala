package controllers

import javax.inject.{Inject, Singleton}

import models.Speaker
import play.api.libs.json.Json.toJson
import play.api.mvc.{AbstractController, ControllerComponents}
import repositories.SpeakerRepository

import scala.concurrent.ExecutionContext

@Singleton
class SpeakerController @Inject() (
  cc: ControllerComponents,
  speakers: SpeakerRepository
)(implicit ec: ExecutionContext) extends AbstractController(cc) {

  def list = Action.async {
    speakers.list.map(x => Ok(toJson(x)))
  }

  def create = Action.async(parse.json[Speaker]) { implicit req =>
    speakers.create(req.body).map(x => Ok(toJson(x)))
  }

  def get(id: Long) = Action.async {
    speakers.get(id).map(x => Ok(toJson(x)))
  }

  def update(id: Long) = Action.async(parse.json[Speaker]) { implicit req =>
    speakers.update(id, req.body).map(x => Ok(toJson(x)))
  }

  def delete(id: Long) = Action.async {
    speakers.delete(id).map(x => Ok(toJson(x)))
  }
}
