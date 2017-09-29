package actions

import javax.inject.Inject

import play.api.Configuration
import play.api.http.{HttpEntity, Status}
import play.api.mvc.{BodyParsers, Request, Result, ActionBuilderImpl, ResponseHeader}

import scala.concurrent.{ExecutionContext, Future}

/**
  * Filters out the requests done without passing the `Token` set in the application.conf
  */

class AuthenticatedAction @Inject()(parser: BodyParsers.Default, config: Configuration)(implicit ec: ExecutionContext) extends ActionBuilderImpl(parser) {
  val apiKey = config.get[String]("api.key")

  override def invokeBlock[A](request: Request[A], block: (Request[A]) => Future[Result]): Future[Result] = {
    request.headers.get("Token") match {
      case Some(passedKey) if passedKey == apiKey =>
        block(request)
      case _ =>
        Future(Result(ResponseHeader(Status.UNAUTHORIZED), HttpEntity.NoEntity))
    }
  }
}
