package controllers

import javax.inject.{Inject, Singleton}

import models.TalkFeedback
import play.api.libs.json.Json.toJson
import play.api.mvc.{AbstractController, ControllerComponents}
import repositories.TalkFeedbackRepo

import scala.concurrent.ExecutionContext

@Singleton
class TalkFeedbackController @Inject() (
  cc: ControllerComponents,
  talkFeedbacks: TalkFeedbackRepo
)(implicit ec: ExecutionContext) extends AbstractController(cc) {

  def list = Action.async {
    talkFeedbacks.list.map(x => Ok(toJson(x)))
  }

  def create = Action.async(parse.json[TalkFeedback]) { implicit req =>
    talkFeedbacks.create(req.body).map(x => Ok(toJson(x)))
  }

  def get(id: Long) = Action.async {
    talkFeedbacks.get(id).map(x => Ok(toJson(x)))
  }

  def update(id: Long) = Action.async(parse.json[TalkFeedback]) { implicit req =>
    talkFeedbacks.update(id, req.body).map(x => Ok(toJson(x)))
  }

  def delete(id: Long) = Action.async {
    talkFeedbacks.delete(id).map(x => Ok(toJson(x)))
  }
}
