package controllers

import javax.inject.{Inject, Singleton}

import actions.AuthenticatedAction
import models.TalkFeedback
import org.joda.time.Instant
import play.api.libs.json.Json.toJson
import play.api.mvc.{AbstractController, ControllerComponents}
import repositories.TalkFeedbackRepo

import scala.concurrent.ExecutionContext

@Singleton
class TalkFeedbackController @Inject() (
  cc: ControllerComponents,
  authAction: AuthenticatedAction,
  talkFeedbacks: TalkFeedbackRepo
)(implicit ec: ExecutionContext) extends AbstractController(cc) {

  def list = Action.async {
    talkFeedbacks.list.map(x => Ok(toJson(x)))
  }

  def create = Action.async(parse.json[TalkFeedback]) { implicit req =>
    talkFeedbacks.create(req.body.copy(createdAt = Some(Instant.now()))).map(x => Ok(toJson(x)))
  }

  def get(id: Long) = Action.async {
    talkFeedbacks.get(id).map(x => Ok(toJson(x)))
  }

  def update(id: Long) = authAction.async(parse.json[TalkFeedback]) { implicit req =>
    talkFeedbacks.update(id, req.body).map(x => Ok(toJson(x)))
  }

  def delete(id: Long) = authAction.async {
    talkFeedbacks.delete(id).map(x => Ok(toJson(x)))
  }
}
